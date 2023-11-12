package com.glenneligio.xstoreconf.service;

import com.glenneligio.xstoreconf.model.XStoreConf;
import com.glenneligio.xstoreconf.model.XStoreConfExcelEntry;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface XStoreConfExcelService extends ExcelService<XStoreConfExcelEntry>{
    List<XStoreConfExcelEntry> getXStoreExcelEntries(List<XStoreConf> xStoreConfList1, List<XStoreConf> xStoreConfList2);
}
