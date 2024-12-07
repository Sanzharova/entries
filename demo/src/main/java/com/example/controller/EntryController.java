package com.example.controller;

import com.example.dto.EntryRequest;
import com.example.dto.EntryResponse;
import com.example.entity.UserEntity;
import com.example.service.EntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entries")
public class EntryController {

    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }


    @GetMapping
    public ResponseEntity<List<EntryResponse>> getAllEntries() {
        List<EntryResponse> entries = entryService.getAll();
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntryResponse> getEntryById(@PathVariable Long id) {
        Optional<EntryResponse> entry = entryService.getById(id);
        return entry.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Long> createEntry(
            @RequestBody EntryRequest entryDTO,
            @AuthenticationPrincipal UserEntity user) {
        Long entryId = entryService.createEntry(entryDTO.getTitle(), entryDTO.getContent(), entryDTO.getSummary(), user);
        return ResponseEntity.ok(entryId);
    }

    @PutMapping("/{entryId}")
    public ResponseEntity<Long> editEntry(
            @PathVariable Long entryId,
            @RequestBody EntryRequest entryDTO) {
        return entryService.editEntry(entryId, entryDTO.getTitle(), entryDTO.getContent(), entryDTO.getSummary())
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
            @RequestBody List<MultipartFile> images,
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
