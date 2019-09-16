package com.amazing.company.hierarchy;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface HierarchyRepository extends PagingAndSortingRepository<Node, Long> {

    @Query(
            value = "WITH RECURSIVE descendants AS (" +
                    "       SELECT id, parent_id FROM hierarchy WHERE id = :parentId" +
                    "   UNION " +
                    "       SELECT ancestor.id, ancestor.parent_id " +
                    "       FROM hierarchy ancestor INNER JOIN descendants d " +
                    "           ON d.parent_id = ancestor.id" +
                    ")" +
                    "SELECT " +
                    "   id, " +
                    "   parent_id, " +
                    "   (SELECT id FROM hierarchy WHERE parent_id IS NULL) as root_id, " +
                    "   (SELECT COUNT(*) FROM descendants) as height " +
                    "FROM Hierarchy " +
                    "WHERE parent_id = :parentId",
            nativeQuery = true
    )
    List<Node> getChildren(@Param("parentId") Long parentId);

    @Query(
            value = "WITH RECURSIVE ancestor AS (" +
                    "       SELECT id, parent_id FROM hierarchy WHERE id = :parentId " +
                    "   UNION " +
                    "       SELECT descendant.id, descendant.parent_id " +
                    "       FROM hierarchy descendant INNER JOIN ancestor a " +
                    "           ON a.parent_id = descendant.id" +
                    ") " +
                    "UPDATE hierarchy " +
                    "SET parent_id = (SELECT parent_id FROM hierarchy WHERE id = :id) " +
                    "WHERE id = (SELECT id FROM ancestor WHERE parent_id = :id) ;" +
                    "UPDATE hierarchy " +
                    "SET parent_id = :parentId " +
                    "WHERE id = :id",
            nativeQuery = true
    )
    @Modifying
    @Transactional
    void changeParent(@Param("id") Long id, @Param("parentId") Long parentId);

    @Query(
            value = "UPDATE hierarchy " +
                    "SET parent_id = :id " +
                    "WHERE id = (SELECT id FROM hierarchy WHERE parent_id IS NULL) ;" +
                    "UPDATE hierarchy " +
                    "SET parent_id = NULL " +
                    "WHERE id = :id",
            nativeQuery = true
    )
    @Modifying
    @Transactional
    void makeRoot(@Param("id") Long id);
}
