package com.burger.services;

import java.util.List;
import java.util.Map;

import com.burger.entities.Product;
import com.burger.enums.SearchAboutType;
import com.burger.enums.SearchFieldType;
import com.burger.exception.BaseException;
import com.burger.others.Search;
import com.burger.others.SearchAbout;
import com.burger.others.SearchField;
import com.burger.others.SearchResult;
import com.burger.repositories.ProductRepository;

public class ProductService extends BaseService<Product, ProductRepository> {
  private static ProductService instance;

  public static ProductService getInstance() {
    if (instance == null)
      instance = new ProductService();
    return instance;
  }

  private ProductService() {
    super(ProductRepository.getInstance());
  }

  public SearchResult<Product> findByFields(Search search, Map<String, String[]> map) throws BaseException {

    List<SearchField> searchFields = List.of(
        SearchField.builder()
            .field("id")
            .values(map.get("id"))
            .type(SearchFieldType.NUMBER)
            .build(),
        SearchField.builder()
            .field("name")
            .values(map.get("name"))
            .type(SearchFieldType.STRING)
            .build(),
        SearchField.builder()
            .field("category")
            .values(map.get("categoryId"))
            .type(SearchFieldType.ARRAY)
            .build(),
        SearchField.builder()
            .field("supplier")
            .values(map.get("supplierId"))
            .type(SearchFieldType.ARRAY)
            .build());

    List<SearchAbout> searchAbouts = List.of(
        SearchAbout.builder()
            .field("priceSale")
            .from(map.get("priceSaleFrom"))
            .to(map.get("priceSaleTo"))
            .type(SearchAboutType.NUMBER)
            .build(),
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
