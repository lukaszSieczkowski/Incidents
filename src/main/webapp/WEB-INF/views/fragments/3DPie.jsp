<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
	
<c:set var="map" value="${map}" />	
<c:set var="startDate" value="${startDate}"  />	
<c:set var="endDate" value="${endDate}"  />	

<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<script type="text/javascript">
	google.charts.load('current', {packages: ['corechart']});     
</script>

<script language="JavaScript">
	function drawChart() {
   		var data = new google.visualization.DataTable();
   		data.addColumn('string', 'Area');
   		data.addColumn('number', 'Percentage');
   		data.addRows([
	   		<c:forEach var="entry" items="${map}">
				['<c:out value="${entry.key}"/>', <c:out value="${entry.value}"/>],
			</c:forEach>
  	 	]);
   		var options = {'title':'Incidents reported from <c:out value="${startDate}"/> until <c:out value="${endDate}"/>',
						'width':600,
						'height':400,
       					is3D:true
   		};
   			var chart = new google.visualization.PieChart(document.getElementById('chart'));
   			chart.draw(data, options);
	}
	google.charts.setOnLoadCallback(drawChart);
</script>