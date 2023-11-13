package com.glenneligio.xstoreconf.model;

import lombok.Data;

@Data
public class Database {
    private String hostname;
    private int port;
    private String username;
    private String password;
    private String name;
}
