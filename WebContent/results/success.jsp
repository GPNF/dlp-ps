<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Success</title>
<link rel="stylesheet" type="text/css"
  href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css" />
</head>
<body>
  <%@ include file="/parts/header.html"%>
  <div class="container">

    <div class="card" style="width: 500px; margin: 100px auto;">

      <div class="card-header alert-success">Successfully
        Published on ${fn:length(messageIds)} topics</div>

      <div class="card-body">
        <b>Global Transaction Id:</b><br/>
        <%=request.getAttribute("gbTxnId")%><hr>
         <b>Message Id:</b> <br/>
        <c:forEach items="${messageIds}" var="msgId">
            ${msgId}<br/>
        </c:forEach>
        <hr> 
        
        <div style="text-align:center">
          <a class="btn btn-primary float-left"
          href="/index.jsp">&lt;&lt;Back</a>
          <span class="span4 text-center"><a class="btn btn-success"
          href="/pages/publishdata.jsp">Show Table</a></span>
          <a class="btn btn-primary float-right"
          href="/pages/syncpullclient.jsp">Pull&gt;&gt;</a>
         </div>
        
        
        
          
          
      </div>
    </div>

  </div>



  <script
    src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.min.js"></script>
</body>
</html>