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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h2 style="font-family: sans-serif;">
	<spring:message code="applications.list" />
</h2>

<security:authorize access="hasRole('CUSTOMER')">
	<security:authentication property="principal.username" var="user" />

	<jstl:if test="${user == owner.userAccount.username}">

		<display:table name="applications" id="applications"
			requestURI="application/customer/list.do" pagesize="10"
			class="displaytag">

			<display:column property="applicant.name"
				titleKey="application.applicant" />

			<display:column property="registeredMoment"
				titleKey="application.registeredMoment" sortable="true"
				format="{0,date,dd/MM/yyyy HH:mm}" />

			<display:column property="offeredPrice"
				titleKey="application.offeredPrice" sortable="true" />

			<display:column property="comments" titleKey="application.comments" />

			<display:column property="status" titleKey="application.status" />


			<display:column titleKey="application.buttons">
				<jstl:choose>
					<jstl:when test="${hasAccepted}">
						<spring:message code="application.noaction" />
					</jstl:when>
					<jstl:when
						test="${applications.status == 'PENDING' && hasAccepted == false}">
						<a
							onclick="redirect: location.href = 'application/customer/accept.do?applicationID=${applications.id}';">
							<img src="images/confirm.png">
						</a>

						<a
							onclick="redirect: location.href = 'application/customer/deny.do?applicationID=${applications.id}';">
							<img src="images/delete.png">
						</a>
					</jstl:when>
				</jstl:choose>
			</display:column>


		</display:table>

	</jstl:if>
</security:authorize>