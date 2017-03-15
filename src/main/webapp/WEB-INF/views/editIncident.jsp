<%@page import="pl.incidents.model.Incident"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>

<c:set var="userWithDetails" value="${userWithDetails}" />
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
				<strong>User:</strong> <c:out value="${user.name}" /> <c:out value="${user.surname}" /></br>
				<strong>Role:</strong> <c:out value="${user.userType}" /></br> 
				<strong>Date:</strong> <c:out value="${date}" />
			</div>
		</div>
		<div class="col-sm-8 ">
			<div class="form-group">
				<p class="help-block text-center"><c:out value="${pageScope.alert}"/></p>
			</div>
			<form  method="post" action="editIncident?param=<c:out value="${incident.id}"/>">
			<table class="table table-bordered">
				<tr>
					<td class="cell"><strong>Incident Date</strong></td>
					<td>
						<c:set var="date" value="${fn:substring(incident.incidentDate, 0, 10)}" /> <c:out value="${date}" />
					</td>
				</tr>
				<tr>
					<td ><strong>Incident Time</strong></td>
					<td><c:set var="time" value="${fn:substring(incident.incidentDate, 11, 16)}" /> 
						<c:out value="${time}" /></td>
				</tr>
				<tr>
					<td ><strong>Area</strong></td>
					<td>
						<c:out value="${incident.area}" />
						<select required class="form-control" id="place" name="area">
							<option value="">----- Select one -----</option>
							<option value="OFFICE">Office</option>
							<option value="WORKSHOP">Workshop</option>
							<option value="PARK_LOOT">Park Loot</option>
							<option value="PROJECT_SITE">Project Site</option>
						</select>
					</td>
				</tr>
				<tr>
					<td ><strong>Event Type</strong></td>
					<td>
						<c:out value="${incident.typeOfObservation}" />
						<select class="form-control" id="evenType" required name="typeOfObservation" >
							<option value="">----- Select one -----</option>
							<option value="UNSAFE_ACT">Unsafe Act</option>
							<option value="UNSAFE_CONDITIONS">Unsafe Conditions</option>
							<option value="SAFE_BEHAVIOURS">Safe Behaviours</option>
						</select>
					</td>
				</tr>
				<tr>
					<td ><strong>Exact Location</strong></td>
					<td>
						<c:out value="${incident.location}" />
						<textarea class="form-control" rows="2" id="location" required name="location"> </textarea>
					</td>
				</tr>
				<tr>
					<td ><strong>Cathegory of personel</strong></td>
					<td>
						<c:out value="${incident.cathegoryOfPersonel}" />
						<select class="form-control" id="personel" required name="cathegoryOfPersonel">
							<option value="">----- Select one -----</option>
							<option value="OWN">Own</option>
							<option value="CONTRACTOR">Contractor</option>
							<option value="CLIENT">Client</option>
							<option value="THIRD_PARTY">Third Party</option>
						</select>
					</td>
				</tr>
				<tr>
					<td ><strong>Details of Safety</strong></td>
					<td>
						<c:out value="${incident.details}" />
						<textarea class="form-control" rows="5" id="safetyDetails" required name="details"></textarea>
					</td>
				</tr>
				<tr>
					<td ><strong>Immediate Action Taken/Recommended</strong></td>
					<td>
						<c:out value="${incident.action}" />
						<textarea class="form-control" rows="5" id="action" required name="action"></textarea>
					</td>
				</tr>
				<tr>
					<td ><strong>Supervisior Informed</strong></td>
					<td>
						<c:out value="${incident.supervisorInformed}" /></br>
						<label class="radio-inline"><input type="radio" value="YES" name="supervisorInformed">Yes</label>
						<label class="radio-inline"><input type="radio" value= "NO" checked="checked" name="supervisorInformed">No</label>
					</td>
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
			</table>
			<div>
				<button id="go_back_button" type="submit"
						class="btn btn-warning btn-lg ">Update</button>
			</div>
			</form>
		</div>
	</div>
</body>
</html>
