<!--
  - Author(s): AdarshSinghal
  - Date: 09/07/2018
  - Description: This JSP is displayed on Error 500 received from Server. Check web.xml for mapping.
-->

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
<%@ include file="/parts/html-head.jsp"%>
<title>Internal Server Error</title>
</head>
<body>
  <%@ include file="/parts/header.jsp"%>
  <div class="container mt-5">
    <h1 class="mt-5 text-center">'500' means Internal Server Error</h1>

    <h3 class="mt-5 text-center">This is an intermittent issue. Try
      again.</h3>

    <h6 class="mt-5 text-center">While we are working hard to
      resolve the issue, you should checkout other features.</h6>
  </div>




</body>
</html>