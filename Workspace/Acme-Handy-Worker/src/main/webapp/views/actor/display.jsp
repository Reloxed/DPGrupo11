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

<security:authorize access="isAuthenticated()" var="authenticated" />

<jstl:if test="${authenticated}">
	<security:authentication property="principal.username" var="user" />
</jstl:if>

<jstl:choose>
	<jstl:when test="${not empty sponsor}">
		<jstl:set var="type" value="sponsor" />
	</jstl:when>

	<jstl:when test="${not empty handyWorker}">
		<jstl:set var="type" value="handyWorker" />
	</jstl:when>

	<jstl:when test="${not empty customer}">
		<jstl:set var="type" value="customer" />
	</jstl:when>

	<jstl:when test="${not empty referee}">
		<jstl:set var="type" value="referee" />
	</jstl:when>

	<jstl:when test="${not empty administrator}">
		<jstl:set var="type" value="administrator" />
	</jstl:when>
</jstl:choose>

<h3>
	<spring:message code="actor.userAccount.username" />
	<jstl:out value="${actor.userAccount.username}" />
</h3>

<div
	style="float: left; padding: 10px; height: 150px; width: 150px; overflow: auto;">
	<img height="100%" width="100%" alt="userPhoto" src="${actor.photo}">
	<h3>
		<jstl:out value="${actor.name}" />
		<jstl:out value="${actor.middleName}" />
		<jstl:out value="${actor.surname}" />
	</h3>
</div>

<h5>
	<strong><spring:message code="actor.email" />:</strong>
	<jstl:out value="${actor.email}"></jstl:out>
</h5>

<h5>
	<strong><spring:message code="actor.phone" />:</strong>
	<jstl:out value="${actor.phoneNumber}"></jstl:out>
</h5>

<h5>
	<strong><spring:message code="actor.address" />:</strong>
	<jstl:out value="${actor.address}"></jstl:out>
</h5>

<jstl:if test="${type == 'customer' || type == 'handyWorker'}">
	<h5>
		<strong><spring:message code="actor.score" /></strong>
	</h5>
	<h5>
		<jstl:out value="${handyWorker.score}" />
		<jstl:out value="${customer.score}" />
	</h5>
</jstl:if>

<jstl:if test="${type == 'handyWorker'}">
	<h5>
		<strong><spring:message code="handyWorker.make" />:</strong>
		<jstl:out value="${handyWorker.make}"></jstl:out>
	</h5>

	<input type="button" name="cv"
		value="<spring:message code="handyWorker.cv" />"
		onclick="redirect: location.href = 'curriculum/handyWorker/display.do?curriculumID=${handyWorker.curriculum.id}';" />

</jstl:if>

<jstl:if test="${user == actor.userAccount.username}">
	<input type="button" name="editActor"
		value="<spring:message code="actor.edit" />"
		onclick="redirect: location.href = '${type}/${type}/edit.do?${type}ID=${actor.id}';" />
</jstl:if>
