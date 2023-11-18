package com.burger.services;

import java.util.List;

import com.burger.entities.CartItem;
import com.burger.entities.User;
import com.burger.exception.BaseException;
import com.burger.exception.ForbiddenException;
import com.burger.repositories.CartRepository;

public class CartService extends BaseService<CartItem, CartRepository> {
  private static CartService instance;

  public static CartService getInstance() {
    if (instance == null)
      instance = new CartService();
    return instance;
  }

  private CartService() {
    super(CartRepository.getInstance());
  }

  public List<CartItem> findByUser(User user) throws BaseException {
    return transaction.doInTransaction(() -> repository.findByUser(user));
  }

  public CartItem addCartItem(CartItem cartItem) throws BaseException {

    try {
      CartItem cartItemExist = transaction
          .doInTransaction(() -> repository.findByUserAndProduct(cartItem.getUser(), cartItem.getProduct()));

      cartItemExist.setAmount(cartItemExist.getAmount() + 1);
      return transaction.doInTransaction(() -> repository.saveOrUpdate(cartItemExist));

    } catch (BaseException e) {

      return transaction.doInTransaction(() -> repository.saveOrUpdate(cartItem));
    }

  }

  public CartItem updateCartItem(CartItem cartItem) throws BaseException {
    CartItem cartItemExist = transaction
        .doInTransaction(() -> repository.findByUserAndProduct(cartItem.getUser(), cartItem.getProduct()));

    if (cartItemExist != null) {
      cartItemExist.setAmount(cartItem.getAmount());
      return transaction.doInTransaction(() -> repository.saveOrUpdate(cartItemExist));
    }

    return transaction.doInTransaction(() -> repository.saveOrUpdate(cartItem));
  }

  public void deleteCartItem(Integer id, User user) throws BaseException {

    CartItem cartItemExist = transaction.doInTransaction(() -> repository.findById(id));
    if (cartItemExist.getUser().getId() != user.getId()) {
      throw new ForbiddenException("Bạn không có quyền!");
    }

    transaction.doInTransaction(() -> repository.delete(id));
  }

}