package com.glenneligio.xstoreconf.service;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.util.List;

public interface ExcelService<T>{
    void listToExcel(List<T> objects, String fileName);
}
