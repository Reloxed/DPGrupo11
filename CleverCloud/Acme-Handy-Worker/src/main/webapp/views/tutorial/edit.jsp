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


<form:form action="tutorial/edit.do" modelAttribute="tutorial" id="form">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="lastUpdate" />
	<form:hidden path="sections" />
	<form:hidden path="sponsorships" />

	<form:label path="title">
		<spring:message code="tutorial.title" />
	</form:label>
	<spring:message code="tutorial.titlePlaceholder" var="placeholder" />
	<form:input path="title" placeholder="${placeholder}" />

	<br />

	<form:label path="summary">
		<spring:message code="tutorial.summary" />
	</form:label>
	<spring:message code="tutorial.summaryPlaceholder" var="placeholder" />
	<form:input path="summary" placeholder="${placeholder}" />
	<br />

	<form:label path="pictures">
		<spring:message code="tutorial.pictures" />
	</form:label>
	<spring:message code="tutorial.picturesPlaceholder" var="placeholder" />
	<form:input path="pictures" placeholder="${placeholder}" />
	<br />

	<form:button type="submit" name="save" id="save"
		value="<spring:message code="tutorial.save"/>" />
	<form:button type="reset" name="reset" id="reset"
		value="<spring:message code="tutorial.reset"/>" />
	<form:button type="cancel" name="cancel" id="cancel"
		value="<spring:message code="tutorial.cancel"/>"
		onclick="window.history.back()" />
</form:form>
