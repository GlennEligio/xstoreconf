package com.glenneligio.xstoreconf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XStoreConfExcelEntry {
    private int storeEntId;
    private String name1;
    private String name2;
    private String value1;
    private String value2;
    private boolean uxVisibility1;
    private boolean uxVisibility2;
    private int langId1;
    private int langId2;

    private boolean isNameSame;
    private boolean isValueSame;
    private boolean isUxVisibilitySame;
    private boolean isLangIdSame;

    public XStoreConfExcelEntry(XStoreConf xStoreConf1, XStoreConf xStoreConf2) {
        setStoreEntId(Objects.nonNull(xStoreConf1) ? xStoreConf1.getStoreEntId() : xStoreConf2.getStoreEntId());

        if(Objects.nonNull(xStoreConf1)) {
            setName1(xStoreConf1.getName());
            setValue1(xStoreConf1.getValue());
            setUxVisibility1(xStoreConf1.isUxVisibility());
            setLangId1(xStoreConf1.getLanguageId());
        }

        if(Objects.nonNull(xStoreConf2)) {
            setName2(xStoreConf2.getName());
            setValue2(xStoreConf2.getValue());
            setUxVisibility2(xStoreConf2.isUxVisibility());
            setLangId2(xStoreConf2.getLanguageId());
        }

        setNameSame(Objects.equals(name1, name2));
        setValueSame(Objects.equals(value1, value2));
        setUxVisibilitySame(uxVisibility1 == uxVisibility2);
        setLangIdSame(langId1 == langId2);
    }
}
