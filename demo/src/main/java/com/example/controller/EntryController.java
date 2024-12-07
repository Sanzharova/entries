package com.example.controller;

import com.example.entity.EntryEntity;
import com.example.entity.UserEntity;
import com.example.service.EntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/entries")
public class EntryController {

    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @PostMapping
    public ResponseEntity<EntryEntity> createEntry(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String summary,
            @AuthenticationPrincipal UserEntity user) {
        EntryEntity entry = entryService.createEntry(title, content, summary, user);
        return ResponseEntity.ok(entry);
    }

    @PutMapping("/{entryId}")
    public ResponseEntity<EntryEntity> editEntry(
            @PathVariable Long entryId,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String summary) {
        return entryService.editEntry(entryId, title, content, summary)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{entryId}")
    public ResponseEntity<Void> deleteEntry(
            @PathVariable Long entryId,
            @AuthenticationPrincipal UserEntity user) {
        if (entryService.deleteEntry(entryId, user)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{entryId}/images")
    public ResponseEntity<Void> addImages(
            @PathVariable Long entryId,
            @RequestParam List<MultipartFile> images,
            @AuthenticationPrincipal UserEntity user) {
        if (entryService.addImages(entryId, images, user)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long imageId,
            @AuthenticationPrincipal UserEntity user) {
        if (entryService.deleteImage(imageId, user)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
