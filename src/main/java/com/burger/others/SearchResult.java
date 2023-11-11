package com.burger.others;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchResult<T> {
  Long totalRecord;
  Integer pageCurrent;
  List<T> records;
}
