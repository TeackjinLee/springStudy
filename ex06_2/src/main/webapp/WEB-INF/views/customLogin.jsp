<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Bootstrap Admin Theme</title>

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
</head>
<body>
	<h1>Custom Login Page</h1>
	<h2><c:out value="${error}" /></h2>
	<h2><c:out value="${logout}" /></h2>
	
	<form method="post" action="/login">
	
		<fieldset>
			<div class="form-group">
			    <input class="form-control" placeholder="userid" name="username" type="text" autofocus>
			</div>
			<div class="form-group">
			    <input class="form-control" placeholder="Password" name="password" type="password" value="">
			</div>
			<div class="checkbox">
			    <label>
			        <input name="remember-me" type="checkbox">Remember Me
			    </label>
			</div>
			<!-- Change this to a button or input when using this as a form -->
			<a href="index.html" class="btn btn-lg btn-success btn-block">Login</a>
		</fieldset>
		<input type='hidden' name="${_csrf.parameterName}" value="${_csrf.token}" />
		
	</form>
	
	<!-- jQuery -->
    <script src="/resources/vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="/resources/vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="/resources/dist/js/sb-admin-2.js"></script>
    
    <script>
    	const btn_success = document.querySelector(".btn-success");

        btn_success.addEventListener("click", function(e){
            e.preventDefault();
            let form = document.querySelector('form');
            form.submit();
        });
    </script>
	
</body>
</html>