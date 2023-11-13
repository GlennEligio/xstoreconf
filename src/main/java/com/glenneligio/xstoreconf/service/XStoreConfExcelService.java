package com.glenneligio.xstoreconf.service;

import com.glenneligio.xstoreconf.model.XStoreConf;
import com.glenneligio.xstoreconf.model.XStoreConfExcelEntry;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.util.List;

public interface XStoreConfExcelService extends ExcelService<XStoreConfExcelEntry>{

    List<XStoreConfExcelEntry> getXStoreExcelEntries(List<XStoreConf> xStoreConfList1, List<XStoreConf> xStoreConfList2);
    void createExcelFileFromTwoXStoreConfList(List<XStoreConf> x1, List<XStoreConf> x2, String fileName);
}
