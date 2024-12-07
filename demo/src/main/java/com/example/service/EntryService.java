package com.example.service;

import com.example.entity.EntryEntity;
import com.example.entity.ImageEntity;
import com.example.entity.UserEntity;
import com.example.repository.EntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EntryService {

    private final EntryRepository entryRepository;
    private final ImageService imageService;

    public EntryService(EntryRepository entryRepository, ImageService imageService) {
        this.entryRepository = entryRepository;
        this.imageService = imageService;
    }

    public EntryEntity createEntry(String title,
                                   String content,
                                   String summary,
                                   UserEntity user) {
        EntryEntity entry = new EntryEntity();
        entry.setTitle(title);
        entry.setContent(content);
        entry.setSummary(summary);
        entry.setUser(user);
        return entryRepository.save(entry);
    }

    public Optional<EntryEntity> editEntry(Long entryId,
                                           String title,
                                           String content,
                                           String summary) {
        Optional<EntryEntity> entryOptional = entryRepository.findById(entryId);
        if (entryOptional.isPresent()) {
            EntryEntity entry = entryOptional.get();
            entry.setTitle(title);
            entry.setContent(content);
            entry.setSummary(summary);
            return Optional.of(entryRepository.save(entry));
        }
        return Optional.empty();
    }

    public boolean deleteEntry(Long entryId, UserEntity user) {
        Optional<EntryEntity> entryOptional = entryRepository.findById(entryId);
        if (entryOptional.isPresent() && entryOptional.get().getUser().equals(user)) {
            entryRepository.delete(entryOptional.get());
            return true;
        }
        return false;
    }

    public boolean addImages(Long entryId, List<MultipartFile> images, UserEntity user) {
        Optional<EntryEntity> entryOptional = entryRepository.findById(entryId);
        if (entryOptional.isPresent() && entryOptional.get().getUser().equals(user)) {
            EntryEntity entry = entryOptional.get();
            images.forEach(image -> imageService.saveImage(image, entry));
            return true;
        }
        return false;
    }

    public boolean deleteImage(Long imageId, UserEntity user) {
        Optional<ImageEntity> imageOptional = imageService.findById(imageId);
        if (imageOptional.isPresent()) {
            ImageEntity image = imageOptional.get();
            if (image.getEntry().getUser().equals(user)) {
                imageService.deleteImage(image);
                return true;
            }
        }
        return false;
    }
}
