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

<display:table name="warranties" id="row" requestURI="$(requestURI)"
pagesize="5" class="displaytag">
	
	<display:column property="title" titleKey="warranty.title"/>
	<display:column property="terms" titleKey="warranty.terms"/>
	<display:column property="laws" titleKey="warranty.laws"/>
	<display:column property="is Final" titleKey="warranty.isFinal"/>
	
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="warranty/administrator/edit.do?warrantyId=${warranty.id}">
				<spring:message code="warranty.edit"/>
			</a> 
		</display:column>
	</security:authorize>
</display:table>