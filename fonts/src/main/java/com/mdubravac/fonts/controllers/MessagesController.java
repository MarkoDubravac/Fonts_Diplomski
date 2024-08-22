package com.mdubravac.fonts.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MessagesController {

    @GetMapping("/messages/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<String>> messagesAdmin() {
        System.out.println("BBBBBBBB");
        return ResponseEntity.ok(Arrays.asList("THIRD", "FORTH"));
    }

    @GetMapping("/messages/superadmin")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<List<String>> messagesSuperAdmin() {
        System.out.println("CCCCCCCCC");
        return ResponseEntity.ok(Arrays.asList("Fifht", "Sixth"));
    }
}
