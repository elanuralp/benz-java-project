package com.benz.javaproject.service.impl;

import com.benz.javaproject.service.KeycloakUserService;
import com.benz.javaproject.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.RolesRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements  RoleService{

    @Value("${keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;

    private final KeycloakUserService keycloakUserService;

    @Override
    public void assignRoleToUser(String userId, String roleName) {
        UserResource userResource = keycloakUserService.getUserResource(userId);
        if (userResource == null) {
            throw new IllegalArgumentException("User with id " + userId + " does not exist");
        }

        RolesResource rolesResource = getRolesResource();
        RoleRepresentation roleRepresentation = rolesResource.get(roleName).toRepresentation();
        if (roleRepresentation == null) {
            throw new IllegalArgumentException("Role with name " + roleName + " does not exist");
        }

        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
    }

    private RolesResource getRolesResource(){
        return  keycloak.realm(realm).roles();
    }

}
