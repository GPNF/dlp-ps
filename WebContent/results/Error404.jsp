<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="app.service.TextTranslator, java.util.Locale"%>
<%
	Locale locale = request.getLocale();
	TextTranslator svc = new TextTranslator(locale.getLanguage());
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/parts/html-head.jsp"%><title>Not Found</title>
</head>

<style>

table{
margin:0px auto;
padding:5px;
}

td,th{
padding: 2px 20px;
}

thead>tr{
background-color:black; color:white;
}

</style>

<body >
  <%@ include file="/parts/header.jsp"%>
  <div class="container mt-5">

    <h1 class="mt-5 text-center">
      <b>'404' means Page Not Found</b>
    </h1>

    <h1 class="mt-5 text-center" class="mt-5">Why it occurrs?</h1>

    <table class="table-bordered table-striped mt-3">

      <thead>

        <tr>
          <th>Reasons</th>
          <th>Chances</th>
        </tr>
      </thead>

      <tbody>
        <tr>
          <td>You typed the URL wrong</td>
          <td>Very High</td>
        </tr>
        <tr>
          <td>The page is not implemented or under construction</td>
          <td>Moderate</td>
        </tr>

        <tr>
          <td>The Developer provided wrong URL or incorrect
            relative path</td>
          <td>Rare</td>
        </tr>
      </tbody>

    </table>

  </div>


</body>
</html>