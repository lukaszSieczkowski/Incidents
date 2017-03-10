<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
	


<c:set var="user" value="${user}" scope="session" />	
<c:set var="date" value="${date}" scope="session" />
<c:set var="chartForm" value="${chartForm}" scope="session" />
		
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

	<c:if test="${chartForm eq 'DONUT'}">
		<jsp:include page="/WEB-INF/views/fragments/donutChart.jsp"/>
	</c:if>

	<c:if test="${chartForm eq 'SLICED_PIE'}">
 		<jsp:include page="/WEB-INF/views/fragments/pieChart.jsp"/>
	</c:if>

	<c:if test="${chartForm eq '3D_PIE'}">
		<jsp:include page="/WEB-INF/views/fragments/3DPie.jsp"/>
	</c:if>
	
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
	
		<div class="col-sm-2 ">
			<div class="alert alert-warning">
				<strong>User:</strong> <c:out value="${user.name}"/> <c:out value="${user.surname}"/> </br> <strong>Role:</strong> <c:out value="${user.userType}"/></br>
				 <strong>Date:</strong> <c:out value="${date}"/>
			</div>
		</div>
	
		<div class="col-sm-8 ">
			<form  method="post" action="filterForChart">
			<div class="col-sm-2">
				<div class="form-group">
					<label for="datepicker">Start Date</label> 
					<input type="text" class="form-control" name="dateStart" placeholder="dd-mm-yyyy" id="datepicker">
				</div>
			</div>
			<div class="col-sm-2">
				<div class="form-group">		
					<label for="datepicker">End Date</label> 
					<input type="text" class="form-control" name="dateEnd" placeholder="dd-mm-yyyy" id="datepicker2">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="form-group">
					<label for="cathegory">Select Cathegory</label> 
					<select class="form-control" id="cathegory" name="cathegory">
						<option value="AREA">Area</option>
						<option value="EVENT_TYPE">Event Type</option>
						<option value="CATHEGORY_OF_PERSONEL">Cathegory of Personel</option>
					</select>
				</div>
			</div>
			<div class="col-sm-2">
				<div class="form-group">
					<label for="chartForm">Select Chart</label> 
					<select class="form-control" id="chartForm" name="chartForm">
						<option value="3D_PIE">3D Pie</option>
						<option value="SLICED_PIE">Sliced Pie</option>
						<option value="DONUT">Donut</option>
					</select>
				</div>
			</div>
			<div class="col-sm-2">
				<button type="submit" class="btn btn-warning btn-lg ">Filter</button>
			</div>
		</div>
		</form>
		<div id="chart"></div>	
</body>
</html>
	