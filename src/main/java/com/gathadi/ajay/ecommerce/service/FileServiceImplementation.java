package com.gathadi.ajay.ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImplementation implements FileService{
    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename();

        String randomId = UUID.randomUUID().toString();

        if(originalFilename == null || originalFilename.isBlank()){
            throw new IllegalArgumentException("Uploaded file must have a valid name");
        }

        int dotIndex = originalFilename.lastIndexOf('.');
        if(dotIndex == -1){
            throw new IllegalArgumentException("Uploaded file must have an extension");
        }

        String fileName = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;

        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdirs();
        }

        Files.copy(image.getInputStream(), Paths.get(filePath));
        return fileName;
    }
}
