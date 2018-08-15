<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pulled Message</title>
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" />
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/plug-ins/1.10.13/features/mark.js/datatables.mark.min.css" />
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
	<div id="pull-data" class="mt-5">
		<table id="pull-datatable" class="display" style="">
			<thead>
				<tr>
                    <th>Pull Time</th>
                    <th>Published Time</th>
					<th>Message Id</th>
                    <th>Message</th>
                    <th>Subscription</th>
					<th>Subscriber</th>
					<th>Global Txn Id</th>
                    <th>Ack Id</th>
				</tr>
			</thead>
		</table>
	</div>






	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
		<script src="https://cdn.jsdelivr.net/g/mark.js(jquery.mark.min.js)"></script>
    <script src="https://cdn.datatables.net/plug-ins/1.10.13/features/mark.js/datatables.mark.js"></script>

	
  <script src="js/pulldata.js"></script>
</body>
</html>