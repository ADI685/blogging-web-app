package com.adi685.blogging_web_app.service.impl;

import com.adi685.blogging_web_app.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// Ensure the path ends with a separator
		if (!path.endsWith(File.separator)) {
			path = path + File.separator;
		}

		// Get original file name
		String originalFileName = file.getOriginalFilename();

		// Full path to save the file
		String randomFileName = UUID.randomUUID().toString();
		assert originalFileName != null;
		String newFileName = randomFileName.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
		String filePath = path + newFileName;

		// Create directory if not present
		File directory = new File(path);
		if (!directory.exists()) {
			boolean dirCreated = directory.mkdirs();
			if (dirCreated) {
				log.debug("Created new directory: {}", path);
			}
			else {
				log.error("Failed to create directory: {}", path);
				return String.format("Failed to create directory for file %s", originalFileName);
			}
		}

		// Copy file to the destination path
		Path destinationPath = Paths.get(filePath);

		Files.copy(file.getInputStream(), destinationPath);

		return newFileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		if (!path.endsWith(File.separator)) {
			path = path + File.separator;
		}
		String filePath = path + fileName;
		return new FileInputStream(filePath);
	}

}
