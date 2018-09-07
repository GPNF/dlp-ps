<!--
  - Author(s): AdarshSinghal
  - Date: 09/07/2018
  - Description: Message Source Page
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
<title>Message Source</title>
</head>
<body>
  <%@ include file="/parts/header.jsp"%>
  <div class="container">

    <div class="row mt-5">

      <div class="col-md-3"></div>

      <div class="col-md-6">

        <div>

          <form id="msg-src-form" action="/notify" method="POST">

            <div class="form-group">
              <label for="src-auth-level-select"><%=svc.translate("Source Authorization Level")%></label>
              <select id="src-auth-level-select" name="src-auth-level"
                class="form-control">
                <option value="0">External User (0)</option>
                <option value="1">Merchant (1)</option>
                <option value="2">Executive (2)</option>
                <option value="3">Administrator (3)</option>
              </select>
            </div>

            <div class="form-group">
              <label for="group-id-select"><%=svc.translate("Target Group Id")%></label>
              <select id="group-id-select" name="group-id"
                class="form-control">
                <option value="1">G1 &emsp;&emsp;(Requires Source Authorization Level >= 1)</option>
                <option value="2">G2 &emsp;&emsp;(Requires Source Authorization Level >= 2)</option>
                <option value="3">G3 &emsp;&emsp;(Does not exist)</option>
              </select>
            </div>


            <div class="form-group">
              <label for="random-user-message" id="message-label"><%=svc.translate("Message")%>:</label>

              <i id="msg-loading"
                class="fa fa-spinner fa-pulse fa-1x fa-fw invisible"
                style="font-weight: bold"></i>

              <textarea id="random-user-message" name="message"
                class="form-control" rows="8" cols="8" required>Sample Text</textarea>
            </div>

            <button id="submit-btn" style="min-width: 81px;"
              class="btn btn-dark"><%=svc.translate("Submit")%></button>
              
              <c:if test="${isExceptionOccured}">
              <span class="float-right" style="color:red">${exceptionMsg}</span>
              </c:if>
              
          </form>
        </div>

      </div>

      <div class="col-md-3"></div>

    </div>

  </div>


  <script type="text/javascript" src="/js/random-user.js"></script>
  <script type="text/javascript" src="/pages/js/message-source.js"></script>

</body>
</html>