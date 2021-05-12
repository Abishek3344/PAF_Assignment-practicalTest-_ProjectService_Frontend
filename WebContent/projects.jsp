<%@page import="com.Project"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>GB Project</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Component/jquery-3.6.0.min.js"></script>
<script src="Component/items.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Project Management</h1>
				<form id="formItem" name="formItem">
					Project code: <input id="projectCode" name="projectCode" type="text"
						class="form-control form-control-sm"> <br> Project
					name: <input id="projectName" name="projectName" type="text"
						class="form-control form-control-sm"> <br> Project
					Fund: <input id="projectFund" name="projectFund" type="text"
						class="form-control form-control-sm"> <br> Project
					description: <input id="projectDesc" name="projectDesc" type="text"
						class="form-control form-control-sm"> <br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidItemIDSave" name="hidItemIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divItemsGrid">
					<%
						Project projectObj = new Project();
						out.print(projectObj.readProjects());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>