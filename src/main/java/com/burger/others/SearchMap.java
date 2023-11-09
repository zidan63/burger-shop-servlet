package com.burger.others;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class SearchMap {
    private String field;
    private Object value;
}
