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
<title>Message Source</title>
</head>
<body>
  <%@ include file="/parts/header.jsp"%>
  <div class="container">

    <div class="row mt-5">

      <div class="col-md-3"></div>

      <div class="col-md-6">

        <div>

          <form>

            <div class="form-group">
              <label for="src-auth-level-select"><%=svc.translate("Source Authorization Level")%></label>
              <select id="src-auth-level-select" name="src-auth-level"
                class="form-control">
                <option value="0">0</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
              </select>
            </div>

            <div class="form-group">
              <label for="group-id-select"><%=svc.translate("Group Id")%></label>
              <select id="group-id-select" name="group-id"
                class="form-control">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
              </select>
            </div>


            <div class="form-group">
              <label for="random-user-message" id="message-label"><%=svc.translate("Message")%>:</label>

              <i id="msg-loading"
                class="fa fa-spinner fa-pulse fa-1x fa-fw invisible"
                style="font-weight: bold"></i>

              <textarea id="random-user-message" name="message"
                class="form-control" rows="8" cols="8" required></textarea>
            </div>

            <button id="submit-btn" style="min-width: 81px;"
              class="btn btn-dark"><%=svc.translate("Submit")%></button>

          </form>

        </div>

      </div>

      <div class="col-md-3"></div>

    </div>

  </div>


  <script type="text/javascript" src="/js/random-user.js"></script>

</body>
</html>