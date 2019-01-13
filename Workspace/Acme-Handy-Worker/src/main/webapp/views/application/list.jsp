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
			requestURI="application/customer,handy-worker/list.do"
			pagesize="10" class="displaytag">

			<jstl:choose>
				<jstl:when test="${applications.status == 'ACCEPTED'}">
					<jstl:set var="bgcolor" value="tableColorGreen" />
				</jstl:when>

				<jstl:when test="${applications.status == 'REJECTED'}">
					<jstl:set var="bgcolor" value="tableColorOrange" />
				</jstl:when>

				<jstl:when
					test="${applications.status == 'PENDING' and date gt applications.fixUpTask.startMoment}">

					<jstl:set var="bgcolor" value="tableColorGrey" />
				</jstl:when>

				<jstl:otherwise>
					<jstl:set var="bgcolor" value="tableColorDefault" />
				</jstl:otherwise>
			</jstl:choose>

			<display:column property="fixUpTask.description"
				titleKey="application.fixuptask" />

			<display:column titleKey="application.fixuptask.goto">
				<a
					href="fixUpTask/handyWorker/display.do?taskId=${applications.fixUpTask.id}"><spring:message
						code="fixuptask.display" /></a>
			</display:column>

			<display:column property="registeredMoment"
				titleKey="application.registeredMoment" sortable="true"
				format="{0,date,dd/MM/yyyy HH:mm}" />

			<display:column property="offeredPrice"
				titleKey="application.offeredPrice" sortable="true" />


			<display:column property="handyWorkerComment"
				titleKey="application.myComments" />

			<display:column property="customerComment"
				titleKey="application.customerComment" />


			<display:column property="status" titleKey="application.status"
				sortable="true" class="${bgcolor}" />

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

<security:authorize access="hasRole('CUSTOMER')">
	<security:authentication property="principal.username" var="user" />

	<jstl:if test="${user == owner.userAccount.username}">

		<display:table name="applications" id="applications"
			requestURI="${requestURI}" pagesize="10" class="displaytag">

			<display:column property="applicant.name"
				titleKey="application.applicant" />

			<display:column property="registeredMoment"
				titleKey="application.registeredMoment" sortable="true"
				format="{0,date,dd/MM/yyyy HH:mm}" />

			<display:column property="offeredPrice"
				titleKey="application.offeredPrice" sortable="true" />

			<display:column property="handyWorkerComment" titleKey="application.handyWorkerComment" />
			
			<display:column property="customerComment" titleKey="application.myComment" />

			<display:column property="status" titleKey="application.status" />


			<display:column titleKey="application.buttons">
				<jstl:choose>
					<jstl:when test="${hasAccepted}">
						<spring:message code="application.noaction" />
					</jstl:when>
					<jstl:when
						test="${applications.status == 'PENDING' && hasAccepted == false}">
						<a
							onclick="redirect: location.href = 'application/customer/acceptv.do?applicationID=${applications.id}';">
							<img src="images/confirm.png">
						</a>

						<a
							onclick="redirect: location.href = 'application/customer/reject.do?applicationID=${applications.id}';">
							<img src="images/delete.png">
						</a>
					</jstl:when>
				</jstl:choose>
			</display:column>


		</display:table>

	</jstl:if>
</security:authorize>