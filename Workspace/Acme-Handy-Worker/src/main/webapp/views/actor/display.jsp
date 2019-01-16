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
		<jstl:set var="type" value="handyworker" />
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
	style="float: left; padding: 10px; height: 150px; width: 150px; padding-bottom: 50px">
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



<jstl:if test="${type == 'customer' || type == 'handyworker'}">
	<h5>
		<strong><spring:message code="actor.score" /></strong>
	</h5>
	<h5>
		<jstl:out value="${handyWorker.score}" />
		<jstl:out value="${customer.score}" />
		<br/>
		<br/>
		<br/>
			<spring:message code="customer.fixs"/>
			<br/>
			<br/>
		
			<jstl:forEach var="fixUpTask" items="${customer.fixUpTasks}">
				
				<ul><li> <jstl:out value="${fixUpTask.description}"> </jstl:out></li></ul>
				<br/>
			</jstl:forEach>

	</h5>
</jstl:if>

<jstl:if test="${not empty socialProfiles}">
	<p>
		<spring:message code="actor.socialprofiles" />
	</p>
	<table class="displayStyle" style="width: 50%">
		<tr>
			<td><display:table pagesize="5" class="displaytag"
					name="socialProfiles"
					requestURI="actor/display.do?actorID=${actor.id}"
					id="socialProfiles">

					<display:column titleKey="actor.socialprofile.network"
						value="${socialProfiles.socialNetwork}" />
					<display:column titleKey="actor.socialprofile.network"
						value="${socialProfiles.nick}" />
					<display:column titleKey="actor.socialprofile.link"
						value="${socialProfiles.link}" />
				</display:table></td>
		</tr>
	</table>
	<jstl:if test="${user == actor.userAccount.username}">
		<input type="button" name="listSocial"
			value="<spring:message code="actor.list.social" />"
			onclick="redirect: location.href = 'social-profile/actor/list.do?actorID=${actor.id}';" />
	</jstl:if>

</jstl:if>

<jstl:if test="${type == 'handyworker'}">
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

<jstl:if test="${user == actor.userAccount.username}">
	<jstl:if test="${type == 'customer' || type == 'sponsor'}">

		<table class="displayStyle">
			<tr>
				<td><display:table pagesize="5" class="displaytag"
						name="creditCards"
						requestURI="actor/display.do?actorID=${actor.id}" id="creditCards">

						<display:column titleKey="actor.creditcard.holdername"
							value="${creditCards.holderName}" sortable="true" />
						<display:column titleKey="actor.creditcard.brand"
							value="${creditCards.brandName}" sortable="true" />
					</display:table></td>
			</tr>
		</table>

		<input type="button" name="addCredCard"
			value="<spring:message code="actor.add.creditcard" />"
			onclick="redirect: location.href = 'creditcard/create.do?';" />
	</jstl:if>
</jstl:if>
