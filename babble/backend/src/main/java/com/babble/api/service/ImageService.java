package com.babble.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ImageService {

    boolean userImageUpload(String email, MultipartFile file);
    byte[] getUserImage(String email) throws IOException;
    byte[] getRoomImage(String thumbnail) throws IOException;
    String roomImageUpload(MultipartFile file);
}
