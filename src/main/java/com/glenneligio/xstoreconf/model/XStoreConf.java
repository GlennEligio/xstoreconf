package com.glenneligio.xstoreconf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XStoreConf {
    private int storeEntId;
    private String name;
    private String value;
    private int languageId;
    private boolean uxVisibility;
}
