package com.burger.others;

import com.burger.enums.SearchFieldType;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SearchField {
    private String field;
    private String[] values;
    private SearchFieldType type;

    public boolean isEmpty() {
        if (values != null && values.length > 0) {
            return false;
        }
        return true;
    }

    public String getValueString() {
        if (values != null && values.length > 0) {
            return values[0].isEmpty() ? null : values[0];
        }
        return null;
    }

    public Integer getValueNumber() {
        if (values != null && values.length > 0) {
            return values[0].isEmpty() ? null : Integer.valueOf(values[0]);
        }
        return null;
    }

    public String[] getValuesString() {
        return values;
    }

    public Integer[] getValuesNumber() {
        Integer[] integerArray = new Integer[values.length];

        for (int i = 0; i < values.length; i++) {

            integerArray[i] = values[i].isEmpty() ? 0 : Integer.valueOf(values[i]);
        }

        return integerArray;
    }
    public <T,P> Path<P> getPath(Root<T> root, String attributeName) {
        Path<T> path = root;
        Path<P> _path = null;
        for (String part : attributeName.split("\\.")) {
            path = path.get(part);
        }
        _path = (Path<P>) path;
        return _path;
    }
}
