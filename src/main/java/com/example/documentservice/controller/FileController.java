package com.example.documentservice.controller;

import com.example.documentservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/files")
public class FileController {
  private final Logger log = Logger.getLogger(FileController.class.getName());

  private final FileService fileService;

  @Autowired
  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    log.log(Level.INFO, "Received file: {0}", file.getOriginalFilename());
    try {
      String fileId = fileService.uploadFile(file);
      return ResponseEntity.ok("File uploaded successfully with ID: " + fileId);
    } catch (IOException e) {
      return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
    }
  }
}
