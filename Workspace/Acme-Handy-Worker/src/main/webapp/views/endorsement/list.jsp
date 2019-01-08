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


	<display:table name="endorsements" id="row"
		requestURI="endorsement/handyWorker/list.do" pagesize="10"
		class="displaytag">



		<spring:message code="endorsement.sender" var="senderHeader" />
		<display:column property="sender.name" title="${senderHeader}"
			sortable="true" />


		<spring:message code="endorsement.publishedMoment"
			var="publishedMomentHeader" />
		<display:column property="publishedMoment"
			title="${publishedMomentHeader}" sortable="true" />

		<spring:message code="endorsement.comments" var="commentsHeader" />
		<display:column property="comments" title="${commentsHeader}"
			sortable="true" />

		<spring:message code="endorsement.buttons" />
		<display:column titleKey="endorsement.buttons">
			<a href="endorsement/handyWorker/edit.do?endorsementId=${row.id}"
				style="text-align: center"> <spring:message
					code="endorsement.edit" /></a>
			<a href="${requestURI}" style="text-align: center"> <spring:message
					code="endorsement.delete" /></a>
		</display:column>

	</display:table>


</security:authorize>
<security:authorize access="hasRole('CUSTOMER')">

	<display:table name="endorsements" id="row"
		requestURI="endorsement/customer/list.do" pagesize="10"
		class="displaytag">

		<spring:message code="endorsement.sender" var="senderHeader" />
		<display:column property="sender.name" title="${senderHeader}"
			sortable="true" />


		<spring:message code="endorsement.publishedMoment"
			var="publishedMomentHeader" />
		<display:column property="publishedMoment"
			title="${publishedMomentHeader}" sortable="true" />

		<spring:message code="endorsement.comments" var="commentsHeader" />
		<display:column property="comments" title="${commentsHeader}"
			sortable="true" />

		<spring:message code="endorsement.buttons" />
		<display:column titleKey="endorsement.buttons">
			<a href="endorsement/customer/edit.do?endorsementId=${row.id}"
				style="text-align: center"> <spring:message
					code="endorsement.edit" /></a>
			<a href="${requestURI}" style="text-align: center"> <spring:message
					code="endorsement.delete" /></a>
		</display:column>

	</display:table>

</security:authorize>
