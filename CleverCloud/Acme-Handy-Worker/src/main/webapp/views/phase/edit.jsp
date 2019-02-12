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
			<br>

			<form:label path="description">
				<spring:message code="phase.description" />
			</form:label>
			<form:input path="description" />
			<form:errors cssClass="error" path="description" />
			<br>
			<br>


			<form:label path="startMoment">
				<spring:message code="phase.startmoment" />
			</form:label>
			<spring:message code="phase.startMoment.placeholder"
				var="placeholder" />
			<form:input path="startMoment" placeholder="${placeholder}" />
			<form:errors cssClass="error" path="startMoment" />
			
			&#160;
		
			<form:label path="endMoment">
				<spring:message code="phase.endmoment" />
			</form:label>
			<spring:message code="phase.startMoment.placeholder"
				var="placeholder" />
			<form:input path="endMoment" placeholder="${placeholder}" />
			<form:errors cssClass="error" path="endMoment" />

			<br>
			<br>

			<input type="submit" name="save" id="save"
				value='<spring:message code="phase.save"/>' />

			<input type="button" name="cancel" id="cancel"
				onclick="window.history.back()"
				value="<spring:message code="fixuptask.cancel" />" />

		</form:form>

	</jstl:if>
</security:authorize>
