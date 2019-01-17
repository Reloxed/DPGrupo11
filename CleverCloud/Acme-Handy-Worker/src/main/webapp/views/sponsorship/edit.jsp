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

<form:form 	action="sponsorship/sponsor/edit.do"	modelAttribute="sponsorship">
	
	<form:hidden	path="id"	/>
	<form:hidden	path="version"	/>
	<form:hidden	path="sponsor"	/>
	<form:hidden	path="creditCard"	/>
	
	<form:label path="banner">
		<spring:message code="ss.banner"/>:
	</form:label>
	<form:input path="banner"/>
	<form:errors	cssClass="error" path="banner"	/>
	<br/>
	<br/>
	
	<form:label path="targetPage">
		<spring:message code="ss.targetPage"/>:
	</form:label>
	<form:input path="targetPage"/>
	<form:errors	cssClass="error" path="targetPage"	/>
	<br/>
	<br/>

	
	<spring:message code="ss.save" var="saveSponsorship"/>
	<spring:message code="ss.delete" var="deleteSponsorship"/>
	<spring:message code="ss.confirm.delete" var="confirmDeleteSponsorship"/>
	<spring:message code="ss.cancel" var="cancelSponsorship"/>	
	
	<input	type="submit" name="save"	
		value="${saveSponsorship}"/>&nbsp;
	<jstl:if test="${sponsorship.id != 0}">
		<input	type="submit" name="delete"
			value="${deleteSponsorship} "
			onclick="return confirm('${confirmDeleteSponsorship}')"/>&nbsp;
	
	
	</jstl:if>
	
	<input	type="button" name="cancel"
		value="${cancelSponsorship}"
		onclick="window.history.back()"	/>
	<br/>
</form:form>