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

<div style="text-align: center;">
	<h2 style="font-family: sans-serif;">
		<spring:message code="application.edit" />
	</h2>
</div>

<form:form action="application/edit.do" modelAttribute="application"
	id="form">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="registeredMoment" />
	<form:hidden path="fixuptask" />
	<form:hidden path="applicant" />

	<form:label path="status">
		<spring:message code="application.status" />
	</form:label>
	<form:select path="status">
		<form:option value="PENDING">
			<spring:message code="tutorial.status.pending" />
		</form:option>
		<form:option value="PENDING">
			<spring:message code="tutorial.status.rejected" />
		</form:option>
		<form:option value="PENDING">
			<spring:message code="tutorial.status.accepted" />
		</form:option>
	</form:select>

	<br />

	<form:label path="offeredPrice">
		<spring:message code="application.offeredPrice" />
	</form:label>
	<spring:message code="application.offeredPricePlaceholder"
		var="placeholder" />
	<form:input path="offeredPrice" placeholder="${placeholder}" />

	<br />

	<form:label path="comments">
		<spring:message code="application.comments" />
	</form:label>
	<spring:message code="application.commentsPlaceholder"
		var="placeholder" />
	<form:input path="comments" placeholder="${placeholder}" />

	<br />

	<form:label path="creditCard">
		<spring:message code="application.creditCard" />
	</form:label>
	<jstl:set var="listCreditCard" value="" />
	<form:select path="creditCard">
		<jstl:forEach var="x" items="${listFixUpTask}">
			<form:option value="${x.brandName}">
				<jstl:out value="${x.brandName}" />
			</form:option>
		</jstl:forEach>
	</form:select>

	<br />

</form:form>