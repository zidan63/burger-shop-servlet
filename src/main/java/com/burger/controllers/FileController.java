package com.burger.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

import com.burger.annotation.Middlewares;
import com.burger.annotation.RequirePermissions;
import com.burger.enums.PermissionCode;
import com.burger.middlewares.AuthMiddleware;
import com.burger.middlewares.PermissionMiddleware;
import com.burger.others.FileUpload;
import com.burger.others.RequestAuth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet({ "/file/download", "/file/upload" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10, // 10MB
    maxRequestSize = 1024 * 1024 * 50 // 50MB
)
@Middlewares({ AuthMiddleware.class, PermissionMiddleware.class })
public class FileController extends BaseController {
  @Override
  protected void doGet(RequestAuth request, HttpServletResponse response)
      throws ServletException, IOException {
    // Đường dẫn tới thư mục lưu trữ file
    // String uploadPath = getServletContext().getRealPath("") + File.separator +
    // "uploads";
    String uploadPath = "/uploads";

    // Lấy tên file cần tải từ parameter
    String fileName = request.getParameter("fileName");
    File file = new File(uploadPath + File.separator + fileName);

    // Đọc file và gửi về client
    FileInputStream inputStream = new FileInputStream(file);

    // Lấy kiểu MIME của file
    String mimeType = getServletContext().getMimeType(file.getAbsolutePath());
    if (mimeType == null) {
      mimeType = "application/octet-stream";
    }

    response.setContentType(mimeType);
    response.setContentLength((int) file.length());

    // Thiết lập header cho response
    String headerKey = "Content-Disposition";
    String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
    response.setHeader(headerKey, headerValue);

    // Gửi dữ liệu file về client
    int bytesRead;
    byte[] buffer = new byte[4096];
    while ((bytesRead = inputStream.read(buffer)) != -1) {
      response.getOutputStream().write(buffer, 0, bytesRead);
    }

    inputStream.close();
  }

  @Override
  @RequirePermissions({ PermissionCode.CREATE_PRODUCT })
  protected void doPost(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {

    // Get the upload directory real path
    // String uploadPath = getServletContext().getRealPath("") + File.separator +
    // "uploads";
    String uploadPath = "/uploads";

    // Create the upload directory if it doesn't exist
    File uploadDir = new File(uploadPath);
    if (!uploadDir.exists()) {
      uploadDir.mkdir();
    }

    // Create a unique file name using UUID
    String fileName = UUID.randomUUID().toString();

    // Process each part in the request
    for (Part part : req.getParts()) {
      // Get the submitted file name
      String submittedFileName = getSubmittedFileName(part);
      submittedFileName = submittedFileName.substring(submittedFileName.lastIndexOf('.') + 1);

      // If the submittedFileName is not empty, it means the part represents a file
      if (submittedFileName != null && !submittedFileName.isEmpty()) {
        // Construct the absolute file path
        String filePath = uploadPath + File.separator + fileName + "." + submittedFileName;

        // Save the file to the server
        part.write(filePath);

        // You can store the file path in a database or use it as needed
        // For simplicity, we'll just print the file path to the response
        resp.getWriter().write(gson.toJson(new FileUpload(fileName + "." + submittedFileName)));
      }
    }
  }

  private String getSubmittedFileName(Part part) {
    String contentDispositionHeader = part.getHeader("content-disposition");
    String[] elements = contentDispositionHeader.split(";");
    for (String element : elements) {
      if (element.trim().startsWith("filename")) {
        return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
      }
    }
    return null;
  }
}