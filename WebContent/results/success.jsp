<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Success</title>
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css" />
</head>
<body>
<%@ include file="/parts/header.html" %>
<div class="container">

<div class="card" style="width:300px;margin:100px auto;">
  
  <div class="card-header alert-success">Successfully Published</div>
  
  <div class="card-body">
	Global Transaction Id: <%= request.getAttribute("gbTxnId") %><br>
	<a class="btn btn-primary" style="margin:20px auto;" href="/index.jsp">&lt;&lt;Publish More</a>
  </div>
</div>

</div>



	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.min.js"></script>
</body>
</html>