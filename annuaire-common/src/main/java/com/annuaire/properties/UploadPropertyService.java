package com.annuaire.properties;


import java.io.IOException;

public class UploadPropertyService {
    private static PropertyService service;
    private static UploadPropertyService instance;


    private UploadPropertyService() throws IOException {
        service = new PropertyService("upload");
    }


    public static UploadPropertyService getInstance() throws IOException{
        if (instance == null) {
            instance = new UploadPropertyService();
        }
        return instance;
    }


    public String getPath() {
        return service.getProperty("path");
    }
}
