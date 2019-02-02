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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${observation.id==0}">

	<form:form action="observation/customer/edit.do"
		modelAttribute="observation">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="publishedMoment" />
		<form:hidden path="ticker" />
		<form:hidden path="fixUpTask" />

		<form:label path="body">
			<spring:message code="observation.body" /> : 
		</form:label>
		<form:input path="body" />
		<form:errors cssClass="error" path="body" />
		<br />
		<br />

		<form:label path="picture">
			<spring:message code="observation.picture" /> : 
		</form:label>
		<form:input path="picture" />
		<form:errors cssClass="error" path="picture" />
		<br />
		<br />


		<input type="submit" name="saveDraft"
			value="<spring:message code="observation.saveDraft"/>" />&nbsp;
			
			
			<input type="submit" name="saveFinal"
			value="<spring:message code="observation.saveFinal"/>" />&nbsp;
		
		<input type="button" name="cancel"
			value="<spring:message code="observation.cancel"/>"
			onclick="javascript: relativeRedir('/fixUpTask/customer/list.do');" />
		<br />

	</form:form>
</jstl:if>

<jstl:if test="${observation.id != 0}">
	<form:form action="observation/customer/edit.do"
		modelAttribute="observation">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="publishedMoment" />
		<form:hidden path="isFinal" />
		<form:hidden path="fixUpTask" />
		<form:hidden path="ticker" />

		<form:label path="body">
			<spring:message code="observation.body" />
		</form:label>
		<form:input path="body"/>
		<form:errors cssClass="error" path="body"/>
		<br />
		<br />

		<form:label path="picture">
			<spring:message code="observation.picture" />
		</form:label>
		<form:input path="picture" />
		<form:errors cssClass="error" path="picture"/>
		<br />
		<br />
		
		
		<input type="submit" name="saveFinal"
		value="<spring:message code="observation.saveFinal"/>" />

	<input type="submit" name="delete"
		value="<spring:message code="observation.delete"/>"
		onclick="return confirm('<spring:message code="observation.confirm.delete"/>') " />&nbsp;
				
				<input type="button" name="cancel"
		value="<spring:message code="observation.cancel"/>"
		onclick="javascript: relativeRedir('/fixUpTask/customer/list.do');" />
	<br />
	
	</form:form>

	
	
</jstl:if>
