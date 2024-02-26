package com.example.cloudservice.service;

import com.example.cloudservice.dto.FileResponseDto;
import com.example.cloudservice.dto.NewFilenameRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    String uploadFile(String authToken, MultipartFile file);

    String deleteFile(String authToken, String filename);


    byte[] getFile(String authToken, String filename);

    String renameFile(String authToken, String filename, NewFilenameRequestDto newFilename);

    List<FileResponseDto> getAllFiles(String authToken, Integer limit);
}