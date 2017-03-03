<%@page import="pl.incidents.model.Incident"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>

<c:set var="incident" value="${incident}" />

<c:set var="user" value="${user}" />
<c:set var="date" value="${date}" />



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

 <style type="text/css">
  	<%@include file="css/main.css" %>
</style>	

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>


</head>
<body>
	<div class="col-sm-12">
		<nav class="navbar ">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand">Incident Reporting Tool</a>
				</div>
				<ul class="nav navbar-nav">
					<li><a href="uReportIncident">Report Incident</a></li>
					<li><a href="uShowIncidents">Show Reported Incidents</a></li>
					
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="logout"><span
							class="glyphicon glyphicon-log-in"></span> Logout</a></li>
				</ul>
			</div>
		</nav>
		<div class="col-sm-2 ">
			<div class="alert alert-warning" role="alert">
				<strong>User:</strong>
				<c:out value="${user.name}" />
				<c:out value="${user.surname}" />
				</br> <strong>Role:</strong>
				<c:out value="${user.userType}" />
				</br> <strong>Date:</strong>
				<c:out value="${date}" />
			</div>
		</div>


		<div class="col-sm-8 ">
			<table class="table table-bordered">
				<tr>
					<td class="cell2"><strong>Incident Date</strong></td>
					<td><c:set var="date"
							value="${fn:substring(incident.incidentDate, 0, 10)}" /> <c:out
							value="${date}" /></td>
				</tr>
				<tr>
					<td class="cell2"><strong>Incident Time</strong></td>
					<td><c:set var="time"
							value="${fn:substring(incident.incidentDate, 11, 16)}" /> <c:out
							value="${time}" /></td>
				</tr>

				<tr>
					<td class="cell2"><strong>Area</strong></td>
					<td><c:out value="${incident.area}" /></td>
				</tr>
				<tr>
					<td class="cell2"><strong>Event Type</strong></td>
					<td><c:out value="${incident.typeOfObservation}" /></td>
				</tr>
				<tr>
					<td class="cell2"><strong>Exact Location</strong></td>
					<td><c:out value="${incident.location}" /></td>
				</tr>
				<tr>
					<td class="cell2"><strong>Cathegory of personel</strong></td>
					<td><c:out value="${incident.cathegoryOfPersonel}" /></td>
				</tr>
				<tr>
					<td class="cell2"><strong>Event Type</strong></td>
					<td><c:out value="${incident.typeOfObservation}" /></td>
				</tr>
				<tr>
					<td class="cell2"><strong>Details of Safety</strong></td>
					<td><c:out value="${incident.details}" /></td>
				</tr>
				<tr>
					<td class="cell2"><strong>Immediate Action
							Taken/Recommended</strong></td>
					<td><c:out value="${incident.action}" /></td>
				</tr>
				<tr>
					<td class="cell2"><strong>Supervisior Informed</strong></td>
					<td><c:out value="${incident.supervisorInformed}" /></td>
				</tr>
				<tr>
					<td class="cell2"><strong>Incident Reported By</strong></td>
					<td></td>
				</tr>
			</table>
			<div>
			<a href="uShowIncidents"><button id="go_back_button" type="submit" class="btn btn-warning btn-lg " >Go back</button></a>
			</div>
		


</body>
</html>
</html>