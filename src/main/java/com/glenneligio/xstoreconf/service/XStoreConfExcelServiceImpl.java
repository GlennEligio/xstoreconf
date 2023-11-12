package com.glenneligio.xstoreconf.service;

import com.glenneligio.xstoreconf.XstoreconfApplication;
import com.glenneligio.xstoreconf.model.XStoreConf;
import com.glenneligio.xstoreconf.model.XStoreConfExcelEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class XStoreConfExcelServiceImpl implements XStoreConfExcelService{

    private static Logger logger = LoggerFactory.getLogger(XStoreConfExcelServiceImpl.class);

    @Override
    public ByteArrayInputStream listToExcel(List<XStoreConfExcelEntry> objects) {
        return null;
    }

    @Override
    public List<XStoreConfExcelEntry> getXStoreExcelEntries(List<XStoreConf> xStoreConfList1, List<XStoreConf> xStoreConfList2) {
        // This assumes the two xStoreConf list are sorted by STOREENT_ID first, then NAME second
        // Handle sorting either in SQL used to fetch these, or using Stream API before calling this method
        List<XStoreConf> sortedXStoreConfList1 = new ArrayList<>(xStoreConfList1.stream().sorted((x1, x2) -> {
            int storeEntIdCompare = x1.getStoreEntId() - x2.getStoreEntId();
            int nameCompare = x1.getName().compareTo(x2.getName());
            return storeEntIdCompare == 0 ? nameCompare : storeEntIdCompare;
        }).toList());

        List<XStoreConf> sortedXStoreConfList2 = new ArrayList<>(xStoreConfList2.stream().sorted((x1, x2) -> {
            int storeEntIdCompare = x1.getStoreEntId() - x2.getStoreEntId();
            int nameCompare = x1.getName().compareTo(x2.getName());
            return storeEntIdCompare == 0 ? nameCompare : storeEntIdCompare;
        }).toList());

        List<XStoreConfExcelEntry> result = new ArrayList<>();
        int currIteration = 0;
        while (currIteration < sortedXStoreConfList2.size() || currIteration < sortedXStoreConfList1.size()) {
            logger.info("Sorted XSTORECONF list 1 size: {}", sortedXStoreConfList1.size());
            logger.info("Sorted XSTORECONF list 2 size: {}", sortedXStoreConfList2.size());
            logger.info("Current iteration: {}", currIteration);
            XStoreConf x1 = null;
            XStoreConf x2 = null;
            try {
                x1 = sortedXStoreConfList1.get(currIteration);
                x2 = sortedXStoreConfList2.get(currIteration);
            } catch (IndexOutOfBoundsException ex) {
                logger.info("No more entries to process, handling remaining entries");
            }

            logger.info("X1 {}", x1);
            logger.info("X2 {}", x2);

            // handle remaining entry, for case where all entries on one list is already processed
            if(Objects.isNull(x1)) {
                result.add(new XStoreConfExcelEntry(null, sortedXStoreConfList2.get(currIteration)));
                currIteration++;
                continue;
            }
            if(Objects.isNull(x2)) {
                result.add(new XStoreConfExcelEntry(sortedXStoreConfList1.get(currIteration), null));
                currIteration++;
                continue;
            }

            // check if different storeEntId
            if(x1.getStoreEntId() != x2.getStoreEntId()) {
                // whoever have lower storeEntId, the list where that entry below will be added
                if(x1.getStoreEntId() > x2.getStoreEntId()) {
                    sortedXStoreConfList1.add(currIteration, null);
                }
                else {
                    sortedXStoreConfList2.add(currIteration, null);
                }
            } else {
                if(!x1.getName().equals(x2.getName())) {
                    if(x1.getName().compareTo(x2.getName()) > 0) {
                        sortedXStoreConfList1.add(currIteration, null);
                    } else {
                        sortedXStoreConfList2.add(currIteration, null);
                    }
                }
            }

            result.add(new XStoreConfExcelEntry(sortedXStoreConfList1.get(currIteration), sortedXStoreConfList2.get(currIteration)));

            currIteration++;
        }

        return result;
    }
}
