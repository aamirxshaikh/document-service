package com.example.documentservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class FileMetaData {
  @Id
  private String id;

  private String fileName;

  private String contentType;

  private long size;

  private String gridFsId;

  public FileMetaData(String fileName, String contentType, long size, String gridFsId) {
    this.fileName = fileName;
    this.contentType = contentType;
    this.size = size;
    this.gridFsId = gridFsId;
  }
}
