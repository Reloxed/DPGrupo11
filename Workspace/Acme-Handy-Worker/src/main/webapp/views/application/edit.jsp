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

<security:authorize access="hasRole('HANDYWORKER')">

	<div style="text-align: center;">
		<h2 style="font-family: sans-serif;">
			<spring:message code="application.create" />
		</h2>
	</div>
	
	<form:form action="application/handy-worker/edit.do" modelAttribute="application"
		id="form">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<jstl:if test="${application.id == 0 }">
				<form:hidden path="registeredMoment" value="01/01/2001 00:00" />
		</jstl:if>
		<jstl:if test="${application.id != 0 }">
				<form:hidden path="registeredMoment" />
		</jstl:if>
		<form:hidden path="fixUpTask" />
		<form:hidden path="applicant"/>
		<form:hidden path="creditCard"/>
		<form:hidden path="status" />
		<form:hidden path="customerComment"/>
	
		<br />
	
		<form:label path="offeredPrice">
			<spring:message code="application.offeredPrice" />
		</form:label>
		<spring:message code="application.offeredPricePlaceholder"
			var="placeholder" />
		<form:input path="offeredPrice" placeholder="${placeholder}" />
	
		<br /><br />
	
		<form:label path="handyWorkerComment">
			<spring:message code="application.myComment" />
		</form:label>
		<spring:message code="application.handyWorkerCommentsPlaceholder"
			var="placeholder" />
		<form:input path="handyWorkerComment" placeholder="${placeholder}" />
		<form:errors cssClass="error" path="handyWorkerComment"></form:errors>
	
		<br />
	
		<br />
		
		<input type="submit" name="save" id="save"
			value='<spring:message code="application.save"/>' />
		<input type="button" name="cancel"
			value="<spring:message code="application.cancel" />"
			onclick="window.history.back()" />
			
		<br/>
	
	</form:form>

</security:authorize>