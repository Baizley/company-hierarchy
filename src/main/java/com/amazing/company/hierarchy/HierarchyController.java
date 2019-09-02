package com.amazing.company.hierarchy;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HierarchyController {

    private HierarchyRepository hierarchyRepository;

    public HierarchyController(HierarchyRepository hierarchyRepository) {
        this.hierarchyRepository = hierarchyRepository;
    }

    @GetMapping("/node/{nodeId}/children")
    public List<Node> getChildren(@PathVariable Long nodeId) {
        return hierarchyRepository.getChildren(nodeId);
    }

    @PutMapping("/node/{nodeId}")
    public void putNode(@PathVariable Long nodeId, @RequestBody Node node) {
        hierarchyRepository.changeParent(node.getId(), node.getParentId());
    }
}
