package com.sacc.assessment.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 * Created by 林夕
 * Date 2021/8/31 23:08
 */
public class ExcelUtils {

    /**
     * excel值处理
     * @param hssfCell
     * @return
     */
    public static Object getXSSFValue(XSSFCell hssfCell) {
        if(hssfCell.getCellType() == CellType.NUMERIC) {
            return hssfCell.getNumericCellValue();    //数字
        }else if(hssfCell.getCellType() == CellType.BOOLEAN) {
            return hssfCell.getBooleanCellValue();    //boolean
        }else if(hssfCell.getCellType() == CellType.ERROR){
            return hssfCell.getErrorCellValue();      //故障
        }else if(hssfCell.getCellType() == CellType.FORMULA){
            return hssfCell.getCellFormula();         //公式
        }else if(hssfCell.getCellType() == CellType.BLANK) {
            return "";                                //空值
        }else {
            return hssfCell.getStringCellValue();     //字符串
        }

    }

    /**
     * excel值处理
     * @param hssfCell
     * @return
     */
    public static Object getValue(Cell hssfCell) {
        if(hssfCell.getCellType()== CellType.NUMERIC) {
            return hssfCell.getNumericCellValue();   //数字
        }else if(hssfCell.getCellType()==CellType.BOOLEAN) {
            return hssfCell.getBooleanCellValue();   //boolean
        }else if(hssfCell.getCellType()==CellType.ERROR){
            return hssfCell.getErrorCellValue();     //故障
        }else if(hssfCell.getCellType()==CellType.FORMULA){
            return hssfCell.getCellFormula();        //公式
        }else if(hssfCell.getCellType() == CellType.BLANK) {
            return ""; 	                             //空值
        }else {
            return hssfCell.getStringCellValue();    //字符串
        }
    }

}
