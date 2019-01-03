<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('HANDYWORKER')">


<display:table name="endorsement" id="row"
	requestURI="endorsement/handyWorker/list.do" pagesize="5"
	class="displaytag">
	
	<!-- Atributos -->
	
		<spring:message code="endorsement.sender"
	var="senderHeader" />
	<display:column property="sender" title="${senderHeader}"
		sortable="true" />


	<spring:message code="endorsement.publishedMoment"
	var="publishedMomentHeader" />
	<display:column property="publishedMoment" title="${publishedMomentHeader}"
		sortable="true" />

			<spring:message code="endorsement.comments"
	var="commentsHeader" />
	<display:column property="sender" title="${commentsHeader}"
		sortable="true" />
		
			<display:column titleKey="endorsement.buttons">
		<a href="${requestURI}"> <img src="/webapp/images/edit.png"
			style="width: center; height: center"> <spring:message
				code="endorsement.edit" /></a>
		<a href="${requestURI}"> <img src="/webapp/images/delete.png"
			style="width: center; height: center"> <spring:message
				code="endorsement.delete" /></a>
	</display:column>

</display:table>

	<div>
		<a href="endorsement/handyWorker/create.do"> <spring:message
				code="endorsement.create" />
		</a>
	</div>

</security:authorize>