package com.test.controller;

import com.test.dto.request.PermissionRequest;
import com.test.dto.response.PermissionResponse;
import com.test.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling permission-related HTTP requests.
 * This class provides endpoints for creating, updating, viewing, and deleting permissions.
 */

@RestController
@RequestMapping("api/v1/permissions")
@Slf4j
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping("/create")
    public ResponseEntity<PermissionResponse> create(@Validated @RequestBody PermissionRequest request) {
        log.info("(create) Request : {}", request);
        return new ResponseEntity<>(permissionService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PermissionResponse> update(@Validated @RequestBody PermissionRequest request, @PathVariable Long id) {
        log.info("(update) Request : {}", request);
        return new ResponseEntity<>(permissionService.update(id, request), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PermissionResponse>> viewAll() {
        return new ResponseEntity<>(permissionService.viewAll(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        permissionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
