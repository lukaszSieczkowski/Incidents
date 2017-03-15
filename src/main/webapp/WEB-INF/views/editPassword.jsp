<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
	
<c:set var="alert" value="${alert}" scope="page" />	
		
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
	
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>	

<style type="text/css">
  	<%@include file="css/main.css" %>
</style>

<title>Incident Reporting Tool</title>
	
</head>
<body>

	<c:if test="${user.userType eq 'USER'}">
   		<jsp:include page="/WEB-INF/views/fragments/menuUser.jsp"/>
	</c:if>
	
	<c:if test="${user.userType eq 'ADMIN'}">
   		<jsp:include page="/WEB-INF/views/fragments/menuAdmin.jsp"/>
	</c:if>
	<div class="col-sm-12">
		<div class="col-sm-2 ">
			<div class="alert alert-warning" role="alert">
				<strong>User:</strong> <c:out value="${user.name}" /> <c:out value="${user.surname}" /></br>
				<strong>Role:</strong> <c:out value="${user.userType}" /></br> 
				<strong>Date:</strong> <c:out value="${date}" />
			</div>
		</div>
	
		<div class="col-sm-4 ">
			<form  method="post" action="changePassword" >
				<div class="form-group">
					<label for="oldPassword">Old Password</label> 
					<input type="password"  class="form-control"  id="oldPassword" required  name="oldPassword"/> 
				</div>
				<div class="form-group">
					<label for="newPassword1">New Password</label> 
					<input type="password" class="form-control"  id="newPassword1" required  name="newPassword1"/> 
				</div>
				<div class="form-group">
					<label for="newPassword2">Repeat New Password</label> 
					<input type="password" class="form-control"   id="newPassword2" required name="newPassword2"/>
				</div>
				
				<div class="form-group">
					<p class="help-block text-center"><c:out value="${pageScope.alert}"/></p>
				</div>
				<button type="submit" class="btn btn-warning btn-lg change_password_button">Change Password</button>
			</form>
		</div>
	</div>
</body>
</html>
