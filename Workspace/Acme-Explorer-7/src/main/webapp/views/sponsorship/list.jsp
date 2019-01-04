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



<display:table name="sponsorships" id="row"
	requestURI="sponsorship/sponsor/list.do" pagesize="5"
	class="displaytag">



	<!-- Action links -->
		<display:column>
			<a href="sponsorship/sponsor/edit.do?sponsorshipId=${row.id}"> <spring:message
					code="sponsorship.edit" />
			</a>
		</display:column>
	

	<!-- Attributes -->


	<spring:message code="sponsorship.linkBanner" var="linkBannerHeader" />
	<display:column title="${linkBannerHeader}"
		sortable="true"><a href="${row.linkBanner}" target="_blank"> ${row.linkBanner} </a> </display:column>


	<spring:message code="sponsorship.linkInfoPage"
		var="linkInfoPageHeader" />
	<display:column title="${linkInfoPageHeader}"
		sortable="true"> <a href="${row.linkInfoPage}" target="_blank"> ${row.linkInfoPage} </a></display:column>


	
	<spring:message code="sponsorship.trip"
		var="tripHeader" />
		<spring:message code="sponsorship.trip.show"
		var="showTrip" />
		
		
	 <display:column title="${tripHeader}"> 
	 
	 
	<div>  <jstl:out value = "${row.trip.title}"/> &nbsp; (<a href="trip/display.do?tripId=${row.trip.id}"> ${showTrip} </a>)</div>
	 
	 
	  </display:column>




</display:table>


