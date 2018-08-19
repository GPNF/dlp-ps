<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sync Pull Client</title>
<link
  href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
  rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<jsp:include page="/parts/header.html" />
  <div class="container" style="margin-top: 50px;">
    <div class="row">
      <div class="col-md-4"></div>
      <div class="col-md-4">
        <div id="sync-pull-form-div">
          <form id="pull-form" action="/pullmessage" method="POST">
            <div class="form-group">
              <label for="max-message">Maximum Message</label> <input
                id="max-message" name="max-message" type="number"
                value="1" min="1" class="form-control" />
            </div>
            <div class="form-group">
              <label for="return-immediately">Return Immediately</label>
              <select id="return-immediately" name="return-immediately"
                class="form-control">
                <option value="true">True</option>
                <option value="false">False</option>
              </select>
            </div>
            <button id="pull-msg-btn" style="width:133px;" class="btn btn-primary">Pull Messages</button>
            <c:if test="${noMsg}">
             <span class="text-success" style="margin-left:10px;">No new messages.</span>
            </c:if>
          </form>
        </div>


        <!-- sync-pull-form-div -->
      </div>
      <!-- End of mid col -->
      <div class="col-md-4">
        <c:forEach items="${messageList}" var="msg">
          <div class="card" style="padding: 20px;margin-bottom:20px;background-color: #E1FFC5">
            Message Id: ${msg.messageId}<hr>
            Message: ${msg.message}<hr>
            Publish Time: ${msg.publishTime}
          </div>
        </c:forEach>
      </div>
    </div>
    <!-- End of Row -->

  </div>
  <!-- End of container -->

  
  <script
    src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
     <script src="/pages/js/syncpullclient.js"></script>
</body>
</html>