package com.burger.others;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.burger.entities.User;
import com.burger.enums.PermissionCode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RequestAuth extends HttpServletRequestWrapper {

  User userCurrent;
  Map<String, List<PermissionCode>> permissionCodesPerMethod = new HashMap<>();

  public RequestAuth(HttpServletRequest request) {
    super(request);
  }

  public List<PermissionCode> getPermissionCode() {
    return permissionCodesPerMethod.get(this.getMethod());
  }

  public void putPermissionCode(String methodName, List<PermissionCode> permissionCodes) {
    String method = "GET";
    switch (methodName) {
      case "doGet":
        method = "GET";
        break;

      case "doPost":
        method = "POST";
        break;

      case "doPut":
        method = "PUT";
        break;

      case "doDelete":
        method = "DELETE";
        break;
    }

    permissionCodesPerMethod.put(method, permissionCodes);

  }

}
