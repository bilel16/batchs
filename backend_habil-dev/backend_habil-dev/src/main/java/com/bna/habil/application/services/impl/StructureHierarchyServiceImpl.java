package com.bna.habil.application.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bna.habil.application.services.StructureHierarchyService;
import com.bna.habil.domain.entities.Personnel;
import com.bna.habil.domain.entities.Structure;
import com.bna.habil.infrastructure.persistence.repositories.PersonnelCustomRepository;
import com.bna.habil.infrastructure.persistence.repositories.StructureCustomRepository;
import com.bna.habil.infrastructure.utils.StructureNode;
@Service
public class StructureHierarchyServiceImpl implements StructureHierarchyService {

    private final StructureCustomRepository structureRepository;
    private final PersonnelCustomRepository personnelRepository;

    public StructureHierarchyServiceImpl(StructureCustomRepository structureRepository,
                                         PersonnelCustomRepository personnelRepository) {
        this.structureRepository = structureRepository;
        this.personnelRepository = personnelRepository;
    }

    // ─── NEW: Single method to get all managed structure IDs ───────────
    //  Uses Oracle CONNECT BY (already in your repository)
    //
    //  Example:     [100]           ← manager's structure
    //              /     \
    //           [101]   [102]       ← children
    //           /
    //        [103]                  ← grandchild
    //
    //  Returns: {100, 101, 102, 103}
    // ───────────────────────────────────────────────────────────────────

    public Set<Integer> getManagedStructureIds(String managerMatricule) {
        // 1. Find the manager's structure
        Structure managerStructure = structureRepository
                .findStructureByUserMatricule(managerMatricule);

        if (managerStructure == null) {
            return Collections.emptySet();
        }

        // 2. Oracle CONNECT BY → gets root + ALL descendants in one query
        List<Structure> allStructures = structureRepository
                .findByParentStructureRecursive(managerStructure.getId());

        // 3. Collect IDs
        return allStructures.stream()
                .map(Structure::getId)
                .collect(Collectors.toSet());
    }

    // ─── UPDATED: Now uses hierarchical structure IDs ──────────────────

    @Override
    public Set<String> getAllManagedUsers(String managerMatricule) {
        Set<Integer> structureIds = getManagedStructureIds(managerMatricule);
        if (structureIds.isEmpty()) {
            return Collections.emptySet();
        }
        return personnelRepository.findActiveMatriculesByStructureIds(structureIds);
    }

    @Override
    public Set<String> getAllManagedUsersCins(String managerMatricule) {
        Set<Integer> structureIds = getManagedStructureIds(managerMatricule);
        if (structureIds.isEmpty()) {
            return Collections.emptySet();
        }
        return personnelRepository.findActiveCinsByStructureIds(structureIds);
    }

    // ─── KEEP: These still work as before ──────────────────────────────

    @Override
    public Integer getStructureIdForUser(String userMatricule) {
        Personnel personnel = personnelRepository.findById(userMatricule).orElse(null);
        if (personnel != null && Boolean.TRUE.equals(personnel.getCod_stat_user())) {
            return personnel.getCod_strc_strc();
        }
        return null;
    }

    @Override
    public List<Personnel> getPersonnelInStructure(Integer structureId) {
        return personnelRepository.findActivePersonnelByStructureId(structureId);
    }

    @Override
    public boolean isDescendantOrSame(Integer ancestorId, Integer descendantId) {
        if (Objects.equals(ancestorId, descendantId)) {
            return true;
        }
        // Use Oracle CONNECT BY instead of building full in-memory tree
        List<Structure> descendants = structureRepository
                .findByParentStructureRecursive(ancestorId);
        return descendants.stream()
                .anyMatch(s -> Objects.equals(s.getId(), descendantId));
    }

    @Override
    public Set<Integer> getAllDescendants(Integer structureId) {
        List<Structure> descendants = structureRepository
                .findByParentStructureRecursive(structureId);
        // Remove the root itself (only descendants)
        return descendants.stream()
                .map(Structure::getId)
                .filter(id -> !Objects.equals(id, structureId))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> getDirectChildren(Integer structureId) {
        // For direct children only, we can still use in-memory or add a query
        Map<Integer, StructureNode> structureMap = buildHierarchy();
        StructureNode node = structureMap.get(structureId);
        if (node == null) {
            return Collections.emptySet();
        }
        return node.getChildren().stream()
                .map(StructureNode::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public StructureNode getNode(Integer id) {
        Map<Integer, StructureNode> structureMap = buildHierarchy();
        return structureMap.get(id);
    }

    @Override
    public Integer getLevel(Integer structureId) {
        Map<Integer, StructureNode> structureMap = buildHierarchy();
        StructureNode node = structureMap.get(structureId);
        if (node == null) return null;
        return computeLevel(node);
    }

    @Override
    public List<Integer> getPath(Integer fromId, Integer toId) {
        Map<Integer, StructureNode> structureMap = buildHierarchy();
        StructureNode fromNode = structureMap.get(fromId);
        if (fromNode == null) return Collections.emptyList();

        Set<Integer> descendants = computeDescendants(fromNode);
        if (!Objects.equals(fromId, toId) && !descendants.contains(toId)) {
            return Collections.emptyList();
        }

        List<Integer> path = new ArrayList<>();
        StructureNode current = structureMap.get(toId);
        while (current != null && !current.getId().equals(fromId)) {
            path.add(0, current.getId());
            current = current.getParent();
        }
        if (current != null) path.add(0, fromId);
        return path;
    }

    @Override
    public Integer getTypeForStructure(Integer structureId) {
        Structure structure = structureRepository.findById(structureId).orElse(null);
        return structure != null ? structure.getCodeTypeStructure() : null;
    }

    // ─── KEEP: In-memory hierarchy for methods that need it ────────────

    private Map<Integer, StructureNode> buildHierarchy() {
        List<Structure> structures = structureRepository.findAll();
        Map<Integer, StructureNode> structureMap = new HashMap<>();

        for (Structure s : structures) {
            structureMap.put(s.getId(),
                    new StructureNode(s.getId(), s.getLibelleStructure(),
                            s.getCodeStructureMere(), s.getCodeTypeStructure(),
                            new ArrayList<>()));
        }

        for (StructureNode node : structureMap.values()) {
            if (node.getParentId() != null && structureMap.containsKey(node.getParentId())) {
                StructureNode parent = structureMap.get(node.getParentId());
                parent.getChildren().add(node);
                node.setParent(parent);
            }
        }

        return structureMap;
    }

    private Set<Integer> computeDescendants(StructureNode node) {
        Set<Integer> descendants = new HashSet<>();
        for (StructureNode child : node.getChildren()) {
            descendants.add(child.getId());
            descendants.addAll(computeDescendants(child));
        }
        return descendants;
    }

    private int computeLevel(StructureNode node) {
        int level = 0;
        StructureNode current = node;
        while (current.getParent() != null) {
            level++;
            current = current.getParent();
        }
        return level;
    }
}