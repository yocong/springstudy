<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Web Study</title>
</head>
<body>

<h1>파일 업로드 예제</h1>
  <form action="/upload/file" method="post" enctype="multipart/form-data">
    <%--   form태그의 enctype="multipart/form-data" 속성이 있어야 한다.  --%>
    <input type="file" name="thumbnail" id="img-input" accept="image/*">
    <button type="submit">전송</button>
  </form>

</body>
</html>