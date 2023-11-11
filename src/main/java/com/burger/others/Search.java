package com.burger.others;

import com.burger.enums.SearchType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Search {
  String page;
  String pageSize;
  String type;

  public Integer getPage() {
    return page != null ? Integer.parseInt(page) : 1;
  }

  public Integer getPageSize() {
    return pageSize != null ? Integer.parseInt(pageSize) : 10;
  }

  public SearchType getType() {
    return type != null ? SearchType.valueOf(type) : SearchType.NORMAL;
  }
}
