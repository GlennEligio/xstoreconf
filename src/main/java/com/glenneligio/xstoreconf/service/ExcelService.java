package com.glenneligio.xstoreconf.service;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ExcelService<T>{
    ByteArrayInputStream listToExcel(List<T> objects);
}
