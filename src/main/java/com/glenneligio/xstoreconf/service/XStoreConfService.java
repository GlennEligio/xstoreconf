package com.glenneligio.xstoreconf.service;

import com.glenneligio.xstoreconf.model.XStoreConf;

import java.util.List;

public interface XStoreConfService {
    List<XStoreConf> getAllXStoreConf();
    List<XStoreConf> getAllXStoreConfByStoreEntId(int storeEntId);

    List<Integer> getAllStoreEntId();
}
