package com.annuaire.enums;


public enum PhoneType {
    HOME("HOME"),
    MOBILE("MOBILE"),
    UNKNOWN("UNKNOWN");

    private String value;

    PhoneType(String value){
        this.value = value;
    }

    public String value(){
        return value == null ? UNKNOWN.value : value;
    }

}
