<%--
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>




<!-- Listing grid -->

<!--  La variable de modelo 'survivalClasses' es una variable que puede englobar unos objetos survivalClass u otros, dependiendo del controlador -->



<display:table name="survivalClasses" id="row"
	requestURI="survivalClass/manager/list.do" pagesize="5"
	class="displaytag">
	

	<!-- Action links -->
	
	
	
	
		<display:column>

			<jstl:if test="${futureNoCancelledTrips.contains(row.trip)}">
				<a href="survivalClass/manager/edit.do?survivalClassId=${row.id}"> <spring:message
					code="survivalClass.edit" />
				</a>			
			</jstl:if>
		
	
		</display:column>
	



	<!-- Attributes -->

	<spring:message code="survivalClass.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="true" />

	
	
	
	<spring:message code="survivalClass.moment"
	var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		sortable="true" format = "{0,date,dd/MM/yyyy HH:mm}"/>



	
	<spring:message code="survivalClass.trip.title"
		var="tripHeader" />
		<spring:message code="survivalClass.trip.show"
		var="showTrip" />
		
		
	 <display:column title="${tripHeader}"> 
	 
	 
	<div>  <jstl:out value = "${row.trip.title}"/> &nbsp; (<a href="trip/display.do?tripId=${row.trip.id}"> ${showTrip} </a>)</div>
	 
	 
	  </display:column>



	
	


<!-- Action links -->

	<display:column>
			<a href="survivalClass/display.do?survivalClassId=${row.id}"> <spring:message
					code="survivalClass.display" />
			</a>
		</display:column>


</display:table>






