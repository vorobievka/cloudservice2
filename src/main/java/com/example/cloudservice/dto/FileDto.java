package com.example.cloudservice.dto;

import com.example.cloudservice.model.entity.FileEntity;
import lombok.Data;

@Data
public class FileDto {

    private FileEntity filename;

    private Long size;

    private String type;

    private byte[] body;
}