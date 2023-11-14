package com.glenneligio.xstoreconf.service;

import com.glenneligio.xstoreconf.model.XStoreConf;
import com.glenneligio.xstoreconf.model.XStoreConfExcelEntry;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class XStoreConfExcelServiceImpl implements XStoreConfExcelService{

    private static Logger logger = LoggerFactory.getLogger(XStoreConfExcelServiceImpl.class);

    @Override
    public void listToExcel(List<XStoreConfExcelEntry> objects, String fileName) {
        try (Workbook workbook = new XSSFWorkbook()) {
            logger.info("Iterating to each TransactionType");
            Sheet sheet = workbook.createSheet("Comparison");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = new String[]{"STOREENT_ID", "NAME1", "VALUE1", "UX_VISIBILITY1", "LANGID1", "NAME", "VALUE","UX_VISIBILITY","LANGID" ,"NAME2","VALUE2", "UX_VISIBILITY2", "LANGID2"};

            for(int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            for (int j = 0; j < objects.size(); j++) {
                Row dataRow = sheet.createRow(j+1);
                XStoreConfExcelEntry excelEntry = objects.get(j);
                dataRow.createCell(0).setCellValue(excelEntry.getStoreEntId());
                dataRow.createCell(1).setCellValue(excelEntry.getName1());
                dataRow.createCell(2).setCellValue(excelEntry.getValue1());
                dataRow.createCell(3).setCellValue(Objects.isNull(excelEntry.getUxVisibility1()) ? "" : excelEntry.getUxVisibility1().toString());
                dataRow.createCell(4).setCellValue(Objects.isNull(excelEntry.getLangId1()) ? "" : excelEntry.getLangId1().toString());
                dataRow.createCell(5).setCellValue(excelEntry.isNameSame());
                dataRow.createCell(6).setCellValue(excelEntry.isValueSame());
                dataRow.createCell(7).setCellValue(excelEntry.isUxVisibilitySame());
                dataRow.createCell(8).setCellValue(excelEntry.isLangIdSame());
                dataRow.createCell(9).setCellValue(excelEntry.getName2());
                dataRow.createCell(10).setCellValue(excelEntry.getValue2());
                dataRow.createCell(11).setCellValue(Objects.isNull(excelEntry.getUxVisibility2()) ? "" : excelEntry.getUxVisibility2().toString());
                dataRow.createCell(12).setCellValue(Objects.isNull(excelEntry.getLangId2()) ? "" : excelEntry.getLangId2().toString());
            }

            File file = new File(fileName);
            if(file.createNewFile()) {
                FileOutputStream outputStream = new FileOutputStream(file);
                workbook.write(outputStream);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
            if(!Objects.equals(x1.getStoreEntId(), x2.getStoreEntId())) {
                // whoever have lower storeEntId, the list where that entry below will be added
                if(x1.getStoreEntId() > x2.getStoreEntId()) {
                    sortedXStoreConfList1.add(currIteration, null);
                }
                else {
                    sortedXStoreConfList2.add(currIteration, null);
                }
            } else {
                if(!Objects.equals(x1.getName(), x2.getName())) {
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

    @Override
    public void createExcelFileFromTwoXStoreConfList(List<XStoreConf> x1, List<XStoreConf> x2, String fileName) {
        List<XStoreConfExcelEntry> excelEntries = getXStoreExcelEntries(x1, x2);
        listToExcel(excelEntries, fileName);
    }
}
