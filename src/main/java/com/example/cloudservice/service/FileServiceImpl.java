package com.example.cloudservice.service;

import com.example.cloudservice.dto.FileResponseDto;
import com.example.cloudservice.dto.NewFilenameRequestDto;
import com.example.cloudservice.exceptions.DeleteFileException;
import com.example.cloudservice.exceptions.FileUploadException;
import com.example.cloudservice.exceptions.GettingFileListException;
import com.example.cloudservice.exceptions.RenameFileException;
import com.example.cloudservice.model.entity.FileEntity;
import com.example.cloudservice.repository.FileRepository;
import com.example.cloudservice.security.JwtTokenUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final JwtTokenUtils jwtTokenUtils;

    public final static String SUCCESS_UPLOAD = "Success upload!";
    public final static String SUCCESS_DELETE = "Success deleted!";
    public final static String SUCCESS_RENAME = "Success rename!";

    public final static String ERROR_GETTING_FILE_LIST = "Error getting file list!";
    public final static String ERROR_UPLOAD_FILE = "Error upload file!";
    public final static String ERROR_DELETE_FILENAME = "Error delete file!";
    public final static String ERROR_RENAME_FILENAME = "File is not exist!";
    public final static String ERROR_UPLOAD_NOT_UNIQ_FILE = "Error upload file! Filename is not uniq";


    @Override
    public String uploadFile(String authToken, MultipartFile file) {
        try {
            FileEntity doublicateFilename = fileRepository.findByFilename(file.getOriginalFilename());
            if (isNull(doublicateFilename)) {
                fileRepository.save(new FileEntity(file.getOriginalFilename(),
                        file.getSize(), file.getContentType(), file.getBytes()));
                log.info("Successful upload file: {}", file.getOriginalFilename());
                return SUCCESS_UPLOAD;
            }
            log.error("Error upload file: {}. Filename is not uniq!", file.getOriginalFilename());
            throw new FileUploadException(ERROR_UPLOAD_NOT_UNIQ_FILE);
        } catch (IOException e) {
            log.error("Error upload file: {}. Please try again!", file.getOriginalFilename());
            throw new FileUploadException(ERROR_UPLOAD_FILE);
        }
    }

    @Override
    public String deleteFile(String authToken, String filename) {
        FileEntity file = fileRepository.findByFilename(filename);
        if (isNull(file)) {
            throw new DeleteFileException(ERROR_DELETE_FILENAME);
        }
        fileRepository.delete(file);
        return SUCCESS_DELETE;
    }


    @Override
    public byte[] getFile(String authToken, String filename) {
        FileEntity file = fileRepository.findByFilename(filename);
        return file.getBody();
    }


    @Override
    public String renameFile(String authToken, String filename, NewFilenameRequestDto nf) {
        FileEntity file = fileRepository.findByFilename(filename);
        if (isNull(file)) {
            log.error("Error rename file: {}. Please try again!", filename);
            throw new RenameFileException(ERROR_RENAME_FILENAME);
        }
        file.setFilename(nf.getFilename());
        fileRepository.save(file);
        log.info("Successful rename file: {}" , filename);
        return SUCCESS_RENAME;
    }

    @Override
    public List<FileResponseDto> getAllFiles(String authToken, Integer limit) {
        List<FileEntity> fileList = fileRepository.findAll();
        if (fileList.isEmpty()) {
            throw new GettingFileListException(ERROR_GETTING_FILE_LIST);
        }
        return fileList.stream().map(f -> new FileResponseDto(f.getFilename(), f.getSize()))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
