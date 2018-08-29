<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activity Logging</title>
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/plug-ins/1.10.13/features/mark.js/datatables.mark.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="/pages/dbtables/css/datatable-style.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="/parts/header.html" />
	<div class="container">
		<div id="logging-data" class="mt-5 invisible">
			<table id="logging-datatable" class="display">
				<thead>
					<tr>
						<th>Id</th>
						<th>Message Id</th>
						<th>Message</th>
                        <th>Topic Name</th>
						<th>Subscription Name</th>
						<th>Published Time</th>
						<th>Global Txn Id</th>
						
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<div id="loading-div" class="invisible">
		<i id="loading" class="fa fa-refresh fa-spin"
			style="margin-left: calc(50% - 75px); font-size: 150px"></i>
	</div>


	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
	<script src="https://cdn.jsdelivr.net/g/mark.js(jquery.mark.min.js)"></script>
	<script
		src="https://cdn.datatables.net/plug-ins/1.10.13/features/mark.js/datatables.mark.js"></script>


	<script src="/pages/dbtables/js/activity-logging-table.js"></script>
</body>
</html>