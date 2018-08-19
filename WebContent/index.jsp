<%@ page pageEncoding="UTF8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Publisher</title>
<link rel="stylesheet" type="text/css"
  href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script
  src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>
</head>
<body>

  <jsp:include page="parts/header.html" />

  <div class="container">
    <div class="row" style="margin-top: 50px;">

      <div class="col-md-6">

        <h3 style="margin-bottom: 20px;">DLP - Inspect</h3>
        <div class="form-group">
          <label for="inspect-message">Message:</label>
          
          <textarea id="inspect-message" class="form-control"
            rows="12" cols="8"></textarea>
        </div>
        <button id="inspect-btn" style="width:81px;" class="btn btn-success">Inspect</button>
      </div>

      <div class="col-md-6">
        <h3 style="margin-bottom: 21px;">Pub/Sub - Publish</h3>
        <form id="publish-form" action="/topic/publish" method="POST">

          <div class="form-group">
            <label for="topic-name">Topic Name:</label> 
            <i id="topic-loading" class="fa fa-spinner fa-pulse fa-1x fa-fw"></i>
              
              <select id="topic-name-select" name="topic-name" class="form-control">
              </select>
          </div>

          <div class="form-group">
            <label for="message" id="message-label">Message:</label>
            
            <textarea id="message" name="message" class="form-control"
              rows="8" cols="8" required></textarea>
          </div>
          <button id="publish-btn" style="width:81px;" type="submit" class="btn btn-success">Publish</button>

        </form>

      </div>

    </div>
    <!-- End of Row -->
  </div>



  <script
    src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.min.js"></script>
  <script src="js/main.js"></script>
  <script src="js/inspect-message.js"></script>
  
</body>
</html>