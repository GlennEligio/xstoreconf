package com.glenneligio.xstoreconf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DbConfiguration {
    private List<DbConfig> configuration;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DbConfig {
        private String brand_name;
        private String environment;
        private String environment_type;
        private Database database;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Database {
        private String host;
        private int port;
        private String username;
        private String password;
        private String name;
    }
}
