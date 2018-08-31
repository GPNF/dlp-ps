<%@ page pageEncoding="UTF8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Publisher</title>
<link rel="stylesheet" type="text/css"
  href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="./css/inspect.css">
<script
  src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>

<style>

td,th{
padding:0px 10px;
}

</style>

</head>
<body>

  <jsp:include page="parts/header.html" />

  <div class="container">
    <div class="row" style="margin-top: 50px;">

      <div class="col-md-6">

        <table class="table-bordered" >
          <thead>
            <tr>
              <th>Auth Level</th>
              <th>Description</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>0</td>
              <td>No access to application</td>
            </tr>
            <tr>
              <td>1,2,3</td>
              <td>Will be used along with Group Auth level</td>
            </tr>
          </tbody>
        </table>

        <h4 style="margin-bottom: 20px; margin-top: 50px;">DLP -
          Inspection and De-identification</h4>
        <div class="form-group">
          <label for="inspect-message">Message:</label> <i
            id="dlp-msg-loading"
            class="fa fa-spinner fa-pulse fa-1x fa-fw invisible"
            style="font-weight: bold"></i>

          <textarea id="inspect-message" class="form-control" rows="12"
            cols="8"></textarea>
        </div>
        <button id="inspect-btn" style="width: 105px;"
          class="btn btn-success">DeIdentify</button>

      </div>
      <!-- End of Column 1 -->

      <div class="col-md-6">


        <form id="publish-form" action="/topic/publish" method="POST">

          <div class="form-group">
            <label for="src-auth-level-select">Source Auth Level</label>
            <select id="src-auth-level-select" name="src-auth-level"
              class="form-control">
              <option value="0">0</option>
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
            </select>
          </div>

          <h4 style="margin: 65px 0px 21px 0px;">Pub/Sub -
            Publish</h4>
          <div class="form-group">
            <label for="topic-name">Topic Name:</label> <select
              id="topic-name-select" name="topic-name"
              class="form-control">
            </select>
          </div>

          <div class="form-group">
            <label for="message" id="message-label">Message:</label>

            <textarea id="message" name="message" class="form-control"
              rows="8" cols="8" required></textarea>
          </div>
          <button id="publish-btn" style="width: 81px;"
            class="btn btn-success">Publish</button>

        </form>

      </div>

    </div>
    <!-- End of Row -->

    <div id="inspect-row" class="row">
      <div class="col-md-6">
        <div id="inspection-div" class="invisible">
          <h4>DLP Inspection</h4>
          <table id="inspect-table"
            class="table table-bordered table-striped">

          </table>
        </div>
      </div>

      <div class="col-md-6"></div>
    </div>
    <!-- End of row -->
    <div style="margin-top: 50px;"></div>

  </div>



  <script
    src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.min.js"></script>
  <script src="js/main.js"></script>
  <script src="js/random-user.js"></script>
  <script src="js/inspect-message.js"></script>

</body>
</html>