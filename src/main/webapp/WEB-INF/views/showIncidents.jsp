<%@page import="pl.incidents.model.Incident"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="x"  uri="http://java.sun.com/jsp/jstl/xml" %>

<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
	
<c:set var="incidents" value="${incidents}" scope="session"/>
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
<script>
	$(function() {
		$("#datepicker2").datepicker({
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
		<form  method="post" action="filterIncidents">
			<div class="col-sm-2 ">
				<div class="form-group">
					<label for="datepicker">Observation Date</label> 
					<input type="text" class="form-control" name="dateStart" placeholder="dd-mm-yyyy" id="datepicker">
				</div>
				<div class="form-group">		
					<label for="datepicker">Observation Date</label> 
					<input type="text" class="form-control" name="dateEnd" placeholder="dd-mm-yyyy" id="datepicker2">
				</div>
			</div>
			<div class="col-sm-2 col-sm-offset-1 ">
				<div class="form-group">
					<label for="place">Area</label> 
					<select class="form-control" id="place" name="area">
						<option value="">--------- All ---------</option>
						<option value="OFFICE">Office</option>
						<option value="WORKSHOP">Workshop</option>
						<option value="PARK_LOOT">Park Loot</option>
						<option value="PROJECT_SITE">Project Site</option>
					</select>
				</div>
				<div class="form-group">
					<label for="eventType">Event Type</label> 
					<select class="form-control" id="evenType"  name="typeOfObservation">
						<option value="">--------- All ---------</option>
						<option value="UNSAFE_ACT">Unsafe Act</option>
						<option value="UNSAFE_CONDITIONS">Unsafe Conditions</option>
						<option value="SAFE_BEHAVIOURS">Safe Behaviours</option>
					</select>
				</div>
			</div>
			<div class="col-sm-2 col-sm-offset-1 ">
				<div class="form-group">
					<label for="personel">Cathegory of Personeel</label> <select
						class="form-control" id="personel" name="cathegoryOfPersonel">
						<option value="">--------- All ---------</option>
						<option value="OWN">Own</option>
						<option value="CONTRACTOR">Contractor</option>
						<option value="CLIENT">Client</option>
						<option value="THIRD_PARTY">Third Party</option>
					</select>
					<button type="submit" class="btn btn-warning btn-lg ">Submit</button>
				</div>
			</div>
		</form>
		<div class="col-sm-12  ">
			<table class="table table-condensed ">
				<tr>
					<th id="id">No.</th>
					<th>Status</th>
					<th class="date" ><div>
					<span class="nowrap">Incident  
							 <a href="sortByIncidentDate?param=desc">&uarr;</a>
							 <a href="sortByIncidentDate?param=asc"> &darr;</a>
						</span>
					</div>
					<div>Date ​</div>
						
					</th> 
					<th>
						<span class="nowrap">Area ​ 
							 <a href="sortByArea?param=desc">&uarr;</a>
							 <a href="sortByAreaAsc?param=asc"> &darr;</a>
						</span>
					</th>
					<th>Exact location</th>
					<th>
						<span class="nowrap">Event Type ​ 
							 <a href="sortByEvent?param=desc">&uarr;</a>
							 <a href="sortByEvent?param=asc"> &darr;</a>
						</span>
					</th> 					
					<th id="personel">
						<span class="nowrap">Personel
							 <a href="sortByPersonel?param=desc">&uarr;</a>
							 <a href="sortByPersonel?param=asc"> &darr;</a>
						</span>
					</th> 
					<th>Details of Safety</th>
					<th>Action Taken</th> 
					<th id="supervisor">
						<div>
							<span class="nowrap">Supervisor  
								<a href="sortBySupervisorInf?param=desc">&uarr;</a>
								<a href="sortBySupervisorInf?param=asc"> &darr;</a>
							</span>
						</div>
						<div>informed</div>
					</th> 
					<th id="reportedBy">
						<span class="nowrap">Reported by
							 <a href="sortByReporter?param=desc">&uarr;</a>
							 <a href="sortByReporter?param=asc"> &darr;</a>
						</span>
					</th> 
					<th class="date" ><div>
					<span class="nowrap">Reporting 
							 <a href="sortByReportingDate?param=desc">&uarr;</a>
							 <a href="sortByReportingDate?param=asc"> &darr;</a>
						</span>
					</div>
					<div>Date ​</div>
					<th id="details"><div>Show</div><div> Details</div></th>
				</tr>
					
				<c:forEach var="incident" items="${incidents}" varStatus="loop">
					
				<tr>
					<td>${loop.index+1}</td>
					<td
					<c:if test="${incident.incidentStatus eq 'OPEN'}">
						style="color:green;"
					</c:if>	
					<c:if test="${incident.incidentStatus eq 'NOT_APPROVED'}">
						style="color:red;"
					</c:if>	
					>${incident.incidentStatus}</td>
					<td ><c:out value="${fn:substring(incident.incidentDate, 0, 10)}"/></br>
									<c:out value="${fn:substring(incident.incidentDate, 11,16)}"/></td>
					<td><c:out value="${incident.area}"/></td>
					<td><c:out value="${fn:substring(incident.location, 0, 80)}"/>......</td>
					<td><c:out value="${incident.typeOfObservation}"/></td>
					<td><c:out value="${incident.cathegoryOfPersonel}"/></td>
					<td><c:out value="${fn:substring(incident.details, 0, 80)}"/>......</td>
					<td><c:out value="${fn:substring(incident.action, 0, 80)}"/>......</td> 
					<td> <c:out value="${incident.supervisorInformed}"/></td>
					<td>
						<c:out value="${incident.user.name}"/> 
						<c:out value="${incident.user.surname}"/> 
					</td>
					<td><c:out value="${fn:substring(incident.reportingDate, 0, 10)}"/>
									<c:out value="${fn:substring(incident.reportingDate, 11,16)}"/></td>
									 
					<c:set var="a" value="${incident.id}" />
					<td>
						<a href ="showIncident?param=<c:out value="${a}"/>">Details...</a>
					</td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>