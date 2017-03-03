<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
	
<c:set var="users" value="${users}" scope="page" />	
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
		
		<div class="col-sm-8 ">
			<div class="col-sm-12  ">
			<table class="table table-condensed ">
				<tr>
					<th class="cell">No.</th>
					<th>
						<span class="nowrap">Name Surname 
							 <a href="#">&#8679;</a>
							 <a href="#">&#8681;</a>
						</span>
					</th> 
					<th>
						<span class="nowrap">User Type
							 <a href="*">&#8679;</a>
							 <a href="#">&#8681;</a>
						</span>
					</th>
					<th>User Active/Inactive</th>
					<th>Change Activity Type</th>
					<th>Statistics</th> 				
					<th>Cancel Password</th> 
				</tr>
					
				<c:forEach var="user" items="${users}" varStatus="loop">
					
				<tr>
					<td>${loop.index+1}</td>
					<td class="cell"><c:out value="${user.name}"/>
									<c:out value="${user.surname}"/>
					<td class="cell"><c:out value="${user.userType}"/></td>
					<td class="cell"><c:out value="${user.userActive}"/>......</td>
					<c:set var="a" value="${user.id}" />
					<td class="cell">
						<a href ="showIncident?param=<c:out value="${a}"/>">Change Activity Type</a>
					</td>
					<td class="cell">
						<a href ="showIncident?param=<c:out value="${a}"/>">Statistics...</a>
					</td>
					<td class="cell">
						<a href ="showIncident?param=<c:out value="${a}"/>">Cancel pass...</a>
					</td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>
	