package com.burger.services;

import java.util.List;
import java.util.Map;

import com.burger.entities.Color;
import com.burger.enums.SearchAboutType;
import com.burger.enums.SearchFieldType;
import com.burger.exception.BaseException;
import com.burger.others.Search;
import com.burger.others.SearchAbout;
import com.burger.others.SearchField;
import com.burger.others.SearchResult;
import com.burger.repositories.ColorRepository;

public class ColorService extends BaseService<Color, ColorRepository> {
  private static ColorService instance;

  public static ColorService getInstance() {
    if (instance == null)
      instance = new ColorService();
    return instance;
  }

  private ColorService() {
    super(ColorRepository.getInstance());
  }

  public SearchResult<Color> findByFields(Search search, Map<String, String[]> map) throws BaseException {

    List<SearchField> searchFields = List.of(
        SearchField.builder()
            .field("code")
            .values(map.get("code"))
            .type(SearchFieldType.STRING)
            .build(),
        SearchField.builder()
            .field("name")
            .values(map.get("name"))
            .type(SearchFieldType.STRING)
            .build());

    List<SearchAbout> searchAbouts = List.of(
        SearchAbout.builder()
            .field("createdAt")
            .from(map.get("createdAtFrom"))
            .to(map.get("createdAtTo"))
            .type(SearchAboutType.DATE)
            .build(),
        SearchAbout.builder()
            .field("updatedAt")
            .from(map.get("updatedAtFrom"))
            .to(map.get("updatedAtTo"))
            .type(SearchAboutType.DATE)
            .build());

    return transaction.doInTransaction(() -> repository.findByFields(search, searchFields, searchAbouts));

  }

}
