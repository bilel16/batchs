package com.bna.habil.infrastructure.utils;


import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StructureNode {
    private Integer id;
    private String name;
    private Integer parentId;
    private Integer structureType;
    private List<StructureNode> children = new ArrayList<>();
    private StructureNode parent;

    public StructureNode(Integer id, String name, Integer parentId, Integer structureType, List<StructureNode> children) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.structureType = structureType;
        this.children = children != null ? children : new ArrayList<>();
    }
}