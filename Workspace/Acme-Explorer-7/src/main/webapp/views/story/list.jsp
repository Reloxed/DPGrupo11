<%--
 * 
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



<display:table name="stories" id="row"
	requestURI="story/explorer/list.do" pagesize="5"
	class="displaytag">



	<!-- Action links -->
	
		<display:column>
			<a href="story/explorer/edit.do?storyId=${row.id}"> <spring:message
					code="story.edit" />
			</a>
		</display:column>

	



	<!-- Attributes -->


	<spring:message code="story.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="true" />


	<spring:message code="story.trip"
		var="tripHeader" />
		<spring:message code="story.trip.show"
		var="showTrip" />
		
		
	 <display:column title="${tripHeader}"> 
	 
	 
	<div>  <jstl:out value = "${row.trip.title}"/> &nbsp; (<a href="trip/display.do?tripId=${row.trip.id}"> ${showTrip} </a>)</div>
	 
	 
	  </display:column>


	<display:column>
			<a href="story/display.do?storyId=${row.id}"> <spring:message
					code="story.display" />
			</a>
		</display:column>


</display:table>


