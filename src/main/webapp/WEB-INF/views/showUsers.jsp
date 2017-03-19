<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
	
<c:set var="alert" value="${alert}" scope="page" />	
<c:set var="users" value="${users}" scope="session" />			
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

<style type="text/css">
  	<%@include file="css/main.css" %>
</style>	
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
				<strong>User:</strong> <c:out value="${user.name}"/> <c:out value="${user.surname}"/> </br> 
				<strong>Role:</strong> <c:out value="${user.userType}"/></br>
				<strong>Date:</strong> <c:out value="${date}"/>
			</div>
		</div>
		
		<div class="col-sm-7  col-sm-offset-1  ">
			<div class="form-group">
				<p class="help-block text-center"><c:out value="${pageScope.alert}"/></p>
			</div>
			<table class="table table-condensed ">
				<tr>
					<th class="cell2">No.</th>
					<th>
						<span class="nowrap">Name Surname 
							 <a href="sortByUserSurname?param=desc">&uarr;</a>
							 <a href="sortByUserSurname?param=asc">&darr;</a>
						</span>
					</th> 
					<th>
						<span class="nowrap">User Type
							  <a href="sortByUserType?param=desc">&uarr;</a>
							 <a href="sortByUserType?param=asc">&darr;</a>
						</span>
					</th>
					<th>
						<span class="nowrap">User Active/Inactiv
							  <a href="sortByUserActive?param=desc">&uarr;</a>
							  <a href="sortByUserActive?param=asc">&darr;</a>
						</span>
					</th>
			
					<th>Statistics</th> 				
					<th>Show Details</th> 
				</tr>
			
				<c:forEach var="user_temp" items="${users}" varStatus="loop">
					
				<tr>
					<td>${loop.index+1}</td>
					<td><c:out value="${user_temp.name}"/>
									<c:out value="${user_temp.surname}"/>
					<td><c:out value="${user_temp.userType}"/></td>
					<td><c:out value="${user_temp.userActive}"/>......</td>
					<c:set var="a" value="${user_temp.id}" />
					<td>
						<a href ="showIncidentsForUser?param=<c:out value="${a}"/>&chartForm=3D_PIE">show Statistics</a>
					</td>
					
					<td>
						<a href ="showUserDetails?param=<c:out value="${a}"/>">Show User Details</a>
					</td>
				</tr>
				</c:forEach>
			
			</table>
		</div>
	</div>
</body>
</html>
	