<!--
  - Author(s): AdarshSinghal
  - Date: 09/07/2018
  - Description: Fetches data from '/api/getMsgStatusCacheData' & renders into Datatable
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
<title>Message Status Table</title>
</head>
<body>
  <%@ include file="/parts/header.jsp"%>

  <div class="row">
    <div class="col-md-3"></div>
    <div class="col-md-6">

      <div id="message-status-data" class="mt-5 invisible">
        <h4><%=svc.translate("Message Status Cache Table")%></h4>
        <div id="loading-div" class="invisible">
          <i id="loading" class="fa fa-refresh fa-spin"></i>
        </div>
        <table id="message-status-datatable" class="display mt-5">
          <thead>
            <tr>
              <th>Global Transaction Id</th>
              <th>Delivery Status</th>
            </tr>
          </thead>
        </table>
      </div>

    </div>

    <div class="col-md-3"></div>

  </div>

  <script
    src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.min.js"></script>
  <script
    src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
  <script src="https://cdn.jsdelivr.net/g/mark.js(jquery.mark.min.js)"></script>
  <script
    src="https://cdn.datatables.net/plug-ins/1.10.13/features/mark.js/datatables.mark.js"></script>
  <script src="./js/message-status-table.js"></script>
</body>
</html>