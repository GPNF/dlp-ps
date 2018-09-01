<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="app.service.TextTranslator, java.util.Locale"%>
<%
  Locale locale = request.getLocale();
  TextTranslator translator = new TextTranslator(locale.getLanguage());
  %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Pull Client</title>
    <link
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
      rel="stylesheet">
    <link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script
      src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  </head>
  <body>
    <%@ include file="/parts/header.jsp" %>
    <div class="container" style="margin-top: 50px;">
      <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
          <div id="sync-pull-form-div">
            <form id="pull-form" action="/pullmessage" method="POST">
              <div class="form-group">
                <label for="max-message"><%=translator.translate("Maximum Message")%></label> <input
                  id="max-message" name="max-message" type="number"
                  value="100" min="1" class="form-control" />
              </div>
              <div class="form-group">
                <label for="return-immediately"><%=translator.translate("Return Immediately")%></label>
                <select id="return-immediately" name="return-immediately"
                  class="form-control">
                  <option value="true"><%=translator.translate("True")%></option>
                  <option value="false"><%=translator.translate("False")%></option>
                </select>
              </div>
              <button id="pull-msg-btn" style="min-width: 133px;"
                class="btn btn-dark"><%=translator.translate("Pull Messages")%></button>
                
               <a id="pull-datatable-btn" href="/pages/dbtables/pulldata.jsp" target="_blank"
                class="btn btn-dark float-right"><%=translator.translate("Pull Table")%>&gt;&gt;</a>
              <c:if test="${noMsg}">
                <div class="text-success"><%=translator.translate("No new messages")%>.</div>
              </c:if>
            </form>
          </div>
          <!-- sync-pull-form-div -->
        </div>
        <!-- End of mid col -->
        <div class="col-md-4"></div>
      </div>
      <!-- End of Row -->
      <c:forEach items="${messageList}" var="msg">
        <div class="row">
          <div class="card mt-5"
            style="padding: 20px; margin-bottom: 20px; background-color: #E1FFC5; margin: 0px auto">
            GlobalTxnId: ${msg.globalTransactionId}
            <hr>
            Message Id: ${msg.messageId}
            <hr>
            <%
              pageContext.setAttribute("newLineChar", "\n");
              %>
            Message: ${fn:replace(msg.message, newLineChar, '<br/>')}
            <hr>
            Publish Time: ${msg.publishTime}
          </div>
        </div>
      </c:forEach>
    </div>
    <!-- End of container -->
    <script
      src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <script src="./js/syncpullclient.js"></script>
  </body>
</html>