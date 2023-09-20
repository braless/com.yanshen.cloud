package com.yanshen.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-15 14:49
 * @Description:
 * @Location: com.yanshen.common
 * @Project: com.yanshen.cloud
 */
@Data
public class PageData<T> {

    private List<T> rows;

    private  Long total;
    private long current;

    private long pages;

    public PageData(IPage<T> page){
        this.rows = page.getRecords();
        this.total = page.getTotal();
        this.current=page.getCurrent();
        this.pages=total % page.getSize() == 0 ? total / page.getSize() : total / page.getSize() + 1;
    }
}
