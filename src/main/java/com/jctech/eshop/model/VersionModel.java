package com.jctech.eshop.model;

import lombok.Data;

@Data
public class VersionModel  {
  private String version = null;
  private Integer major = null;
  private Integer minor = null;
  private Integer patch = null;
}
