package org.lg.engine.core.client.utils;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

import java.io.Serializable;


public class Query extends BaseWfRequest implements Serializable {

    private Integer currentPage =1;

    private Integer pageSize =10;

    private Integer startRow;

    private Integer endRow;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartRow() {
        return (currentPage -1)*pageSize;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getEndRow() {
        return currentPage * pageSize;
    }

    public void setEndRow(Integer endRow) {
        this.endRow = endRow;
    }
}
