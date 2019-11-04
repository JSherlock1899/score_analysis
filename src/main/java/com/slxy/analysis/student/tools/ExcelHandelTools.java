package com.slxy.analysis.student.tools;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/*
操作excel类通用方法
 */
public class ExcelHandelTools {

    /*
    读取excel内容并根据泛型返回集合
     */
    public static CopyOnWriteArrayList<Object> readExcel(Object obj,String path) throws IOException {
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
        HSSFWorkbook sheets = new HSSFWorkbook(new FileInputStream(path));
        //获取第一页
        HSSFSheet sheetAt = sheets.getSheetAt(0);
        //获取最大行数
        int physicalNumberOfRows = sheetAt.getPhysicalNumberOfRows();
        for (int i = 1; i < physicalNumberOfRows; i++) {
            HSSFRow row = sheetAt.getRow(i);
            //获取最大列
            int physicalNumberOfCells = row.getPhysicalNumberOfCells();
            for (int j = 0; j < physicalNumberOfCells; j++) {
                //获取excel每一列信息
                System.out.print(row.getCell(j)+"\t");

            }
            System.out.println();
        }
        return list;
    }
}
