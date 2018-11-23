package com.key.tindog.controller;

import com.key.tindog.exception.FileStorageException;
import com.key.tindog.model.Image;
import com.key.tindog.model.Location;
import com.key.tindog.model.Profile;
import com.key.tindog.payload.UploadFileResponse;
import com.key.tindog.service.ProfileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
public class FileController {

    private final ProfileService profileService;

    public FileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/uploadProfile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,
                                         @RequestParam("firstName") String firstName,
                                         @RequestParam("lastName") String lastName,
                                         @RequestParam(value = "location") double[] location) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Image image;
        try {
            image = new Image(fileName, file.getContentType(), file.getBytes());
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }


        profileService.saveProfile(new Profile(firstName, lastName, image, new Location(location[0], location[1])));

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(String.valueOf(image.getId()))
                .toUriString();

        return new UploadFileResponse(image.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }


    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        Profile profile = profileService.findById(Long.parseLong(fileId));


        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(profile.getImage().getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profile.getImage().getFileName() + "\"")
                .body(new ByteArrayResource(profile.getImage().getData()));
    }

}
