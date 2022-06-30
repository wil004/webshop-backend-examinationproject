package com.novi.webshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentInputDto {
    private String id;
    private String fileName;
    private String downloadUrl;
    private String fileType;
    private long fileSize;
}
