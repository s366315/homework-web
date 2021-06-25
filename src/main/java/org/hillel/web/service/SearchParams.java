package org.hillel.web.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchParams {

    private int pageNumber;
    private int pageSize;
    private String orderFieldName;
    private boolean orderAsc;
    private String filterKey;
    private String filterValue;
}
