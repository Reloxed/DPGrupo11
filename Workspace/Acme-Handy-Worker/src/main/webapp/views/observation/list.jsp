<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize
	access="hasAnyRole('CUSTOMER','HANDYWORKER','ADMINISTRATOR')">

	<display:table name="observations" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">
		<jstl:if test="${row.isFinal== true}">

			<!-- Attributes -->
			<spring:message code="observation.ticker" var="tickerHeader" />
			<display:column property="ticker" title="${tickerHeader}" />

			<spring:message code="observation.publishedMoment"
				var="publishedMomentHeader" />
			<display:column property="publishedMoment"
				title="${publishedMomentHeader}" sortable="true" />

			<spring:message code="observation.body" var="bodyHeader" />
			<display:column property="body" title="${bodyHeader}" />
			
			<spring:message	code="observation.fix"	var="fixHeader"/>
			<display:column	property="fixUpTask" title="${fixHeader}"	/>
		</jstl:if>

	</display:table>


</security:authorize>
<!-- Listing grid -->
