package com.burger.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import com.burger.entities.User;
import com.burger.enums.SearchAboutType;
import com.burger.enums.SearchFieldType;
import com.burger.exception.BadRequestException;
import com.burger.exception.BaseException;
import com.burger.others.Search;
import com.burger.others.SearchAbout;
import com.burger.others.SearchField;
import com.burger.others.SearchResult;
import com.burger.repositories.UserRepository;

public class UserService extends BaseService<User, UserRepository> {
  private static UserService instance;

  public static UserService getInstance() {
    if (instance == null)
      instance = new UserService();
    return instance;
  }

  private UserService() {
    super(UserRepository.getInstance());
  }

  public User findByUserName(String username) {
    return transaction.doInTransaction(() -> repository.findByUserName(username));
  }

  public User findByUserNameOrEmail(String username, String email) {
    return transaction.doInTransaction(() -> repository.findByUserNameOrEmail(username, email));
  }

  public SearchResult<User> findByFields(Search search, Map<String, String[]> map) {

    List<SearchField> searchFields = List.of(
        SearchField.builder()
            .field("id")
            .values(map.get("id"))
            .type(SearchFieldType.NUMBER)
            .build(),
        SearchField.builder()
            .field("fullName")
            .values(map.get("fullName"))
            .type(SearchFieldType.STRING)
            .build(),
        SearchField.builder()
            .field("username")
            .values(map.get("username"))
            .type(SearchFieldType.STRING)
            .build(),
        SearchField.builder()
            .field("email")
            .values(map.get("email"))
            .type(SearchFieldType.STRING)
            .build(),
        SearchField.builder()
            .field("role")
            .values(map.get("roleId"))
            .type(SearchFieldType.ARRAY)
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

  @Override
  public User saveOrUpdate(User user) throws BaseException {

    if (user.getId() == null) {
      User userExist = findByUserNameOrEmail(user.getUsername(), user.getEmail());
      if (userExist != null) {

        List<String> messages = new ArrayList<>();

        if (user.getUsername().equals(userExist.getUsername()))
          messages.add("Tài khoản đã tồn tại!");

        if (user.getEmail().equals(userExist.getEmail()))
          messages.add("Email đã tồn tại!");

        if (messages.size() > 0)
          throw new BadRequestException(messages);

      }

    }

    if (user.getPassword() != null) {
      String salt = BCrypt.gensalt(10);
      String hashPass = BCrypt.hashpw(user.getPassword(), salt);
      user.setPassword(hashPass);
    }

    return transaction.doInTransaction(() -> repository.saveOrUpdate(user));
  }

}
