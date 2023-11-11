package com.burger.others;

import java.util.Date;

import com.burger.enums.SearchAboutType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchAbout {
  private String field;
  private String[] from;
  private String[] to;
  private SearchAboutType type;

  public boolean isEmpty() {
    return from == null && to == null;
  }

  public String getField() {
    return field;
  }

  public Integer getFromInteger() {
    if (from != null && from.length > 0) {
      try {
        return Integer.parseInt(from[0]);
      } catch (NumberFormatException e) {
      }
    }

    return null;
  }

  public Integer getToInteger() {
    if (to != null && to.length > 0) {
      try {
        return Integer.parseInt(to[0]);
      } catch (NumberFormatException e) {
      }
    }

    return null;
  }

  public Date getFromDate() {
    if (from != null && from.length > 0) {
      try {
        long timestamp = Long.parseLong(from[0]);
        Date date = new Date(timestamp);
        return date;
      } catch (NumberFormatException e) {
      }
    }

    return null;
  }

  public Date getToDate() {
    if (to != null && to.length > 0) {
      try {
        long timestamp = Long.parseLong(to[0]);
        Date date = new Date(timestamp);
        return date;
      } catch (NumberFormatException e) {
      }
    }

    return null;
  }
}
