<!--
  - Author(s): AdarshSinghal
  - Date: 09/07/2018
  - Description: Fetches data from '/pulldata' & renders into Datatable
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
<link rel="stylesheet" type="text/css"
  href="/pages/dbtables/css/datatable-style.css" />
<title>Subscriber2</title>
</head>
<body>
  <%@ include file="/parts/header.jsp"%>
  <div style="margin:0px 15px 0px 5px">
    <div id="pull-data" class="mt-5 invisible">
      <h4><%=svc.translate("Notification Service Pull Table")%></h4>
      <div id="loading-div" class="invisible">
        <i id="loading" class="fa fa-refresh fa-spin"></i>
      </div>
      <table id="pull-datatable" class="display">
        <thead>
          <tr>
            <th>Pull Time</th>
            <th>Published Time</th>
            <th>Message Id</th>
            <th>Message</th>
            <th>Subscription</th>
            <th>Global Txn Id</th>
            <th>Ack Id</th>
          </tr>
        </thead>
      </table>

    </div>
  </div>


  <script
    src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.min.js"></script>
  <script
    src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
  <script src="https://cdn.jsdelivr.net/g/mark.js(jquery.mark.min.js)"></script>
  <script
    src="https://cdn.datatables.net/plug-ins/1.10.13/features/mark.js/datatables.mark.js"></script>
  <script src="./js/pulldata.js"></script>
</body>
</html>