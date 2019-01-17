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

<!-- Listing grid -->
<display:table name="warranties" id="row" requestURI="warranty/administrator/list.do"
pagesize="5" class="displaytag">
	
	<!-- Action links -->
	
	<display:column>
		<jstl:if test="${row.isFinal == false}">
			<a href="warranty/administrator/edit.do?warrantyId=${row.id}">
			<spring:message code="warranty.edit"/>
			</a>
		</jstl:if>
	
	</display:column>
	
	<!-- Attributes -->
	<spring:message code="warranty.title" var="titleHeader"/>
	<display:column property="title" title="${titleHeader}"
		sortable="true"/>
	
	<spring:message code="warranty.terms" var="termsHeader"/>
	<display:column property="terms" title="${termsHeader}"
		sortable="true"/>
	
	<display:column>
		<a href="warranty/administrator/display.do?warrantyId=${row.id}">
		<spring:message code="warranty.display"/>
		
		</a>
	</display:column>
</display:table>

<div>
	<a href="warranty/administrator/create.do">
		<spring:message code="warranty.create"/>	
	</a>
	
</div>