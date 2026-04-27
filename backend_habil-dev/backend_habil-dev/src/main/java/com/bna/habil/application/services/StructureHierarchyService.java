package com.bna.habil.application.services;


import java.util.List;
import java.util.Set;

import com.bna.habil.domain.entities.Personnel;
import com.bna.habil.infrastructure.utils.StructureNode;

public interface StructureHierarchyService {
    boolean isDescendantOrSame(Integer ancestorId, Integer descendantId);

    StructureNode getNode(Integer id);

    Integer getStructureIdForUser(String userMatricule);

    List<Integer> getPath(Integer fromId, Integer toId);

    Set<Integer> getAllDescendants(Integer structureId);

    Set<Integer> getDirectChildren(Integer structureId);

    Integer getLevel(Integer structureId);

    Integer getTypeForStructure(Integer structureId);

    Set<String> getAllManagedUsers(String managerMatricule);

    List<Personnel> getPersonnelInStructure(Integer structureId);

    Set<String> getAllManagedUsersCins(String managerMatricule);
    Set<Integer> getManagedStructureIds(String managerMat);
}