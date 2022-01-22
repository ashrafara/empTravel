package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.utl.FileTools;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FileResource {

    public FileResource() {}

    @GetMapping("/public/file/download/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) {
        if (fileName.contains(".png")) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(FileTools.download(fileName));
        } else if (fileName.contains(".pdf")) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(FileTools.download(fileName));
        } else {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(FileTools.download(fileName));
        }
    }
}
