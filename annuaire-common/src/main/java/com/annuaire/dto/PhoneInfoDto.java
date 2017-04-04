package com.annuaire.dto;


import com.annuaire.enums.PhoneType;

public class PhoneInfoDto {
    private Long id;
    private String number;
    private String type;
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type.value();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
