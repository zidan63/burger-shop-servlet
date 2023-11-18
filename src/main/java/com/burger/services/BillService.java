package com.burger.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.burger.entities.Address;
import com.burger.entities.Bill;
import com.burger.entities.BillDetail;
import com.burger.entities.CartItem;
import com.burger.entities.Product;
import com.burger.entities.User;
import com.burger.exception.BaseException;
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

  public Bill createBill(User user, Address address) throws BaseException {

    Bill bill = Bill.builder().user(user).address(address).build();
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

}