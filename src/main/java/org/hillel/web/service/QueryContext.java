package org.hillel.web.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class QueryContext {

    private int pageNumber;
    private int pageSize;
    private String orderFieldName;
    private boolean orderAsc;
    private String filterKey;
    private String filterValue;

    public QueryContext(int pageNumber, int pageSize, String orderFieldName, boolean orderAsc,
                        String filterKey, String filterValue) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.orderFieldName = orderFieldName;
        this.orderAsc = orderAsc;
        this.filterKey = filterKey;
        this.filterValue = filterValue;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getOrderFieldName() {
        return orderFieldName;
    }

    public boolean isOrderAsc() {
        return orderAsc;
    }

    public String getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(String filterKey) {
        this.filterKey = filterKey;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public int getFirstResult(){
        return (pageNumber - 1) * pageSize;
    }

    public String getOrderStr(){
        if (!StringUtils.isEmpty(orderFieldName)){
            return " order by " + orderFieldName + (orderAsc ? " asc" : " desc");
        }
        return "";
    }

    public PageRequest getPageRequest(){
        if (StringUtils.isEmpty(getOrderFieldName())){
            return PageRequest.of(
                    getPageNumber(), getPageSize());
        } else {
            Sort.Direction direction = isOrderAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
            return PageRequest.of(
                    getPageNumber(), getPageSize(), Sort.by(direction, getOrderFieldName()));
        }
    }
}
