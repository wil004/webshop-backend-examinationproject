package com.novi.webshop.controller;

import com.novi.webshop.dto.AttachmentInputDto;
import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.model.Attachment;
import com.novi.webshop.model.Product;
import com.novi.webshop.services.AttachmentService;
import com.novi.webshop.services.AttachmentServiceImpl;
import com.novi.webshop.services.ProductService;
import com.novi.webshop.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class AttachmentController {

    private final AttachmentService attachmentService;
    private final ProductService productService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService, ProductService productService, ProductService productService1) {
        this.attachmentService = attachmentService;
        this.productService = productService;
    }

    @PostMapping("attachment/upload")
    public AttachmentInputDto uploadPictureFile(@RequestParam(value = "file")MultipartFile file) throws Exception {
        Attachment attachment = null;
        attachment = attachmentService.saveAttachment(file);
        String downloadUrl = "";
        downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(attachment.getId())
                .toUriString();
        return new AttachmentInputDto(attachment.getId(), attachment.getFileName(), downloadUrl, file.getContentType(), file.getSize());
    }

    @PutMapping("attachment/product={productId}/file={fileId}")
    public ResponseEntity<ProductDto> uploadProductPictureUrl(@PathVariable Long productId , @PathVariable String fileId){
        return ResponseEntity.ok(productService.uploadPicture(productId, "http://localhost:8080/download/" +  fileId));
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadPictureFile(@PathVariable String fileId) throws Exception {
        Attachment attachment = null;
        attachment = attachmentService.getAttachment(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileName() +
                        "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }
}
