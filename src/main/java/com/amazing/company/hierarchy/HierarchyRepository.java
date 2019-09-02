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
            value = "SELECT " +
                        "id, " +
                        "nlevel(path) - 1 AS height, " +
                        "subltree(path, 0, 1) AS root_id, " +
                        "subpath(path, -2, 1) AS parent_id " +
                    "FROM Hierarchy " +
                    // Match all paths that includes the parentId
                    // followed by exactly one label.
                    "WHERE path ~ CAST(concat('*.', :parentId, '.*{1}') AS lquery)",
            nativeQuery = true
    )
    List<Node> getChildren(@Param("parentId") Long parentId);

    @Modifying
    @Transactional
    @Query(
            value = "WITH " +
                        "parent(path) AS" +
                        "(SELECT path FROM hierarchy WHERE id = :parentId)," +
                        "node(path) AS" +
                        "(SELECT path FROM hierarchy WHERE id = :nodeId)" +
                    "UPDATE hierarchy " +
                    "SET path = (SELECT path FROM parent) || subpath(path, nlevel((SELECT path FROM node)) - 1) " +
                    "WHERE path <@ (SELECT path FROM node)",
            nativeQuery = true
    )
    void changeParent(@Param("nodeId") Long nodeId, @Param("parentId") Long parentId);
}
