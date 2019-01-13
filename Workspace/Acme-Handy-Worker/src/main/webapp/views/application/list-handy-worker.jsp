<%--
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<style>
<!--
.tableColorGreen {
	background-color: chartreuse;
}

.tableColorOrange {
	background-color: orange;
}

.tableColorGrey {
	background-color: grey;
}

.tableColorDefault {
	background-color: white;
}
-->
</style>

<h2 style="font-family: sans-serif;">
	<spring:message code="applications.list" />
</h2>

<security:authorize access="hasRole('HANDYWORKER')">
	<security:authentication property="principal.username" var="user" />

	<jstl:if test="${user == owner.userAccount.username}">

		<display:table name="applications" id="applications"
			requestURI="application/handy-worker/list-handy-worker.do"
			pagesize="10" class="displaytag">

			<jstl:catch>
				<fmt:parseDate value="${applications.fixUpTask.startMoment}"
					pattern="yyyy-MM-dd HH:mm" var="fixUpTaskStartMoment" />
			</jstl:catch>
			<jsp:useBean id="now" class="java.util.Date"/>
			<jstl:choose>
				<jstl:when test="${applications.status == 'ACCEPTED'}">
					<jstl:set var="bgcolor" value="tableColorGreen" />
				</jstl:when>

				<jstl:when test="${applications.status == 'REJECTED'}">
					<jstl:set var="bgcolor" value="tableColorOrange" />
				</jstl:when>
				
				<jstl:when
					test="${applications.status == 'PENDING' && application.fixUpTask.startMoment le application.registeredMoment}">
					<jstl:set var="bgcolor" value="tableColorGrey" />
				</jstl:when>

				<jstl:otherwise>
					<jstl:set var="bgcolor" value="tableColorDefault" />
				</jstl:otherwise>
			</jstl:choose>

			<display:column property="fixUpTask.description"
				titleKey="application.fixuptask" />

			<display:column property="registeredMoment"
				titleKey="application.registeredMoment" sortable="true"
				format="{0,date,dd/MM/yyyy HH:mm}" />

			<display:column property="offeredPrice"
				titleKey="application.offeredPrice" sortable="true" />

			<display:column property="handyWorkerComment" titleKey="application.myComment"/>

			<display:column property="customerComment" titleKey="application.customerComment"/>

			<display:column property="status" titleKey="application.status"  sortable="true"
				class="${bgcolor}" />

			<display:column>
				<jstl:if test="${applications.status == 'ACCEPTED'}">
					<a
						href="phase/handy-worker/list.do?fixuptaskID=${applications.fixUpTask.id}"><spring:message
							code="application.workplan" /> </a>
				</jstl:if>
			</display:column>

		</display:table>

	</jstl:if>
</security:authorize>