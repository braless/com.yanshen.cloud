package com.yanshen.utils;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.yanshen.annotaition.ExcelField;
import com.yanshen.entity.ExcelFieldDTO;
import com.yanshen.exception.TipException;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * excel数据动态转换工具类
 *
 * @Author chenxianjing
 * @date 2020.05.21 15:41
 */
public class ExcelDynamicUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ExcelDynamicUtil.class);

    private static final Map<String, Validator> VALIATOR_MAP = new HashMap<>();

    private ExcelDynamicUtil() {

    }

    static {
        ValidatorFactory avf = Validation.byProvider(HibernateValidator.class).configure().buildValidatorFactory();
        VALIATOR_MAP.put("hibernate_valiator", avf.getValidator());
    }

    /**
     * 获取Validator
     *
     * @param validName
     * @return
     */
    protected static final Validator getValidatorFactory(String validName) {
        return VALIATOR_MAP.get(validName);
    }

//    /**
//     * 将配置字段与excel的值配对
//     *
//     * @param list          必须是按xssx排好序,否则转换不对
//     * @param row
//     * @param isFirstRowNum 是否第一列是序号
//     * @return
//     */
//    public static Map<String, String> converExcel(List<ViewColumnConfig> list, List<Cell> row, boolean isFirstRowNum) {
//        Map<String, String> map = new HashMap<>(list.size());
//        if (CollectionUtils.isEmpty(list)) {
//            LOG.error("字段未配置");
//            return map;
//        }
//        if (CollectionUtils.isEmpty(row)) {
//            LOG.error("数据为空");
//            return map;
//        }
//        int emptyCount = 0;
//        for (int i = 0; i < list.size(); i++) {
//            int j = i;
//            if (isFirstRowNum) {
//                j = i + 1;
//            }
//            if(j == row.size()){
//                break;
//            }
//            String value = ExcelOpUtils.getCellValue(row.get(j));
//            if(StringUtils.isBlank(value)){
//                emptyCount++;
//            }
//            map.put(list.get(i).getZdmc(), value);
//        }
//        /**
//         * 所有列为空值处理
//         */
//        if(emptyCount == map.size()){
//            return Collections.emptyMap();
//        }
//        return map;
//    }

    /**
     * 将excel的字段值赋值到对应的对象字段上,第一列必须是序号
     *
     * @param list 配置字段对象
     * @param row  excel的行
     * @param t    对象的class
     * @param <T>
     * @return
     * @throws Exception
     */
//    public static <T> T converExcel(List<ViewColumnConfig> list, List<Cell> row, Class<T> t, boolean isFirstRowNum) throws IllegalAccessException {
//        Map<String, String> map = converExcel(list, row, isFirstRowNum);
//        T result = null;
//        if(!map.isEmpty()){
//            result = (T) ReflectUtils.newInstance(t);
//        }
//        for (Map.Entry<String, String> m : map.entrySet()) {
//            Field field = FieldUtils.getDeclaredField(t, m.getKey(), true);
//            if (field == null) {
//                continue;
//            }
//            if (!Objects.isNull(m.getValue())) {
//                Object actualTypeValue = convertString(m.getValue(), field);
//                if (!Objects.isNull(actualTypeValue)) {
//                    FieldUtils.writeField(result, m.getKey(), actualTypeValue, true);
//                }
//            }
//            Set<ConstraintViolation<T>> errorSet = ExcelDynamicUtil.getValidatorFactory("hibernate_valiator").validateValue(t, m.getKey(), FieldUtils.readDeclaredField(result, m.getKey(), true));
//            for (ConstraintViolation<T> s : errorSet) {
//                throw new TipException(s.getMessage());
//            }
//        }
//        return result;
//    }

    /**
     * 根据field的类型对string值转换
     *
     * @param value
     * @param field
     * @return
     */
    public static Object convertString(String value, final Field field) {
        String val = value.trim();
        boolean isBlank = StringUtils.isBlank(val);
        if (field.getType() == String.class) {
            return val;
        } else if (isBlank) {
            return null;
        }
        if (ClassUtils.isAssignable(field.getType(), Byte.class, true)) {
            return Byte.valueOf(val);
        } else if (ClassUtils.isAssignable(field.getType(), Short.class, true)) {
            return Short.valueOf(val);
        } else if (ClassUtils.isAssignable(field.getType(), Integer.class, true)) {
            return Integer.valueOf(val);
        } else if (ClassUtils.isAssignable(field.getType(), Long.class, true)) {
            return Long.valueOf(val);
        } else if (ClassUtils.isAssignable(field.getType(), BigInteger.class)) {
            return new BigInteger(val);
        } else if (ClassUtils.isAssignable(field.getType(), Float.class, true)) {
            return Float.valueOf(val);
        } else if (ClassUtils.isAssignable(field.getType(), Double.class, true)) {
            return Double.valueOf(val);
        } else if (ClassUtils.isAssignable(field.getType(), BigDecimal.class) || ClassUtils.isAssignable(field.getType(), Number.class)) {
            return new BigDecimal(val);
        } else if (field.getType() == Date.class) {
            //对时间的格式化处理
        //    return formatDate(val, field);
            return formatDate(val);
        } else if (ClassUtils.isAssignable(field.getType(), Boolean.class, true)) {
            return Boolean.valueOf(val);
        }
        return null;
    }

    /**
     * 日期转换
     *
     * @param value field的值
     * @param field
     * @param
     */
    private static Object formatDate(String value, final Field field) {
        String format = null;
        JSONField jsonField = field.getAnnotation(JSONField.class);
        if (jsonField != null) {
            format = jsonField.format();
        } else {
            JsonFormat jsonFormat = field.getAnnotation(JsonFormat.class);
            if (jsonFormat != null) {
                format = jsonFormat.pattern();
            }
        }
        if (StringUtils.isNotEmpty(format) && StringUtils.isNotEmpty(value)) {
            format = format.trim();
            if (value.length() > format.length()) {
                value = value.substring(0, format.length());
            }
            try {
                return new SimpleDateFormat(format).parse(value);
            } catch (Exception e) {
                throw new TipException("时间格式不对,标准日期格式:" + format);
            }
        }
        return null;
    }

    /***
     * 根据通用的日期格式进行转换
     * @param value
     * @return
     */
    private static Object formatDate(String value) {
        if(StringUtils.isEmpty(value)){
            return null ;
        }
        if(value.contains(" 00:00:00")){
            value = value.replace(" 00:00:00","");
        }
        String [] formatArry1 = {"yyyy年M月d日","yyyy年MM月dd日"};
        String [] formatArry2 = {"yyyy-M-d","yyyy-MM-dd"};
        String [] formatArry3 = {"yyyy/M/d","yyyy/MM/dd"};
        String [] formatArry = null ;
        if(value.contains("年")){
            formatArry = formatArry1;
        }else if(value.contains("-")){
            formatArry = formatArry2;
        }else if(value.contains("/")){
            formatArry = formatArry3;
        }else{
            try{
                return  HSSFDateUtil.getJavaDate(Double.valueOf(value));//格式传过来的可能是被转换为数字了
            }catch (Exception e){
                throw new TipException("时间格式不正确");
            }

        }
        for (String format : formatArry) {
            try{
                return  DateUtils.parseDate(value,format);
            }catch (Exception e){
                continue;
            }
        }
        throw new TipException("时间格式不正确");
    }

    /**
     * 将excel的字段值赋值到对应的对象字段上,第一列必须是序号
     *
     * @param list  配置字段对象
     * @param param excel的所有行
     * @param t     对象的class
     * @param <T>
     * @return
     * @throws Exception
     */
//    public static <T> List<T> converBatchExcel(List<ViewColumnConfig> list, List<List<Cell>> param, Class<T> t) {
//        return converBatchExcel(list, param, t, true);
//    }

    /**
     * 将excel的字段值赋值到对应的对象字段上
     *
     * @param list          配置字段对象
     * @param param         excel的所有行
     * @param t             对象的class
     * @param isFirstRowNum 第一列是否序号
     * @param <T>
     * @return
     * @throws Exception
     */
//    public static <T> List<T> converBatchExcel(List<ViewColumnConfig> list, List<List<Cell>> param, Class<T> t, boolean isFirstRowNum) {
//        if (org.springframework.util.CollectionUtils.isEmpty(param)) {
//            throw new TipException("没有可导入的数据");
//        }
//        int j = 1;
//        List<T> resultList = new ArrayList<>(param.size());
//        for (List<Cell> row : param) {
//            try {
//                T actual = converExcel(list, row, t, isFirstRowNum);
//                if(!Objects.isNull(actual)){
//                    resultList.add(actual);
//                }
//            } catch (Exception e) {
//                String format = String.format("第%d条的数据不正确.原因:", j);
//                LOG.info(format, e);
//                throw new TipException(format + e.getMessage());
//            }
//            j++;
//        }
//        if (org.springframework.util.CollectionUtils.isEmpty(resultList)) {
//            throw new TipException("没有可导入的数据");
//        }
//        return resultList;
//    }

    /**
     * 设置单元格格式
     * @param wb                Excel对象
     * @param sheet             sheet对象
     * @param size              单元格个数
     * @param fontName          字体
     * @param fontSize          字体大小
     * @param isBlod            是否加粗
     * @param columnWidth       列宽度
     * @param alignment         水平格式
     * @param verticalAlignment 垂直格式
     * @return
     */
    public static CellStyle initCellStyle(Workbook wb, Sheet sheet, int size, String fontName, int fontSize, boolean isBlod, int columnWidth, HorizontalAlignment alignment, VerticalAlignment verticalAlignment) {
        return initCellStyle(wb, sheet, size, fontName, fontSize, isBlod, true, columnWidth, alignment, verticalAlignment);
    }

    /**
     * 设置单元格格式
     * @param wb                Excel对象
     * @param sheet             sheet对象
     * @param size              单元格个数
     * @param fontName          字体
     * @param fontSize          字体大小
     * @param isBlod            是否加粗
     * @param isNeedBorder      是否需要边框
     * @param columnWidth       列宽度
     * @param alignment         水平格式
     * @param verticalAlignment 垂直格式
     * @return
     */
    public static CellStyle initCellStyle(Workbook wb, Sheet sheet, int size, String fontName, int fontSize, boolean isBlod, boolean isNeedBorder, int columnWidth, HorizontalAlignment alignment, VerticalAlignment verticalAlignment) {
        return initCellStyle(wb, sheet, size, fontName, fontSize, Font.COLOR_NORMAL, isBlod, isNeedBorder, columnWidth, alignment, verticalAlignment);
    }

    /**
     * 设置单元格格式
     * @param wb                Excel对象
     * @param sheet             sheet对象
     * @param size              单元格个数
     * @param fontName          字体
     * @param fontSize          字体大小
     * @param color             颜色
     * @param isBlod            是否加粗
     * @param isNeedBorder      是否需要边框
     * @param columnWidth       列宽度
     * @param alignment         水平格式
     * @param verticalAlignment 垂直格式
     * @return
     */
    public static CellStyle initCellStyle(Workbook wb, Sheet sheet, int size, String fontName, int fontSize, short color, boolean isBlod, boolean isNeedBorder, int columnWidth, HorizontalAlignment alignment, VerticalAlignment verticalAlignment) {
        for (int i = 0; i < size; i++) {
            // 设置列宽
            sheet.setColumnWidth(i, columnWidth <=0 ? 3966 : columnWidth);
        }
        CellStyle cellStyle = wb.createCellStyle();
        if (alignment != null) {
            // 水平格式
            cellStyle.setAlignment(alignment);
        }
        if (verticalAlignment != null) {
            // 垂直格式
            cellStyle.setVerticalAlignment(verticalAlignment);
        }
        // 是否自动换行
        cellStyle.setWrapText(true);
        Font font = wb.createFont();
        // 设置字体
        font.setFontName((fontName == null || fontName.trim().equals("")) ? "宋体" : fontName);
        // 设置字体大小
        font.setFontHeightInPoints((short) fontSize);
        // 是否加粗
        font.setBold(isBlod);
        // 颜色
        font.setColor(color);
        cellStyle.setFont(font);
        if (isNeedBorder) {
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
        }
        return cellStyle;
    }

    /**
     * 创建标题
     * @param title         标题
     * @param sheet         sheet对象
     * @param cellStyle     单元格风格
     * @param multiples     行高倍数
     * @param isNeedBorder  是否需要边框
     * @param firstRow      起始行（从0开始）（可用于合并单元格）
     * @param lastRow       最后行（从0开始）（可用于合并单元格）
     * @param firstCol      起始列（从0开始）（可用于合并单元格）
     * @param lastCol       最后列（从0开始）（可用于合并单元格）
     * @throws IOException
     */
    public static void createExcelTitle(String title, Sheet sheet, CellStyle cellStyle, int multiples, boolean isNeedBorder, int firstRow, int lastRow, int firstCol, int lastCol) throws IOException {
        multiples = multiples < 1 ? 1 : multiples;
        // 创建标题行
        Row titleRow = sheet.createRow(firstRow);
        // 设置标题行行高
        titleRow.setHeight((short)(titleRow.getHeight()*multiples));
        // 创建标题行的单元格
        Cell cell = titleRow.createCell(firstCol);
        // 设置标题行内容
        cell.setCellValue(title);
        if ((lastRow -  firstRow) > 0 || (lastCol - firstCol) > 0) {
            // 合并标题行单元格
            CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
            if (isNeedBorder) {
                RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet); // 下边框
                RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet); // 左边框
                RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet); // 有边框
                RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet); // 上边框
            }
            sheet.addMergedRegion(region);
        }
        // 设置标题行单元格格式
        cell.setCellStyle(cellStyle);
    }
    /**
     * 创建标题
     * @param title     标题
     * @param sheet     sheet对象
     * @param cellStyle 单元格风格
     * @param multiples 行高倍数
     * @param firstRow  起始行（从0开始）（可用于合并单元格）
     * @param lastRow   最后行（从0开始）（可用于合并单元格）
     * @param firstCol  起始列（从0开始）（可用于合并单元格）
     * @param lastCol   最后列（从0开始）（可用于合并单元格）
     * @throws IOException
     */
    public static void createExcelTitle(String title, Sheet sheet, CellStyle cellStyle, int multiples, int firstRow, int lastRow, int firstCol, int lastCol) throws IOException {
        createExcelTitle(title, sheet, cellStyle, multiples, true, firstRow, lastRow, firstCol, lastCol);
    }

    /**
     * 合并单元格
     * @param sheet         sheet对象
     * @param firstRow      起始行（从0开始）（可用于合并单元格）
     * @param lastRow       最后行（从0开始）（可用于合并单元格）
     * @param firstCol      起始列（从0开始）（可用于合并单元格）
     * @param lastCol       最后列（从0开始）（可用于合并单元格）
     * @param isNeedBorder  是否需要边框
     */
    public static void mergeRowCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol, boolean isNeedBorder) {
        if ((lastRow -  firstRow) > 0 || (lastCol - firstCol) > 0) {
            // 合并标题行单元格
            CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
            if (isNeedBorder) {
                RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet); // 下边框
                RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet); // 左边框
                RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet); // 有边框
                RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet); // 上边框
            }
            sheet.addMergedRegion(region);
        }
    }

    /**
     * 设置单元格文字对齐方式
     * @param sheet     sheet对象
     * @param row       行
     * @param col       列
     * @param alignment 对齐方式
     */
    public static void setCellAlignment(Sheet sheet, int row, int col, HorizontalAlignment alignment) {
        sheet.getRow(row).getCell(col).getCellStyle().setAlignment(alignment);
    }

    /**
     * excel生成表
     * @param sheet             sheet对象
     * @param headerCellStyle   表头格式
     * @param cellStyle         单元格格式
     * @param startRow          起始行
     * @param excelTableHeaders 表头
     * @param dataList          参数列表
     * @return  最后行数
     * @throws IOException
     */
    public static int createExcelTable(Sheet sheet, CellStyle headerCellStyle, CellStyle cellStyle, int startRow, String[][] excelTableHeaders, List<Map> dataList) throws IOException {
        int lastRow = startRow;
        // 表头列
        Row summaryDataHeadRow = sheet.createRow(startRow);
        // 表头生成
        for (int i = 0; i < excelTableHeaders.length; i++) {
            Cell headCell = summaryDataHeadRow.createCell(i);
            headCell.setCellStyle(headerCellStyle);
            headCell.setCellValue(excelTableHeaders[i][0]);
        }
        // 表内容生成
        for (Map map : dataList) {
            lastRow++;
            // 表内容列
            Row summaryDataValueRow = sheet.createRow(lastRow);
            for (int i = 0; i < excelTableHeaders.length; i++) {
                Cell valueCell = summaryDataValueRow.createCell(i);
                valueCell.setCellStyle(cellStyle);
                valueCell.setCellValue(map.get(excelTableHeaders[i][1]) == null ? null : map.get(excelTableHeaders[i][1]).toString());
            }
        }
        return lastRow;
    }

    /**
     * excel生成表
     * @param sheet             sheet对象
     * @param cellStyle         单元格格式
     * @param startRow          起始行
     * @param excelTableHeaders 表头
     * @param dataList          参数列表
     * @return  最后行数
     * @throws IOException
     */
    public static int createExcelTable(Sheet sheet, CellStyle cellStyle, int startRow, String[][] excelTableHeaders, List<Map> dataList) throws IOException {
        return createExcelTable(sheet, cellStyle, cellStyle, startRow, excelTableHeaders, dataList);
    }

    /**
     * 创建行数据
     * @param sheet     sheet对象
     * @param cellStyle cell风格
     * @param startRow  起始行
     * @param multiples 行高倍数（默认1）
     * @param dataList  数据列表
     * @return
     */
    public static int createExcelRowData(Sheet sheet, CellStyle cellStyle, int startRow, int multiples, List<String> dataList) {
        multiples = multiples < 1 ? 1 : multiples;
        // 创建标题行
        Row row = sheet.createRow(startRow);
        // 设置标题行行高
        row.setHeight((short)(row.getHeight() * multiples));
        // 数据生成
        for (int i = 0; i < dataList.size(); i++) {
            Cell headCell = row.createCell(i);
            headCell.setCellStyle(cellStyle);
            headCell.setCellValue(dataList.get(i));
        }
        return startRow + 1;
    }

    /**
     * excel生成表
     * @param sheet             sheet对象
     * @param cellStyle         单元格格式
     * @param startRow          起始行
     * @param excelTableHeaders 表头
     * @param data              参数
     * @return  最后行数
     * @throws IOException
     */
    public static int createExcelTable(Sheet sheet, CellStyle cellStyle, int startRow, String[][] excelTableHeaders, Map data) throws IOException {
        List<Map> datas = new ArrayList<>();
        datas.add(data);
        return createExcelTable(sheet, cellStyle, startRow, excelTableHeaders, datas);
    }

    /**
     * excel生成表
     * @param sheet             sheet对象
     * @param cellStyle         单元格格式
     * @param startRow          起始行
     * @param excelTableHeaders 表头
     * @param data              参数
     * @return  最后行数
     * @throws IOException
     */
    public static int createExcelTableWithObject(Sheet sheet, CellStyle cellStyle, int startRow, String[][] excelTableHeaders, Object data) throws IOException, IllegalAccessException {
        List<Object> datas = new ArrayList<>();
        datas.add(data);
        return createExcelTableWithObjects(sheet, cellStyle, startRow, excelTableHeaders, datas);
    }

    /**
     * excel生成表
     * @param sheet             sheet对象
     * @param cellStyle         单元格格式
     * @param startRow          起始行
     * @param excelTableHeaders 表头
     * @param dataList          参数列表
     * @return  最后行数
     * @throws IOException
     */
    public static int createExcelTableWithObjects(Sheet sheet, CellStyle cellStyle, int startRow, String[][] excelTableHeaders, List<?> dataList) throws IOException, IllegalAccessException {
        List<Map> datas = new ArrayList<>();
        Map map = null;
        for (Object obj: dataList) {
            map = new HashMap();
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
            datas.add(map);
        }
        return createExcelTable(sheet, cellStyle, startRow, excelTableHeaders, datas);
    }

    /**
     * 导出Excel
     * @param workbook  Excel对象
     * @param response  返回
     * @param fileName  文件名称
     * @throws IOException
     */
    public static void exportExcel(Workbook workbook, HttpServletResponse response, String fileName) throws IOException {
        OutputStream output = response.getOutputStream();
        try {
            response.setContentType("application/OCTET-STREAM;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(output);
        } finally {
            if (workbook != null)
                workbook.close();
            if (output != null)
                output.close();
        }
    }

    /**
     * 创建Excel对象
     * @param isXSSF
     * @return
     */
    public static Workbook createWorkBook(boolean isXSSF) {
        Workbook workbook;
        if (isXSSF) {
            workbook = new XSSFWorkbook();
        } else {
            workbook = new HSSFWorkbook();
        }
        return workbook;
    }
    /**
     * 合并单元格
     * @param sheet     sheet对象
     * @param firstRow  起始行（从0开始）（可用于合并单元格）
     * @param lastRow   最后行（从0开始）（可用于合并单元格）
     * @param firstCol  起始列（从0开始）（可用于合并单元格）
     * @param lastCol   最后列（从0开始）（可用于合并单元格）
     */
    public static void mergeCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        if ((lastRow -  firstRow) > 0 || (lastCol - firstCol) > 0) {
            // 合并标题行单元格
            CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
            RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet); // 下边框
            RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet); // 左边框
            RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet); // 有边框
            RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet); // 上边框
            sheet.addMergedRegion(region);
        }
    }

    /**
     * 获取表头列表
     * @param clazz     实体类
     * @param isNeedXh  是否需要序号
     * @return
     */
    public static List<ExcelFieldDTO> getExcelHeaderList(Class clazz, boolean isNeedXh) {
        List<ExcelFieldDTO> headerList = Lists.newArrayList();
        List<Field> fields = Lists.newArrayList();
        for (Class<?> claz = clazz; claz != Object.class; claz = claz.getSuperclass()) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        if (fields != null && !fields.isEmpty()) {
            fields.stream().forEach(field ->{
                if (field.isAnnotationPresent(ExcelField.class)) {
                    ExcelField er = field.getAnnotation(ExcelField.class);
                    headerList.add(
                            ExcelFieldDTO.builder()
                                    .title(er.title())
                                    .parentTitle(er.parentTitle())
                                    .order(er.order())
                                    .fieldName(field.getName())
                                    .isRequired(er.isRequired())
                                    .dataType(er.dataType())
                                    .width(er.width())
                                    .postil(er.postil())
                                    .isXh(false)
                                    .build()
                    );
                }
            });
            Collections.sort(headerList);
        }
        if (isNeedXh) {
            List<ExcelFieldDTO> headerList1 = Lists.newArrayList();
            headerList1.add(ExcelFieldDTO.builder()
                    .title("序号")
                    .fieldName("xh")
                    .dataType("integer")
                    .isRequired(false)
                    .isXh(true)
                    .width(2)
                    .build());
            headerList1.addAll(headerList);
            return headerList1;
        } else {
            return headerList;
        }
    }

    /**
     * 根据表头创建表格列表
     * @param workbook          excel对象
     * @param sheet             sheet对象
     * @param headerList        表头列表
     * @param headerCellStyle   表头格式
     * @param cellStyle         单元格格式
     * @param startRow          起始行
     * @param dataList          数据列表
     * @param isNeedHeaderPane  是否进行表头行固定
     * @return
     * @throws IllegalAccessException
     */
    public static int createExcelTableByFieldList(Workbook workbook, Sheet sheet, List<ExcelFieldDTO> headerList, CellStyle headerCellStyle, CellStyle cellStyle, int startRow, List<?> dataList, boolean isNeedHeaderPane) throws IllegalAccessException {
        List<Map> datas = new ArrayList<>();
        Map map = null;
        for (Object obj: dataList) {
            map = new HashMap();
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
            datas.add(map);
        }
        return createExcelTableByFieldMapList(workbook, sheet, headerList, headerCellStyle, cellStyle, startRow, datas, isNeedHeaderPane);
    }

    /**
     * 根据表头创建表格列表
     * @param workbook          excel对象
     * @param sheet             sheet对象
     * @param headerList        表头列表
     * @param headerCellStyle   表头格式
     * @param cellStyle         单元格格式
     * @param startRow          起始行
     * @param dataList          数据列表
     * @param isNeedHeaderPane  是否进行表头行固定
     * @return
     * @throws IllegalAccessException
     */
    public static int createExcelTableByFieldMapList(Workbook workbook, Sheet sheet, List<ExcelFieldDTO> headerList, CellStyle headerCellStyle, CellStyle cellStyle, int startRow, List<Map> dataList, boolean isNeedHeaderPane) {
        boolean isHeaderTwoRow = false;
        // 遍历判断表头是否有两行需要合并
        for (ExcelFieldDTO dcExcelFieldDTO: headerList) {
            if (!StringUtils.isEmpty(dcExcelFieldDTO.getParentTitle())) {
                isHeaderTwoRow = true;
                break;
            }
        }
        // 表头列
        Row summaryDataHeadRow = sheet.createRow(startRow);
        // 表头第二列
        Row summaryDataHeadRow2 = isHeaderTwoRow ? sheet.createRow(++startRow) : null;
        int lastRow = startRow;
        String parentTitle = null;
        int parentCellStartIndex = 0;
        // 表头生成
        for (int i = 0; i < headerList.size(); i++) {
            Cell valueCell = null;
            // 生成首行
            Cell firstHeadCell = summaryDataHeadRow.createCell(i);
            firstHeadCell.setCellStyle(headerCellStyle);
            // 判断是否需要生成第二行
            if (isHeaderTwoRow) {
                if (StringUtils.isEmpty(headerList.get(i).getParentTitle())) {
                    valueCell = firstHeadCell;
                    mergeRowCell(sheet, startRow - 1, startRow, i, i, true);
                    if (parentTitle != null) {
                        mergeRowCell(sheet, startRow - 1, startRow - 1, parentCellStartIndex, i - 1, true);
                    }
                    parentTitle = null;
                    parentCellStartIndex = 0;
                } else {
                    Cell secondHeadCell = summaryDataHeadRow2.createCell(i);
                    secondHeadCell.setCellStyle(headerCellStyle);
                    valueCell = secondHeadCell;
                    if (parentTitle == null || !parentTitle.equals(headerList.get(i).getParentTitle())) {
                        if (parentTitle != null && !parentTitle.equals(headerList.get(i).getParentTitle())) {
                            mergeRowCell(sheet, startRow - 1, startRow - 1, parentCellStartIndex, i - 1, true);
                        }
                        parentTitle = headerList.get(i).getParentTitle();
                        firstHeadCell.setCellValue(headerList.get(i).getParentTitle());
                        parentCellStartIndex = i;
                    }
                }
                if (parentTitle != null && i >= headerList.size() - 1) {
                    mergeRowCell(sheet, startRow - 1, startRow - 1, parentCellStartIndex, i, true);
                }
            } else {
                valueCell = firstHeadCell;
            }
            // 设置表头标题
            valueCell.setCellValue(getHeaderValue(workbook, headerList.get(i).getTitle(), headerList.get(i).getIsRequired()));
            if (!StringUtils.isEmpty(headerList.get(i).getPostil())) {
                // 设置注解
                valueCell.setCellComment(getHeaderCommon(workbook, sheet, valueCell.getColumnIndex(), summaryDataHeadRow.getRowNum(), headerList.get(i).getPostil()));
            }
            // 设置宽度
            sheet.setColumnWidth(valueCell.getColumnIndex(), headerList.get(i).getWidth() * 1000);
        }

        if (isNeedHeaderPane) {
            sheet.createFreezePane(0, lastRow + 1, 0, lastRow + 1);
        }

        if (summaryDataHeadRow2 != null) {
            summaryDataHeadRow.setHeight((short) (summaryDataHeadRow.getHeight() * 2));
        }

        int rowXh = 1;
        // 表内容生成
        for (Map dataMap : dataList) {
            lastRow++;
            // 表内容列
            Row summaryDataValueRow = sheet.createRow(lastRow);
            for (int i = 0; i < headerList.size(); i++) {
                Cell dataCell = summaryDataValueRow.createCell(i);
                dataCell.setCellStyle(cellStyle);
                if (headerList.get(i).getIsXh()) {
                    dataCell.setCellValue(rowXh);
                } else {
                    Object obj = dataMap.get(headerList.get(i).getFieldName());
                    if (headerList.get(i).getDataType().equals("string")) {
                        dataCell.setCellValue(obj == null ? null : obj.toString());
                    } else if (headerList.get(i).getDataType().equals("integer")) {
                        dataCell.setCellType(CellType.NUMERIC);
                        Long value = 0L;
                        if (obj != null) {
                            if (obj instanceof BigDecimal) {
                                value = ((BigDecimal) obj).longValue();
                            } else {
                                value = Long.valueOf(obj.toString());
                            }
                        }
                        dataCell.setCellValue(value);
                    } else if (headerList.get(i).getDataType().equals("float")) {
                        dataCell.setCellType(CellType.NUMERIC);
                        Double value = 0.0D;
                        if (obj != null) {
                            if (obj instanceof BigDecimal) {
                                value = ((BigDecimal) obj).doubleValue();
                            } else {
                                value = Double.valueOf(obj.toString());
                            }
                        }
                        dataCell.setCellValue(value);
                    }
                }
            }
            rowXh++;
        }
        return lastRow;
    }

    /**
     * 生成批注
     * @param workbook  excel对象
     * @param sheet     sheet表对象
     * @param cellNum   列号
     * @param rowNum    行号
     * @param postil    批注内容
     * @return
     */
    public static Comment getHeaderCommon(Workbook workbook, Sheet sheet, int cellNum, int rowNum, String postil) {
        CreationHelper helper = workbook.getCreationHelper();
        ClientAnchor clientAnchor = helper.createClientAnchor();
        clientAnchor.setDx1(0);
        clientAnchor.setDy1(0);
        clientAnchor.setDx2(0);
        clientAnchor.setDy2(0);
        clientAnchor.setCol1(cellNum + 1);
        clientAnchor.setRow1(rowNum);
        clientAnchor.setCol2(cellNum + 3);
        int index = 0;
        int count = 1;
        while ((index=postil.indexOf("\n", index))!=-1){
            count++;
            index+="\n".length();
        }
        clientAnchor.setRow2(count + rowNum);
        RichTextString richTextString = helper.createRichTextString(postil);
        Font commonFont = workbook.createFont();
        commonFont.setFontName("宋体");
        commonFont.setFontHeightInPoints((short) 9);
        richTextString.applyFont(commonFont);
        // 创建绘图对象
        Drawing drawing = sheet.createDrawingPatriarch();
        Comment comment = drawing.createCellComment(clientAnchor);
        comment.setString(richTextString);
        return comment;
    }

    /**
     * 生成红点文本格式
     * @param workbook
     * @return
     */
    public static RichTextString getHeaderValue(Workbook workbook, String headTitle, boolean notAllowEmpty) {
        CreationHelper helper = workbook.getCreationHelper();
        Font commonFont = workbook.createFont();
        commonFont.setFontName("宋体");
        commonFont.setFontHeightInPoints((short) 11);
        //声明RichTextString类型;
        RichTextString richTextString = null;
        if (notAllowEmpty) {
            headTitle = "*" + headTitle;
            richTextString = helper.createRichTextString(headTitle);
            Font font = workbook.createFont();
            font.setFontName("宋体");
            font.setColor(Font.COLOR_RED);
            font.setFontHeightInPoints((short) 11);
            richTextString.applyFont(0, 1, font);
            richTextString.applyFont(1, headTitle.length(), commonFont);
        } else {
            richTextString = helper.createRichTextString(headTitle);
            richTextString.applyFont(commonFont);
        }
        return richTextString;
    }
}
