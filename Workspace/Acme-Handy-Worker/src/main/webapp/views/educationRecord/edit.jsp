<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form modelAttribute="educationRecord" action="educationRecord/handyWorker/edit.do">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<fieldset>
	<legend> <spring:message code="er.educationRecord"/>: </legend>
	
	<form:label path="diplomaTitle">
		<spring:message code="er.diplomaTitle"/>:
	</form:label>
	<form:input path="diplomaTitle"/>
	<form:errors cssClass="error" path="diplomTitle"/>
	<br />
	<br />
	
	<form:label path="startDate">
		<spring:message code="er.startDate"/>:
	</form:label>
	<form:input path="startDate" placeholder="dd/MM/yyyy"/>
	<form:errors cssClass="error" path="startDate"/>
	<br />
	<br />
	
	<form:label path="endDate">
		<spring:message code="er.endDate"/>
	</form:label>
	<form:input path="endDate" placeholder="dd/MM/yyyy"/>
	<form:errors cssClass="error" path="endDate"/>
	<br/>
	<br/>
	
	<form:label path="institutionName">
		<spring:message code="er.institutionName"/>
	</form:label>
	<form:input path="institutionName"/>
	<form:errors cssClass="error" path="institutionName"/>
	<br/>
	<br/>
	
	<form:label path="linkAttachment">
		<spring:message code="er.linkAttachment"/>
	</form:label>
	<form:input path="linkAttachment"/>
	<form:errors cssClass="error" path="linkAttachment"/>
	<br/>
	<br/>
	
	</fieldset>
</form:form>