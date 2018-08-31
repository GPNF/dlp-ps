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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Internationalization</title>
    <link
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
      rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css">
    <script
      src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  </head>
  <body>
    <%@ include file="/parts/header.jsp" %>
    <div class="container mt-5">
      <h4 class="mt-5">Internationalization</h4>
      <p class="mt-5">Internationalization, in relation to computer
        programming, is the process of designing and writing an
        application so that it can be used in a global or multinational
        context. An internationalized program is capable of supporting
        different languages, as well as date, time, currency, and other
        values, without software modification. This usually involves "soft
        coding" or separating textual components from program code and may
        involve pluggable code modules.
      </p>
      <p class="mt-5">Internationalization is often shortened to I18N
        by practitioners. The rationale is that there are 18 letters
        between the beginning I and final N in internationalization. Try
        saying and writing "internationalization" more than a few times
        and you will appreciate the value of the shorter version.
        Additionally, you may see "I18N'ed" as a shortened form of
        "internationalized." While grammatically imprecise and technically
        incorrect, "I18N'ed" is useful and you will see it frequently in
        the literature, including this tutorial.
      </p>
    </div>
    <script
      src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
  </body>
</html>