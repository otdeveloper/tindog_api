package com.key.tindog.service;


import com.key.tindog.exception.FileStorageException;
import com.key.tindog.exception.MyFileNotFoundException;
import com.key.tindog.model.Image;
import com.key.tindog.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageStorageService {

    private final ImageRepository imageRepository;

    public ImageStorageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Image dbFile = new Image(fileName, file.getContentType(), file.getBytes());

            return imageRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Image getFile(String fileId) {
        return imageRepository.findById(Long.parseLong(fileId))
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
}