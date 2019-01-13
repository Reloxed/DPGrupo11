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

	<display:table name="fixUpTasks" id="row"
		requestURI="fixUpTask/handyWorker/list.do" pagesize="10"
		class="displaytag">
		
		<jstl:set var="a" value="${row.startMoment}"/>			
		<jsp:useBean id="now" class="java.util.Date" />

		<spring:message code="fixUpTask.description" var="descriptionHeader" />

		<display:column property="description" title="${descriptionHeader}"
			sortable="true" />

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

		<jstl:set var="contains" value="${false}" />
		<jstl:forEach items="${fixUpTasks}" var="fix">
			<jstl:forEach items="${collFixUpTasks}" var="fixaux">
				<jstl:if test="${fix.ticker} eq ${fixaux.ticker}">
					<jstl:set var="contains" value="${true}" />
				</jstl:if>
			</jstl:forEach>
		</jstl:forEach>

		<display:column>
		
		<jstl:if test="${a > now}">
			<a href="application/handy-worker/create.do?fixUpTaskId=${row.id}">
				<!-- <img
				style="width: center; height: center" /> --> <spring:message
					code="fixUpTask.apply" />
			</a>
		</jstl:if>
		</display:column>

		<display:column>
			<a href="fixUpTask/handyWorker/display.do?taskId=${row.id}"> <spring:message
					code="fixUpTask.display" />
			</a>
		</display:column>

	</display:table>

</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">

	<display:table name="fixUpTasks" id="row"
		requestURI="fixUpTask/customer/list.do" pagesize="10"
		class="displaytag">

		<display:column>
			<a href="fixUpTask/customer/edit.do?fixUpTaskId=${row.id}"> <spring:message
					code="fixUpTask.edit" />
			</a>
		</display:column>

		<spring:message code="fixUpTask.description" var="descriptionHeader" />

		<display:column property="description" title="${descriptionHeader}"
			sortable="true" />



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
			<a href="fixUpTask/customer/display.do?fixUpTaskId=${row.id}"> <spring:message
						code="fixUpTask.display" /></a>
		</display:column>
		
		<display:column>
			<a href="application/customer/list-customer.do?fixUpTaskId=${row.id}"> <spring:message
						code="fixUpTask.applications" /></a>
		</display:column>


	</display:table>


</security:authorize>


