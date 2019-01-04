<%--
 * 
 *
 * Copyright (C) 2017 Universidad de Sevilla
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


<jstl:choose>
<jstl:when test="${permission}">




<form:form action="emergencyContact/explorer/edit.do" modelAttribute="emergencyContact" id="form">

	<form:hidden path="id" />
	
	<form:hidden path="version" />
	
	


	<form:label path="name">
		<spring:message code="emergencyContact.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	<br />
	
		<form:label path="email">
		<spring:message code="emergencyContact.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	<br />
	
		<form:label path="phone">
		<spring:message code="emergencyContact.phone" />:
	</form:label>
	<spring:message code="emergencyContact.phonePlaceholder" var="placeholder" />
	<form:input path="phone" id="phone" placeholder="${placeholder}" size="30"/>
	<form:errors cssClass="error" path="phone" />
	<br />
	<br />

	

	<spring:message code="emergencyContact.save" var="saveContact"  />

	<spring:message code="emergencyContact.delete" var="deleteContact"  />
	<spring:message code="emergencyContact.confirm.delete" var="confirmDeleteContact"  />
	<spring:message code="emergencyContact.cancel" var="cancelContact"  />
	
	<input type="button" id="save" name="save"
		value="${saveContact}" />&nbsp; 
		
		
	<jstl:if test="${emergencyContact.id != 0}">
		<input type="submit" name="delete" value="${deleteContact}"
			onclick="return confirm('${confirmDeleteContact}')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="${cancelContact}"
		onclick="javascript: relativeRedir('emergencyContact/explorer/list.do');" />
	<br />



</form:form>


</jstl:when>
<jstl:otherwise>

<div><spring:message code="emergencyContact.unauthorizedAccess.message"/> </div>

</jstl:otherwise>

</jstl:choose>

<script type="text/javascript">
$("#save").on("click",function(){validatePhone("<spring:message code='emergencyContact.confirmationPhone'/>","${countryCode}");});
 
</script>



