package com.amazing.company.hierarchy;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class HierarchyController {

    private HierarchyRepository hierarchyRepository;

    public HierarchyController(HierarchyRepository hierarchyRepository) {
        this.hierarchyRepository = hierarchyRepository;
    }

    @GetMapping("/node/{nodeId}/children")
    public ResponseEntity<List<Node>> getChildren(@PathVariable Long nodeId) {
        if (!hierarchyRepository.existsById(nodeId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(hierarchyRepository.getChildren(nodeId));
    }

    @PutMapping("/node")
    public ResponseEntity<String> putNode(@RequestBody Node node) {
        if (node.getId().equals(node.getParentId())) {
            return ResponseEntity
                    .badRequest()
                    .body("Parent ID of a node can not be its own ID.");
        }

        if (!hierarchyRepository.existsById(node.getId())) {
            return ResponseEntity.notFound().build();
        }

        if (node.getParentId() == null) {
            hierarchyRepository.makeRoot(node.getId());
        }
        else {
            hierarchyRepository.changeParent(node.getId(), node.getParentId());
        }


        return ResponseEntity.ok().build();
    }
}
