package com.seng22212project.bitebliss.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface fileUpload {
    public String uploadImage(String path, MultipartFile file) throws IOException;
}
