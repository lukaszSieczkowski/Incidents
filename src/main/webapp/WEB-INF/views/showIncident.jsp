<%@page import="pl.incidents.model.Incident"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>

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
		<div class="col-sm-8 ">
			<table class="table table-bordered">
				<tr>
					<td class="cell"><strong>Incident Date</strong></td>
					<td><c:set var="date"
							value="${fn:substring(incident.incidentDate, 0, 10)}" /> <c:out value="${date}" /></td>
				</tr>
				<tr>
					<td ><strong>Incident Time</strong></td>
					<td><c:set var="time" value="${fn:substring(incident.incidentDate, 11, 16)}" /> 
						<c:out value="${time}" /></td>
				</tr>
				<tr>
					<td ><strong>Area</strong></td>
					<td><c:out value="${incident.area}" /></td>
				</tr>
				<tr>
					<td ><strong>Event Type</strong></td>
					<td><c:out value="${incident.typeOfObservation}" /></td>
				</tr>
				<tr>
					<td ><strong>Exact Location</strong></td>
					<td><c:out value="${incident.location}" /></td>
				</tr>
				<tr>
					<td ><strong>Cathegory of personel</strong></td>
					<td><c:out value="${incident.cathegoryOfPersonel}" /></td>
				</tr>
				
				<tr>
					<td ><strong>Details of Safety</strong></td>
					<td><c:out value="${incident.details}" /></td>
				</tr>
				<tr>
					<td ><strong>Immediate Action
							Taken/Recommended</strong></td>
					<td><c:out value="${incident.action}" /></td>
				</tr>
				<tr>
					<td ><strong>Supervisior Informed</strong></td>
					<td><c:out value="${incident.supervisorInformed}" /></td>
				</tr>
				<tr>
					<td ><strong>Incident Reported By</strong></td>
					<td>
						<c:out value="${incident.user.name}"/> 
						<c:out value="${incident.user.surname}"/> 
					</td>
				</tr>
				<tr>
					<td ><strong>Incident Status</strong></td>
					<td>
						<c:out value="${incident.incidentStatus}"/> 
					</td>
				</tr>
			
				<c:if test="${user.userType eq 'ADMIN'}">
				
   				<tr>
					<td ><strong>Actions</strong></td>
					<td>
						<div>
							<a href="closeIncident?param=<c:out value="${incident.id}"/>">
								<button  type="submit" class="btn btn-warning btn-lg smallButton">Close/Open Incident</button>
							</a>
			
							<a href="aproveIncident?param=<c:out value="${incident.id}"/>">
								<button  type="submit" class="btn btn-warning btn-lg smallButton">Approve/Don't approve</button>
							</a>
							
							<c:if test="${incident.incidentStatus eq 'OPEN'}">
								
								<a href="showEditIncident?param=<c:out value="${incident.id}"/>">
									<button  type="submit" class="btn btn-warning btn-lg smallButton">Edit Incident</button>
								</a>
								
							</c:if>
						</div>
					</td>
				</tr>
				
				</c:if>
				
			</table>
			<div>
			<a href="showIncidents"><button id="go_back_button" type="submit" class="btn btn-warning btn-lg " >Go back</button></a>
		</div>
		</div>
	</div>
</body>
</html>
