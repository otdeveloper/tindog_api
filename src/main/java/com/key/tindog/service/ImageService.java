package com.key.tindog.service;

import com.key.tindog.exception.FileStorageException;
import com.key.tindog.model.Image;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

	public static Image parseToImage(MultipartFile file) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		Image image;

		try {
			image = new Image(fileName, file.getContentType(), file.getBytes());
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
		return image;
	}
}
