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

				<!-- FORMULARIO -->


<form:form action="curriculum/handyWorker/edit.do" modelAttribute="curriculum" id="form">

	<form:hidden path="id" />
	
	<form:hidden path="version" />
	
	<form:hidden path ="ticker" />
	
	<form:hidden path ="professionalRecords" />
	
	<form:hidden path ="educationRecords" />
	
	<form:hidden path ="endorserRecords" />
	
	<form:hidden path ="miscellaneousRecords" />
	
	<%--PERSONAL RECORD --%>
	<fieldset>
	<legend><form:label path="personalRecord"> <spring:message code="curriculum.personalRecord" />: </form:label></legend>
	
	<form:label path="personalRecord.fullName">
		<spring:message code="curriculum.personalRecord.fullName" />:
	</form:label>
	<form:input path="personalRecord.fullName"  />
	<form:errors cssClass="error" path="personalRecord.fullName" />
	<br />
	<br />
	
	<form:label path="personalRecord.photo">
		<spring:message code="curriculum.personalRecord.photo" />:
	</form:label>
	<spring:message code="curriculum.personalRecord.photo" var="photo"/>
	<form:input path="personalRecord.photo" placeholder="${photo }" size="25" />
	<form:errors cssClass="error" path="personalRecord.photo" />
	<br />
	<br />
	
	<form:label path="personalRecord.email">
		<spring:message code="curriculum.personalRecord.email" />:
	</form:label>
	<form:input path="personalRecord.email"  />
	<form:errors cssClass="error" path="personalRecord.email" />
	<br />
	<br />
	
	<spring:message code="curriculum.personalRecord.phoneNumber" var="phoneNumber" />
	<form:label path="personalRecord.phoneNumber">
		<spring:message code="curriculum.personalRecord.phoneNumber" />:
	</form:label>
	<form:input path="personalRecord.phoneNumber"  id="phoneNumber" placeholder="${phoneNumber }" size="30"/>
	<form:errors cssClass="error" path="personalRecord.phoneNumber" />
	<br />
	<br />
	
	<form:label path="personalRecord.linkedinLink">
		<spring:message code="curriculum.personalRecord.linkedinLink" />:
	</form:label>
	<form:input path="personalRecord.linkedinLink"  />
	<form:errors cssClass="error" path="personalRecord.linkedinLink" />
	<br />
	<br />
	</fieldset>
	<br>
	<br>
	
	
	
	
	
	
	
	
	<input type="button" name="save" id="save" value="<spring:message code="curriculum.save" />" />&nbsp; 

	<jstl:if test="${curriculum.id!=0}">
	<input type="button" name="cancel" value="<spring:message code="curriculum.cancel" />"
		onclick="javascript: relativeRedir('/curriculum/handyWorker/display.do?curriculumID=${curriculum.id}');" />
	<br />
	</jstl:if>
	<jstl:if test="${curriculum.id==0}">
	<input type="button" name="cancel" value="<spring:message code="curriculum.cancel" />"
		onclick="javascript: relativeRedir('/welcome/index.do');" />
	<br />
	</jstl:if>
	</form:form>
	
</security:authorize>