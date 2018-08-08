<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Published Data</title>
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" />
<style>

table tr th{
  background:#337ab7;
  color:white;
  text-align:left;
  vertical-align:center;
}
</style>
  <script
    src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
    <jsp:include page="/parts/header.html" />
	<div id="publish-data" class="container mt-5">
		<table id="publish-datatable" class="display" style="width: 100%">
			<thead>
				<tr>
					<th>Message Id</th>
					<th>Message</th>
					<th>Topic</th>
					<th>Publish Time</th>
				</tr>
			</thead>
		</table>
	</div>






	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
    

	
  <script src="js/publishdata.js"></script>
</body>
</html>