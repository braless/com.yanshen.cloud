package com.yanshen.excel;


import com.yanshen.entity.ExcelFieldDTO;
import com.yanshen.entity.Export;
import com.yanshen.entity.JdBean;
import com.yanshen.utils.ExcelDynamicUtil;
import com.yanshen.utils.YanshenUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>Braless</h3>
 * <p>导出服务</p>
 *
 * @author : YanChao
 * @date : 2021-07-29 14:35
 **/
@Service
public class ExportService {
    public void export(HttpServletResponse response) throws IOException, IllegalAccessException {
        // 获取保洁员月统计数据
        List<Export> data = new ArrayList<>();//getBasicHistoryMonthReport(month, productid);


        for (int i = 0; i <200 ; i++) {
            Export export= new Export();
            export.setName(YanshenUtils.randomName());
            export.setAge(Double.valueOf(i)*10);
            export.setQymc(YanshenUtils.RandStr().toUpperCase());
            export.setGender(i%2==0?"男":"女");
            export.setTall(i*100-100);
            data.add(export);
        }


        // 表标题
        String title = "农村生活";

        Workbook wb = ExcelDynamicUtil.createWorkBook(true);
        Sheet sheet = wb.createSheet("sheetname");
        // 生成表头
        List<ExcelFieldDTO> headerList = ExcelDynamicUtil.getExcelHeaderList(Export.class, true);
        // 创建标题行单元格格式
        CellStyle titleCellStyle = ExcelDynamicUtil.initCellStyle(wb, sheet, headerList.size(), "宋体", 16, true, 5000, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        // 创建标题栏
        ExcelDynamicUtil.createExcelTitle(title, sheet, titleCellStyle, 2, 0, 0, 0, headerList.size() - 1);
        // 创建总体单元格格式
        CellStyle cellStyle = ExcelDynamicUtil.initCellStyle(wb, sheet, 1, "宋体", 11, false, 5000, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        // 创建excel表格
        int rowIndex = ExcelDynamicUtil.createExcelTableByFieldList(wb, sheet, headerList, cellStyle, cellStyle, 1, data, true);
        // 合并最后的总计栏
        ExcelDynamicUtil.mergeRowCell(sheet, rowIndex, rowIndex, 0, 1, true);
        sheet.getRow(rowIndex).getCell(0).setCellValue("总计");
        // 导出Excel文件
        ExcelDynamicUtil.exportExcel(wb, response, YanshenUtils.RandStr().toUpperCase() + ".xlsx");
    }

}
