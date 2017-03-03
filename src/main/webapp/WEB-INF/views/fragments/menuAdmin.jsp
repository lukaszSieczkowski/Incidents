
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>


<nav class="navbar ">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand">Incident Reporting Tool</a>
		</div>
		<ul class="nav navbar-nav">
			<li class="dropdown"><a href="#" data-toggle="dropdown"
				class="dropdown-toggle">Users Manager <b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href="#">Inbox</a></li>
					<li><a href="#">Drafts</a></li>
					<li><a href="#">Sent Items</a></li>
					<li class="divider"></li>
					<li><a href="#">Trash</a></li>
				</ul></li>

			<li><a href="reportIncident">Report Incident</a></li>
			<li><a href="showIncidents">Show Reported Incidents</a></li>

		</ul>
		<ul class="nav navbar-nav navbar-right">
			<li><a href="logout"><span
					class="glyphicon glyphicon-log-in"></span> Logout</a></li>
		</ul>
	</div>
</nav>