<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8" content="text/html" http-equiv="Content-Type">
<title>Insert title here</title>
</head>
<body>
	<h1>/sample/all page</h1>
	<!-- isAnonyMouse() : 익명의 사용자의 경우(로그인을 하지 않은 경우도 해당) -->
	<sec:authorize access="isAnonymous()">
		
		<a href="/customLogin">로그인</a>
			
	</sec:authorize>
</body>
</html>