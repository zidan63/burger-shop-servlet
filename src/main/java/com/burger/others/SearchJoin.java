package com.burger.others;

import com.burger.enums.SearchFieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SearchJoin {
  private String fieldJoin;
  private String fieldOfJoin;
  private String[] values;
  private SearchFieldType type;

  public String getValueString() {
    if (values != null && values.length > 0) {
      return values[0];
    }
    return null;
  }

  public Integer getValueNumber() {
    return Integer.valueOf(values[0]);
  }

  public String[] getValuesString() {
    return values;
  }

  public Integer[] getValuesNumber() {
    Integer[] integerArray = new Integer[values.length];

    for (int i = 0; i < values.length; i++) {
      integerArray[i] = Integer.valueOf(values[i]);
    }

    return integerArray;
  }
}
