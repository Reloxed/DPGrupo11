<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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



  
<security:authorize access="hasRole('HANDYWORKER')"> 

	<display:table name="fixUpTasks" id="row" requestURI="fixUpTask/handyWorker/list.do"
		pagesize="10" class="displaytag">




		<display:column>
			<a href="actor/display.do?profileId=${row.id}"> <spring:message
					code="profile.customer" />
			</a>
		</display:column>

		<display:column>

			<spring:message code="fixUpTask.description" var="descriptionHeader" />
			<a href="${descriptionHeader}"> </a>
		</display:column>



		<spring:message code="fixUpTask.address" var="addressHeader" />
		<display:column property="address" title="${addressHeader}"
			sortable="true" />


		<spring:message code="fixUpTask.maxPrice" var="maxPriceHeader" />
		<display:column property="maxPrice" title="${maxPriceHeader}"
			sortable="true" />


		<spring:message code="fixUpTask.startMoment" var="startMomentHeader" />
		<display:column property="startMoment" title="${startMomentHeader}"
			sortable="true" />


		<spring:message code="fixUpTask.endMoment" var="endMomentHeader" />
		<display:column property="endMoment" title="${endMomentHeader}"
			sortable="true" />

		<display:column>
			<a href="handyWorker/applications.do?fixUpTaskId=${row.id}"> <img
				src="webapp/images/confirm.png"
				style="width: center; height: center" /> <spring:message
					code="applications.create" />
			</a>
		</display:column>

	</display:table>

</security:authorize> 


