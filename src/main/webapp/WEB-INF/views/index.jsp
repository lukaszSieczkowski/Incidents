<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<c:set var="alert" value="${alert}" scope="page" />	

<html>
<head>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <!-- Latest compiled and minified JavaScript -->
       	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        
        <style type="text/css">
  			<%@include file="css/index.css" %>
		</style>

     	<title>Incident Reporting Tool</title>
</head>
<body style=
    "background-image: url("../pics/safety.jpg");"
 >
	 <div class="container-fluid">
     	<div class = "upper_container">
			<div class="col-md-5 col-md-offset-3 text-center ">
				<h1>Incident Reportig Tool</br>
			</div>  
		</div>
            <div class="middle_container">
				<div class="col-md-5 col-md-offset-3 ">
				<form class="form-horizontal" method="post" action="login">
					<div class="form-group">
						<label for="username">Email address</label>
						<div class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
							<input type="email" class="form-control"id="username" name="username" placeholder="Email">
						</div>
					</div>
					<div class="form-group">
						<label for="password">Password</label>
						<div class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
							<input type="password" class="form-control" id="password" name="password" placeholder="Password">
						</div>
					</div>
					<div class="form-group">
						<p class="help-block text-center"><c:out value="${pageScope.alert}"/></p>
					</div>
					<div class="form-group">
						<div class="text-center">
							<action type="submit"><button type="submit" class="btn btn-warning"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span>     Login</button>
						</div>
					</div>
				</form>
				</div>
            </div>
        </div>
</body>
</html>