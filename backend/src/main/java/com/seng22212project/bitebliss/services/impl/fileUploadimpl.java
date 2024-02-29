package com.seng22212project.bitebliss.services.impl;

import com.example.demo.services.fileUpload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class fileUploadimpl implements fileUpload {


    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {


//        String originalFilename = file.getOriginalFilename();
//        String randomImageName = UUID.randomUUID().toString();
//        String randomImageNameWithExtension = randomImageName.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
//        fullpath = path + File.separator + randomImageName + randomImageNameWithExtension;

        String originalFilename = file.getOriginalFilename();
        String fullpath = path + File.separator + originalFilename;

        File folderFile = new File(path);

        if (!folderFile.exists()) {
            if (folderFile.mkdir()) {
                System.out.println("Folder created successfully: " + path);
            } else {
                System.out.println("Failed to create the folder: " + path);
            }
        }

        Files.copy(file.getInputStream(), Paths.get(fullpath));


          return originalFilename;
//        return randomImageNameWithExtension;
    }
}
