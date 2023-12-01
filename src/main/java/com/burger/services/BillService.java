package com.burger.services;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.burger.entities.*;
import com.burger.enums.SearchAboutType;
import com.burger.enums.SearchFieldType;
import com.burger.exception.BaseException;
import com.burger.others.*;
import com.burger.repositories.BillRepository;

public class BillService extends BaseService<Bill, BillRepository> {
  private static BillService instance;

  public static BillService getInstance() {
    if (instance == null)
      instance = new BillService();
    return instance;
  }

  private BillService() {
    super(BillRepository.getInstance());
  }

  public List<Bill> findByUser(User user) throws BaseException {
    return transaction.doInTransaction(() -> repository.findByUser(user));
  }

  public Bill createBill(User user, Address address, Status status) throws BaseException {

    Bill bill = Bill.builder().user(user).address(address).status(status).build();
    Bill billNew = transaction.doInTransaction(() -> repository.saveOrUpdate(bill));

    Set<BillDetail> billDetails = new HashSet<>();

    List<CartItem> cartItems = CartService.getInstance().findByUser(user);
    for (CartItem cartItem : cartItems) {
      Product product = Product.builder().id(cartItem.getProduct().getId())
          .priceSale(cartItem.getProduct().getPriceSale()).build();

      BillDetail billDetail = new BillDetail(cartItem.getAmount(),
          product.getPriceSale(),
          product, billNew);

      billDetails.add(billDetail);

    }

    billNew.setBillDetails(billDetails);

    // for (CartItem cartItem : cartItems) {

    // CartService.getInstance().delete(cartItem.getId());
    // }

    return transaction.doInTransaction(() -> repository.saveOrUpdate(billNew));
  }


  public SearchResult<Bill> findAll(Search search, Map<String, String[]> map) throws BaseException {

    List<SearchField> searchFields = List.of();

    List<SearchAbout> searchAbouts = List.of();

    return transaction.doInTransaction(() -> repository.findByFields(search, searchFields, searchAbouts));

  }

  public SearchResult<Bill> findByFields(Search search, Map<String, String[]> map) throws BaseException {
    List<SearchField> searchFields = List.of(
            SearchField.builder()
                    .field("id")
                    .values(map.get("id"))
                    .type(SearchFieldType.NUMBER)
                    .build(),
            SearchField.builder()
                    .field("user.fullName")
                    .values(map.get("fullName"))
                    .type(SearchFieldType.SUB_OBJECT)
                    .build()
    );
    Console.Log(searchFields);
//

    List<SearchAbout> searchAbouts = List.of(
//            SearchAbout.builder()
//                    .field("priceSale")
//                    .from(map.get("priceSaleFrom"))
//                    .to(map.get("priceSaleTo"))
//                    .type(SearchAboutType.NUMBER)
//                    .build(),
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