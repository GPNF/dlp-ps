<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Publisher</title>
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>
</head>
<body>

<jsp:include page="parts/header.html" />

	<div class="row" style="margin-top: 50px;">

		<div class="col-md-4"></div>

		<div class="col-md-4">

			<form action="/publish" method="POST">

				<div class="form-group">
					<label for="topic-name">Topic Name:</label> <input type="text"
						id="topic-name" class="form-control" name="topic-name">
				</div>

				<div class="form-group">
					<label for="message" id="message-label">Message:</label>
					<textarea id="message" name="message" class="form-control" rows="5"
						cols="8"></textarea>
				</div>
				<button type="submit" class="btn btn-success">Publish</button>
                
			</form>

		</div>


		<div class="col-md-4"></div>


	</div>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.min.js"></script>
  <script src="js/main.js"></script>
</body>
</html>