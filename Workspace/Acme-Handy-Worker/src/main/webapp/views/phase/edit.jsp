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

<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:if test="${editable}">

		<form:form action="phase/handy-worker/edit.do" modelAttribute="phase"
			methodParam="post">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="fixUpTask" />

			<form:label path="title">
				<spring:message code="phase.title" />
			</form:label>
			<form:input path="title" />
			<form:errors cssClass="error" path="title" />
			<br>

			<form:label path="description">
				<spring:message code="phase.description" />
			</form:label>
			<form:input path="description" />
			<form:errors cssClass="error" path="description" />
			<br>

			<form:label path="startMoment">
				<spring:message code="phase.startmoment" />
			</form:label>
			<form:input path="startMoment" placeholder="dd/MM/yyyy hh:mm" />
			<form:errors cssClass="error" path="startMoment" />
			<br>

			<form:label path="endMoment">
				<spring:message code="phase.endmoment" />
			</form:label>
			<form:input path="endMoment" placeholder="dd/MM/yyyy hh:mm" />
			<form:errors cssClass="error" path="endMoment" />
			<br>

			<input type="submit" name="save" id="save"
				value='<spring:message code="phase.save"/>' />
		</form:form>

	</jstl:if>
</security:authorize>
