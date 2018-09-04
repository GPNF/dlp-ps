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
    <%@ include file="/parts/html-head.jsp" %>
    <link rel="stylesheet" type="text/css" href="./css/inspect.css">
    <title>DLP & PubSub</title>
    <style>
      td,th{
      padding:0px 10px;
      }
    </style>
  </head>
  <body>
    <%@ include file="/parts/header.jsp" %>
    <div class="container">
      <div class="row">
        <div class="col-md-6">
          <h4 style="margin-bottom: 20px; margin-top: 50px;">DLP -
            <%= svc.translate("Inspection and Deidentification") %>
          </h4>
          <div class="form-group">
            <label for="random-user-message"><%= svc.translate("Message") %>:</label> <i
              id="msg-loading"
              class="fa fa-spinner fa-pulse fa-1x fa-fw invisible"
              style="font-weight: bold"></i>
            <textarea id="random-user-message" class="form-control" rows="12"
              cols="8"></textarea>
          </div>
          <button id="inspect-btn" style="min-width: 105px;"
            class="btn btn-dark"><%= svc.translate("DeIdentify") %></button>
        </div>
        <!-- End of Column 1 -->
        <div class="col-md-6">
          <form id="publish-form" action="/topic/publish" method="POST">
            <h4 style="margin: 55px 0px 21px 0px;">Pub/Sub -
              <%= svc.translate("Publish") %>
            </h4>
            <div class="form-group">
              <label for="topic-name"><%= svc.translate("Topic Name") %>:</label> <select
                id="topic-name-select" name="topic-name"
                class="form-control">
              </select>
            </div>
            <div class="form-group">
              <label for="message" id="message-label"><%= svc.translate("Message") %>:</label>
              <textarea id="message" name="message" class="form-control"
                rows="8" cols="8" required></textarea>
            </div>
            <button id="publish-btn" style="min-width: 81px;"
              class="btn btn-dark"><%= svc.translate("Publish") %></button>
          </form>
        </div>
      </div>
      <!-- End of Row -->
      <div id="inspect-row" class="row">
        <div class="col-md-6">
          <div id="inspection-div" class="invisible">
            <h4><%= svc.translate("DLP Inspection") %></h4>
            <table id="inspect-table"
              class="table table-striped">
            </table>
          </div>
        </div>
        <div class="col-md-6"></div>
      </div>
      <!-- End of row -->
      <div style="margin-top: 50px;"></div>
    </div>
    <script type="text/javascript" src="./js/main.js"></script>
    <script type="text/javascript" src="/js/random-user.js"></script>
    <script type="text/javascript" src="./js/inspect-message.js"></script>
  </body>
</html>