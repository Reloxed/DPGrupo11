<%--
 * action-2.jsp
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

<form:form action="endorserment/handyWorker/edit.do" modelAttribute="endorsement" id="form">


<div>
<fieldset>

<legend> <form:label path="comments"> <spring:message code="endorsement.comments" />: </form:label> </legend>
	<form:textarea path="comments" /> 
	<br />
	</fieldset>
	<br />
	</div>
	<div>
<fieldset>
	<legend>  <spring:message code="endorsement.recipient" /></legend>
		<form:select path="recipient" style="width:100px;">
			<jstl:forEach var="x" items="${endorsers}">
				<form:option value="${x.name}" />
				<jstl:out value="${x.name}" />
			</jstl:forEach>
		</form:select>

</fieldset>
	<br />
	</div>
		<br />
		<br />
		<br />
		<br />
		
	<spring:message code="endorsement.save" var="saveEndorsement"  />
	<spring:message code="endorsement.cancel" var="cancelEndorsement"  />
	
	<input type="submit" name="save"
		value="${saveEndorsement}" />&nbsp; 
	<input type="button" name="cancel"
		value="${cancelEndorsement}"
		onclick="javascript: relativeRedir('endorsement/handyWorker/list.do');" />
	<br />
		
		
		
		
</form:form>
</security:authorize>