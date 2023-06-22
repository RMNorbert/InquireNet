package com.rmnnorbert.InquireNet.dao.model.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class User {

    public User(long id, String status, String name, String password, LocalDateTime registration_date) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.password = password;
        this.registration_date = registration_date;
    }
    private long id;
    private String status;
    private String name;
    private String password;
    private LocalDateTime registration_date;

    public User() {}

}
