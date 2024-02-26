package com.example.cloudservice.controller;

import com.example.cloudservice.dto.FileResponseDto;
import com.example.cloudservice.dto.NewFilenameRequestDto;
import com.example.cloudservice.service.CheckTokenService;
import com.example.cloudservice.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


import static com.example.cloudservice.controller.PathConstant.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final CheckTokenService checkTokenService;

    @PostMapping(FILE)
    public ResponseEntity<String> uploadFile(@RequestHeader(value = "auth-token") String authToken,
                                             @RequestParam(value = "file") MultipartFile file) {
        checkTokenService.testToken(authToken);
        String result = fileService.uploadFile(authToken, file);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(FILE)
    public ResponseEntity<Void> deleteFile(@RequestHeader(value = "auth-token") String authToken,
                                             @RequestParam(value = "filename") String filename) {
        checkTokenService.testToken(authToken);
        String result = fileService.deleteFile(authToken, filename);
        return ResponseEntity.ok().build();
    }

    @GetMapping(FILE)
    public byte[] getFile(@RequestHeader("auth-token") String authToken,
                                              @RequestParam("filename") String filename) {
        checkTokenService.testToken(authToken);
        return fileService.getFile(authToken, filename);
    }


    @PutMapping(FILE)
    public ResponseEntity<Void> renameFile(@RequestHeader("auth-token") String authToken,
                                             @RequestParam String filename,
                                             @RequestBody NewFilenameRequestDto newFilename) {
        checkTokenService.testToken(authToken);
        String result = fileService.renameFile(authToken, filename, newFilename);
        return ResponseEntity.ok().build();
    }


    @GetMapping(LIST)
    public List<FileResponseDto> getAllFiles(@RequestHeader("auth-token") String authToken,
                                             @RequestParam("limit") Integer limit) {
        checkTokenService.testToken(authToken);
        return fileService.getAllFiles(authToken, limit);
    }
}