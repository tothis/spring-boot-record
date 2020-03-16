package com.example.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.example.model.User;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/3/12 13:17
 * @description
 */
@Controller
public class TestController {

    private List<User> users;

    {
        users = new ArrayList<User>() {{
            add(new User(2L, "frank"));
            add(new User(1L, "李磊"));
        }};
    }

    private final String fileName = "excel.xlsx";

    @GetMapping("export1")
    public void export1(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbookSheet = new HSSFWorkbook();
        HSSFSheet sheet = workbookSheet.createSheet("表格标题");

        String[] headers = {"序号", "用户名称"};

        // 标题所占列 从0开始 如下为占5列
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, 4);
        sheet.addMergedRegion(region);
        HSSFRow rowTitle = sheet.createRow(0);
        Cell oneCell = rowTitle.createCell(0);
        oneCell.setCellValue("综合满意度评分结果表"); // 设置标题内容

        // 合并的单元格样式
        HSSFCellStyle cellStyle = workbookSheet.createCellStyle();
        oneCell.setCellStyle(cellStyle);
        HSSFRow row = sheet.createRow(1);
        // 在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        int rowNum = 2;
        // 在表中存放查询到的数据放入对应的列
        for (User User : users) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(User.getId());
            row1.createCell(1).setCellValue(User.getUserName());
            rowNum++;
        }
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        workbookSheet.write(response.getOutputStream());
    }

    @GetMapping("export2")
    public void export2(HttpServletResponse response) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("title"
                , "sheetName"), User.class, users);
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        workbook.write(response.getOutputStream());
    }
}