package com.glenneligio.xstoreconf.service;

import com.glenneligio.xstoreconf.model.XStoreConf;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XStoreConfServiceImpl implements XStoreConfService {

    // for first database
    private static List<XStoreConf> testXStoreConfList1 = new ArrayList<>();
    // for second database
    private static List<XStoreConf> testXStoreConfList2 = new ArrayList<>();

    static {
        // first database
        testXStoreConfList1.add(new XStoreConf(0, "NAME1", "VALUE1", -1, true));
        testXStoreConfList1.add(new XStoreConf(0, "NAME2", "VALUE2", -1, true));
        testXStoreConfList1.add(new XStoreConf(0, "NAME3", "VALUE3", -1, true));
        testXStoreConfList1.add(new XStoreConf(80206, "NAME4", "VALUE4", -1, true));
        testXStoreConfList1.add(new XStoreConf(80206, "NAME5", "VALUE5", -1, true));
        testXStoreConfList1.add(new XStoreConf(80999, "NAME6", "VALUE6", -1, true));

        // second database
        testXStoreConfList2.add(new XStoreConf(0, "NAME1", "VALUE1", -1, true));
        testXStoreConfList2.add(new XStoreConf(0, "NAME2", "VALUE2", -1, true));
        testXStoreConfList2.add(new XStoreConf(0, "NAME3", "VALUE3", -1, true));
        testXStoreConfList2.add(new XStoreConf(80206, "NAME4", "VALUE4", -1, true));
        testXStoreConfList2.add(new XStoreConf(80206, "NAME5", "VALUE5", -1, true));
        testXStoreConfList2.add(new XStoreConf(80205, "NAME7", "VALUE7", -1, true));
    }

    @Override
    public List<XStoreConf> getAllXStoreConf() {
        return testXStoreConfList1;
    }

    @Override
    public List<XStoreConf> getAllXStoreConfByStoreEntId(int storeEntId) {
        return testXStoreConfList1.stream().filter(x -> x.getStoreEntId() == storeEntId).toList();
    }

    @Override
    public List<Integer> getAllStoreEntId() {
        return testXStoreConfList1.stream().map(XStoreConf::getStoreEntId).distinct().collect(Collectors.toList());
    }

    public List<XStoreConf> getAllXStoreConf2() {
        return testXStoreConfList2;
    }

    public List<XStoreConf> getAllXStoreConfByStoreEntId2(int storeEntId) {
        return testXStoreConfList2.stream().filter(x -> x.getStoreEntId() == storeEntId).toList();
    }

    public List<Integer> getAllStoreEntId2() {
        return testXStoreConfList2.stream().map(XStoreConf::getStoreEntId).distinct().toList();
    }
}
