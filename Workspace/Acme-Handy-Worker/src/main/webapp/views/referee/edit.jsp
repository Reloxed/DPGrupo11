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

<form:form action="referee/referee/edit.do" modelAttribute="referee"
	methodParam="post">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:hidden path="socialProfiles" />
	<form:hidden path="userAccount" />
	<form:hidden path="messageBoxes" />
	<form:hidden path="isSuspicious" />

	<form:hidden path="complaints" />

	<form:label path="surname">
		<spring:message code="actor.surname" />:
		</form:label>
	<form:input path="surname" value="${referee.surname}" />
	<form:errors cssClass="error" path="surname" />
	<br>

	<form:label path="name">
		<spring:message code="actor.name" />:
		</form:label>
	<form:input path="name" value="${referee.name}" />
	<form:errors cssClass="error" path="name" />
	<br>

	<form:label path="middlename">
		<spring:message code="actor.middlename" />:
		</form:label>
	<form:input path="middlename" value="${referee.middlename}" />
	<form:errors cssClass="error" path="middlename" />
	<br>

	<form:label path="email">
		<spring:message code="actor.email" />:
		</form:label>
	<form:input path="email" value="${referee.email}" />
	<form:errors cssClass="error" path="email" />
	<br>

	<form:label path="photo">
		<spring:message code="actor.photo" />:
		</form:label>
	<form:input path="photo" value="${referee.photo}" />
	<form:errors cssClass="error" path="photo" />
	<br>

	<form:label path="phoneNumber">
		<spring:message code="actor.phone" />:
		</form:label>
	<form:input path="phoneNumber" value="${referee.phoneNumber}" />
	<form:errors cssClass="error" path="phoneNumber" />
	<br>

	<form:label path="address">
		<spring:message code="actor.address" />:
		</form:label>
	<form:input path="address" value="${referee.address}" />
	<form:errors cssClass="error" path="address" />
	<br>

	<input type="submit" name="save" id="save"
		value='<spring:message code="actor.save"/>' />

</form:form>
