package com.example.documentservice.service;

import com.example.documentservice.entity.FileMetaData;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FileService {
  private final Logger log = Logger.getLogger(FileService.class.getName());

  private final GridFsTemplate gridFsTemplate;
  private final MongoTemplate mongoTemplate;

  @Autowired
  public FileService(GridFsTemplate gridFsTemplate, MongoTemplate mongoTemplate) {
    this.gridFsTemplate = gridFsTemplate;
    this.mongoTemplate = mongoTemplate;
  }

  public String uploadFile(MultipartFile file) throws IOException {
    log.log(Level.INFO, "Received file: {0}", file.getOriginalFilename());

    InputStream inputStream = file.getInputStream();

    ObjectId fileId = gridFsTemplate.store(inputStream, file.getOriginalFilename(), file.getContentType());

    // Save file metadata
    FileMetaData metadata = new FileMetaData(
            file.getOriginalFilename(),
            file.getContentType(),
            file.getSize(),
            fileId.toString()
    );
    mongoTemplate.save(metadata);
    log.log(Level.INFO, "File uploaded successfully with ID: {0}", fileId);
    return fileId.toString();
  }
}
