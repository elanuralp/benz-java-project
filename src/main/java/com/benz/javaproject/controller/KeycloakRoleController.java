package com.benz.javaproject.controller;

import com.benz.javaproject.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class KeycloakRoleController {

    private final RoleService roleService;


    @PutMapping("/assign-role/user/{userId}")
    public ResponseEntity<?>  assignRole(@PathVariable String userId, @RequestParam String roleName){

        roleService.assignRoleToUser(userId, roleName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}

