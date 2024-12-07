package com.example.service;

import com.example.entity.EntryEntity;
import com.example.entity.ImageEntity;
import com.example.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final Path rootLocation = Paths.get("./images");

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    public void saveImage(MultipartFile file, EntryEntity entry) {
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path destinationFile = rootLocation.resolve(filename).normalize();
        try {
            Files.copy(file.getInputStream(), destinationFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
        ImageEntity image = new ImageEntity();
        image.setEntry(entry);
        imageRepository.save(image);
    }

    public Optional<ImageEntity> findById(Long imageId) {
        return imageRepository.findById(imageId);
    }

    public void deleteImage(ImageEntity image) {
        try {
            Files.deleteIfExists(rootLocation.resolve(image.getImageUrl()));
            imageRepository.delete(image);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file " + image.getImageUrl(), e);
        }
    }
}
