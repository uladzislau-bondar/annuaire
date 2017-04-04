package com.annuaire.properties;


public class UploadPropertyService {
    private static PropertyService service;
    private static UploadPropertyService instance;


    private UploadPropertyService() {
        service = new PropertyService("upload");
    }


    public static UploadPropertyService getInstance(){
        if (instance == null) {
            instance = new UploadPropertyService();
        }
        return instance;
    }


    public String getPath() {
        return service.getProperty("path");
    }
}
