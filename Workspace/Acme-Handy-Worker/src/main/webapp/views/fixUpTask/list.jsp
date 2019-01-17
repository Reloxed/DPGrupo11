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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<security:authorize access="hasRole('HANDYWORKER')">

	<display:table name="fixUpTasks" id="row"
		requestURI="fixUpTask/handyWorker/list.do" pagesize="10"
		class="displaytag">

		<spring:message code="fixUpTask.description" var="descriptionHeader" />
		<display:column property="description" title="${descriptionHeader}"
			sortable="true" />

		<spring:message code="fixUpTask.address" var="addressHeader" />
		<display:column property="address" title="${addressHeader}" />


		<jstl:set var="vat" value="${row.maxPrice * 0.21}" />
		<fmt:formatNumber var="vatv2" maxFractionDigits="2" value="${vat}" />

		<spring:message code="fixUpTask.maxPrice" var="maxPriceHeader" />
		<display:column title="${maxPriceHeader}">
			<jstl:out value="${row.maxPrice} (${vatv2})"></jstl:out>

		</display:column>


		<spring:message code="fixUpTask.startMoment" var="startMomentHeader" />
		<display:column property="startMoment" title="${startMomentHeader}"
			sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>


		<spring:message code="fixUpTask.endMoment" var="endMomentHeader" />
		<display:column property="endMoment" title="${endMomentHeader}"
			sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>

		


		<jstl:set var="fixStart" value="${row.startMoment}" />
		<jsp:useBean id="now" class="java.util.Date" />

		<display:column>
		
			<jstl:set var="containsA" value="${false}" />
			<jstl:forEach items="${fixUpTasks}" var="fix">
				<jstl:forEach items="${collFixUpTasksAccepted}" var="fixauxA">
					<jstl:if test="${row.ticker eq fixauxA.ticker}">
						<jstl:set var="containsA" value="${true}" />
					</jstl:if>
				</jstl:forEach>
			</jstl:forEach>
			
			<jstl:set var="containsB" value="${false}" />
			<jstl:forEach items="${fixUpTasks}" var="fix">
				<jstl:forEach items="${collFixUpTasksBanned}" var="fixauxB">
					<jstl:if test="${row.ticker eq fixauxB.ticker}">
						<jstl:set var="containsB" value="${true}" />
					</jstl:if>
				</jstl:forEach>
			</jstl:forEach>
			
			<jstl:if test="${fixStart > now and containsA == false and containsB == false}">
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


		<jstl:set var="vat" value="${row.maxPrice * 0.21}" />
		<fmt:formatNumber var="vatv2" maxFractionDigits="2" value="${vat}" />

		<spring:message code="fixUpTask.maxPrice" var="maxPriceHeader" />
		<display:column title="${maxPriceHeader}">
			<jstl:out value="${row.maxPrice} (${vatv2})"></jstl:out>

		</display:column>

		<spring:message code="fixUpTask.startMoment" var="startMomentHeader" />
		<display:column property="startMoment" title="${startMomentHeader}"
			sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>


		<spring:message code="fixUpTask.endMoment" var="endMomentHeader" />
		<display:column property="endMoment" title="${endMomentHeader}"
			sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>

		<display:column>
			<a href="fixUpTask/customer/display.do?fixUpTaskId=${row.id}"> <spring:message
					code="fixUpTask.display" /></a>
		</display:column>

		<display:column>
			<jstl:if test="${not empty row.applications}">
				<a
					href="application/customer,handy-worker/list.do?fixUpTaskId=${row.id}">
					<spring:message code="fixUpTask.applications" />
				</a>
			</jstl:if>
		</display:column>


	</display:table>


</security:authorize>


