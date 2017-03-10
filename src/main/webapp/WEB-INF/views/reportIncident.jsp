<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>

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

	<c:if test="${user.userType eq 'USER'}">
   		<jsp:include page="/WEB-INF/views/fragments/menuUser.jsp"/>
	</c:if>
	
	<c:if test="${user.userType eq 'ADMIN'}">
   		<jsp:include page="/WEB-INF/views/fragments/menuAdmin.jsp"/>
	</c:if>
	
		<div class="col-sm-2 ">
			<div class="alert alert-warning" role="alert">
				<strong>User:</strong> <c:out value="${user.name}"/> <c:out value="${user.surname}"/> </br> <strong>Role:</strong> <c:out value="${user.userType}"/></br>
				 <strong>Date:</strong> <c:out value="${date}"/>
			</div>
		</div>

		<form  method="post" action="saveIncident">
			<div class="col-sm-4 ">
				<div class="form-group">
					<label for="datepicker">Observation Date</label> <input type="text"
						class="form-control" name="date" placeholder="dd-mm-yyyy" required
						id="datepicker">
				</div>
				<div class="form-group ">
					<label for="time" id="labelTime">Observation Time</label> <select
						class="form-control" id="time" name="hour">
						<option>00</option>
						<option>01</option>
						<option>02</option>
						<option>03</option>
						<option>04</option>
						<option>05</option>
						<option>06</option>
						<option>07</option>
						<option>08</option>
						<option>09</option>
						<option>10</option>
						<option>11</option>
						<option>12</option>
						<option>13</option>
						<option>14</option>
						<option>15</option>
						<option>16</option>
						<option>17</option>
						<option>18</option>
						<option>19</option>
						<option>20</option>
						<option>21</option>
						<option>22</option>
						<option>23</option>
					</select> : <select class="form-control" id="time2" name="minute">
						<option>00</option>
						<option>15</option>
						<option>30</option>
						<option>45</option>
					</select>
				</div>
				<div class="form-group">
					<label for="place">Area</label> <select required
						class="form-control" id="place" name="area">
						<option value="">----- Select one -----</option>
						<option value="OFFICE">Office</option>
						<option value="WORKSHOP">Workshop</option>
						<option value="PARK_LOOT">Park Loot</option>
						<option value="PROJECT_SITE">Project Site</option>
					</select>
				</div>
				<div class="form-group">
					<label for="location">Exact Location</label>
					<textarea class="form-control" rows="2" id="location" required name="location"> </textarea>
				</div>
				<div class="form-group">
					<label for="eventType">Event Type</label> <select
						class="form-control" id="evenType" required name="typeOfObservation">
						<option value="">----- Select one -----</option>
						<option value="UNSAFE_ACT">Unsafe Act</option>
						<option value="UNSAFE_CONDITIONS">Unsafe Conditions</option>
						<option value="SAFE_BEHAVIOURS">Safe Behaviours</option>
					</select>
				</div>
				<div class="form-group">
					<label for="personel">Cathegory of Personeel</label> <select
						class="form-control" id="personel" required name="cathegoryOfPersonel">
						<option value="">----- Select one -----</option>
						<option value="OWN">Own</option>
						<option value="CONTRACTOR">Contractor</option>
						<option value="CLIENT">Client</option>
						<option value="THIRD_PARTY">Third Party</option>
					</select>
				</div>
			</div>
			<div class="col-sm-4 col-sm-offset-1 ">
				<div class="form-group">
					<label for="safetyDetails">Details of Safety
						Observation</label>
					<textarea class="form-control" rows="5" id="safetyDetails" required name="details"></textarea>
				</div>
			</div>
			<div class="col-sm-4 col-sm-offset-1 ">
				<div class="form-group">
					<label for="action">Immediate Action Taken/Recommended</label>
					<textarea class="form-control" rows="5" id="action" required name="action"></textarea>
				</div>
			</div>
			<div class="col-sm-4 col-sm-offset-1 ">
				<div class="form-group">
					<label for="supervisor">Supervisor Informed ?</label><br> 
					<label class="radio-inline"><input type="radio" value="YES" name="supervisorInformed">Yes</label>
					<label class="radio-inline"><input type="radio" value= "NO" checked="checked" name="supervisorInformed">No</label>
				</div>
			</div>
			<div class="col-sm-4 col-sm-offset-1 ">
				<div class="form-group">
					<label for="exampleInputFile">Supporting Informations</label> <input
						type="file" id="exampleInputFile">
				</div>
			</div>
			<div class="col-sm-4 col-sm-offset-1 ">	
				<button type="submit" class="btn btn-warning btn-lg ">Submit</button>
			</div>
		</form>
</body>
</html>
