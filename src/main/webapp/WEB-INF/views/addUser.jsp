<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
	
<c:set var="alert" value="${alert}" scope="page" />	
<c:set var="user" value="${user}" scope="session" />	
<c:set var="date" value="${date}" scope="session" />
		
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
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="jquery.timepicker.js"></script>

<script>
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : "dd-mm-yy",
			inline : true,
			dayNamesMin : [ 'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat' ],
		});
	});
</script>
<style type="text/css">
  	<%@include file="css/main.css" %>
</style>	
</head>
<body>

	<c:if test="${user.userType == 'USER'}">
   		<jsp:include page="/WEB-INF/views/fragments/menuUser.jsp"/>
	</c:if>
	
	<c:if test="${user.userType == 'ADMIN'}">
   		<jsp:include page="/WEB-INF/views/fragments/menuAdmin.jsp"/>
	</c:if>
	
		<div class="col-sm-2 ">
			<div class="alert alert-warning" role="alert"  accept-charset="UTF-8"/>
				<strong>User:</strong> <c:out value="${user.name}"/> <c:out value="${user.surname}"/> </br> <strong>Role:</strong> <c:out value="${user.userType}"/></br>
				 <strong>Date:</strong> <c:out value="${date}"/>
			</div>
		</div>
		<div class="col-sm-4 ">
			<form  method="post" action="saveUser" >
				<div class="form-group">
					<label for="name">First Name</label> 
					<input type="text"  class="form-control"  id="name" required  name="name"/> 
				</div>
				<div class="form-group">
					<label for="surname">Last Name</label> 
					<input type="text" class="form-control"  id="surname" required  name="surname"/> 
				</div>
				<div class="form-group">
					<label for="email">Email</label> 
					<input type="email" class="form-control"   id="email" required name="email"/>
				</div>
				<div class="form-group">
					<label for="userType">Role</label> 
					<select required class="form-control" id="userType" name="userType">
						<option value="">----- Select one -----</option>
						<option value="USER">User</option>
						<option value="ADMIN">Admin</option>
					</select>
				</div>
				<div class="form-group">
					<p class="help-block text-center"><c:out value="${pageScope.alert}"/></p>
				</div>
				<button type="submit" class="btn btn-warning btn-lg ">Save User</button>
			</form>
		</div>
</body>
</html>
