<%@page import="pl.incidents.model.Incident"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>

<c:set var="userWithDetails" value="${userWithDetails}" />

<!DOCTYPE html>
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

<style type="text/css">
  	<%@include file="css/main.css" %>
</style>

<title>Incident Reporting Tool</title>

</head>
<body>

	<c:if test="${user.userType eq 'USER'}">
		<jsp:include page="/WEB-INF/views/fragments/menuUser.jsp" />
	</c:if>

	<c:if test="${user.userType eq 'ADMIN'}">
		<jsp:include page="/WEB-INF/views/fragments/menuAdmin.jsp" />
	</c:if>

	<div class="col-sm-12">
		<div class="col-sm-2 ">
			<div class="alert alert-warning" role="alert">
				<strong>User:</strong> <c:out value="${user.name}"/> <c:out value="${user.surname}"/> </br> 
				<strong>Role:</strong> <c:out value="${user.userType}"/></br>
				<strong>Date:</strong> <c:out value="${date}"/>
			</div>
		</div>
		<div class="col-sm-8 ">
		<div class="form-group">
					<p class="help-block text-center"><c:out value="${pageScope.alert}"/></p>
				</div>
			<table class="table table-bordered">
				<tr>
					<td><strong>Name</strong></td>
					<td><c:out value="${userWithDetails.name}" /></td>
				</tr>
				<tr>
					<td><strong>Surname</strong></td>
					<td><c:out value="${userWithDetails.surname}" /></td>
				</tr>
				<tr>
					<td><strong>User Type</strong></td>
					<td><c:out value="${userWithDetails.userType}" /></td>
				</tr>
				<tr>
					<td><strong>User Active/Inactive</strong></td>
					<td><c:out value="${userWithDetails.userActive}" /></td>
				</tr>
				<tr>
					<td><strong>Actions</strong></td>
					<td>
						<div>

							<a href="changeUserType?param=<c:out value="${userWithDetails.id}"/>">
								<button  type="submit" class="btn btn-warning btn-lg smallButton">Change User Type</button>
							</a>
			
							<a href="changeUserActivity?param=<c:out value="${userWithDetails.id}"/>">
								<button  type="submit" class="btn btn-warning btn-lg smallButton">Activate/Deactivate User</button>
							</a>
				
							<a href="resetUserPassword?param=<c:out value="${userWithDetails.id}"/>">
								<button   type="submit" class="btn btn-warning btn-lg smallButton">Reset Password</button>
							</a>
						</div>
					</td>
				</tr>
			</table>
			<div>
				<a href="getUsers">
					<button id="go_back_button" type="submit" class="btn btn-warning btn-lg ">Go back</button>
				</a>
			</div>
		</div>
	</div>
</body>
</html>
