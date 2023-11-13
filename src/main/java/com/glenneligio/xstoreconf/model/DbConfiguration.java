package com.glenneligio.xstoreconf.model;

import lombok.Data;

@Data
public class DbConfiguration {
    private String brand_name;
    private String environment;
    private String environment_type;
    private Database database;
}
