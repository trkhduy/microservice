package com.test.controller;

import com.test.dto.request.RoleRequest;
import com.test.dto.response.RoleResponse;
import com.test.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling role-related HTTP requests.
 * This class provides endpoints for creating, updating, viewing, and deleting roles.
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<RoleResponse> create(@Validated @RequestBody RoleRequest request) {
        log.info("(create) Request : {}", request);
        return new ResponseEntity<>(roleService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RoleResponse> update(@Validated @RequestBody RoleRequest request, @PathVariable Long id) {
        log.info("(update) Request : {}", request);
        return new ResponseEntity<>(roleService.update(id, request), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoleResponse>> viewAll() {
        return new ResponseEntity<>(roleService.viewAll(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
