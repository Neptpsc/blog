package com.blog.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装
 */
@Data
public class PageResult<T> implements Serializable {

    /** 总记录数 */
    private Long total;

    /** 当前页数据列表 */
    private List<T> records;

    public PageResult() {
    }

    public PageResult(Long total, List<T> records) {
        this.total = total;
        this.records = records;
    }
}
