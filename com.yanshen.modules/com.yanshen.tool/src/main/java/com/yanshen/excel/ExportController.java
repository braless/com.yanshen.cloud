package com.yanshen.excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <h3>Braless</h3>
 * <p>导出测试</p>
 *
 * @author : YanChao
 * @date : 2021-07-29 14:42
 **/
@RestController
@RequestMapping
public class ExportController {

    @Autowired
    ExportService exportService;
    @RequestMapping("/e")
    public void export(HttpServletResponse response) throws IOException, IllegalAccessException {
    exportService.export(response);
    }

}
