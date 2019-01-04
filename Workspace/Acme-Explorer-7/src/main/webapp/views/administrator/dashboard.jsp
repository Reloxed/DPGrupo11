

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<security:authorize access="hasRole('ADMIN')">
	
	<h3><spring:message code="administrator.statistics" /></h3>
	
	<table class="displayStyle">
		<tr>
			<th colspan="5"><spring:message code="administrator.statistics" /></th>
		</tr>
		
		<tr>
			<th><spring:message code="administrator.metrics" /></th>
			<th><spring:message code="administrator.average" /></th>
			<th><spring:message code="administrator.minimum" /></th>
			<th><spring:message code="administrator.maximum" /></th>
			<th><spring:message code="administrator.std" /></th>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.applicationsPerTrip" /></td>
			<td>${avgApplicationsPerTrip }</td>
			<td>${minApplicationsPerTrip }</td>
			<td>${maxApplicationsPerTrip }</td>
			<td>${stdApplicationsPerTrip }</td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.TripsPerManager" /></td>
			<td>${avgTripsPerManager }</td>
			<td>${minTripsPerManager }</td>
			<td>${maxTripsPerManager }</td>
			<td>${stdTripsPerManager }</td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.PricePerTrips" /></td>
			<td>${avgPricePerTrips }</td>
			<td>${minPricePerTrips }</td>
			<td>${maxPricePerTrips }</td>
			<td>${stdPricePerTrips }</td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.TripsPerRanger" /></td>
			<td>${avgTripsPerRanger }</td>
			<td>${minTripsPerRanger }</td>
			<td>${maxTripsPerRanger }</td>
			<td>${stdTripsPerRanger }</td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.NotesPerTrip" /></td>
			<td>${avgNotesPerTrip }</td>
			<td>${minNotesPerTrip }</td>
			<td>${maxNotesPerTrip }</td>
			<td>${stdNotesPerTrip }</td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.AuditsPerTrip" /></td>
			<td>${avgAuditsPerTrip }</td>
			<td>${minAuditsPerTrip }</td>
			<td>${maxAuditsPerTrip }</td>
			<td>${stdAuditsPerTrip }</td>
		</tr>
		
	</table>
	
	<h3><spring:message code="administrator.ratios" /></h3>
	
	<table class="displayStyle">
		<tr>
			<th colspan="2"><spring:message code="administrator.ratios" /></th>
		</tr>
		
		<tr>
			<th><spring:message code="administrator.metrics" /></th>
			<th><spring:message code="administrator.value" /></th>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.ratioStatusPENDING" /></td>
			<td>${ratioStatusPENDING }</td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.ratioStatusDUE" /></td>
			<td>${ratioStatusDUE }</td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.ratioStatusACCEPTED" /></td>
			<td>${ratioStatusACCEPTED }</td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.ratioStatusCANCELLED" /></td>
			<td>${ratioStatusCANCELLED }</td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.ratioCancelledTrips" /></td>
			<td>${ratioCancelledTrips }</td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.ratioTripsWithAudit" /></td>
			<td>${ratioTripsWithAudit }</td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.ratioRangersWithCV" /></td>
			<td>${ratioRangersWithCV }</td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.ratioRangersWithCVEndorsed" /></td>
			<td>${ratioRangersWithCVEndorsed }</td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.ratioSuspiciousRangers" /></td>
			<td>${ratioSuspiciousRangers }</td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.ratioSuspiciousManagers" /></td>
			<td>${ratioSuspiciousManagers }</td>
		</tr>
		
	</table>
	
	<h3><spring:message code="administrator.tripMostApplications" /></h3>
	
	<display:table pagesize="10" class="displaytag" 
	name="tripMostApplications" requestURI="dashboard/administrator/display.do" id="trip">
		
		<spring:message code="administrator.ticker" var="ticker" />
		<display:column property="ticker" title="${ticker }" sortable="true"/>
		
		<spring:message code="administrator.title" var="title" />
		<display:column property="title" title="${title }" sortable="true"/>
		
		<spring:message code="administrator.description" var="description" />
		<display:column property="description" title="${description }" sortable="true"/>
		
		<spring:message code="administrator.price" var="price" />
		<display:column property="price" title="${price }(&euro;)" sortable="true"/>
		
		<spring:message code="administrator.publicationDate" var="publicationDate" />
		<display:column property="publicationDate" title="${publicationDate }" sortable="true" format = "{0,date,dd/MM/yyyy}"/>
		
		<spring:message code="administrator.manager" var="manager" />
		<display:column property="manager.userAccount.username" title="${manager }" sortable="true"/>
		
		<spring:message code="administrator.ranger" var="ranger" />
		<display:column property="ranger.userAccount.username" title="${ranger }" sortable="true"/>
		
		<display:column>
			<a href="trip/display.do?tripId=${trip.id }"><spring:message code="administrator.displayTrip"  /></a>
		</display:column>
	</display:table>
	
	<h3><spring:message code="administrator.legaltextGroupedByNumberOfTrips" /></h3>
	
	<display:table pagesize="10" class="displaytag" 
	name="legaltextGroupedByNumberOfTripsKeys" requestURI="dashboard/administrator/display.do" id="legaltext" offset="0">
	
	<spring:message code="administrator.legalText.title" var="title" />
	<display:column property="title" title="${title }" sortable="true"/>
	
	<spring:message code="administrator.legalText.body" var="body" />
	<display:column property="body" title="${body }" sortable="true"/>
	
	<spring:message code="administrator.legalText.registerDate" var="registerDate" />
	<display:column property="registerDate" title="${registerDate }" sortable="true" format = "{0,date,dd/MM/yyyy}"/>
	
	<spring:message code="administrator.legalText.numberReferences" var="references" />
	<display:column title="${references }">
		<jstl:out value="${legaltextGroupedByNumberOfTripsValues[0]}" />
	</display:column>
	
	<display:column>
		<a href="legalText/administrator/display.do?legalTextId=${legaltext.id }"><spring:message code="administrator.legalText.display"/></a>
	</display:column>
	
		
	
	</display:table>
	
</security:authorize>
