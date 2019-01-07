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


<jstl:choose>
<jstl:when test="${warranty.isFinal == true}">
<h3>	<spring:message code="warranty.nopermission"/>	</h3>
</jstl:when>
<jstl:otherwise>

<form:form action="warranty/administrator/edit.do"
	modelAttribute="warranty" id="warrantyForm">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="id"/>
	
	<form:label path="title">
		<spring:message code="warranty.title"/>
	</form:label>	
	<form:input path="title"/>
	<form:errors cssClass="error" path="title"/>
	<br/>
	<br/>
	
	<form:label path="terms">
		<spring:message code="warranty.terms"/>
	</form:label>	
	<form:input path="terms"/>
	<form:errors cssClass="error" path="terms"/>
	<br/>
	<br/>
	
	<form:label path="laws">
		<spring:message code="warranty.laws"/>
	</form:label>	
	<form:textarea path="laws"/>
	<form:errors cssClass="error" path="laws"/>
	<br/>
	<br/>
	
	<spring:message code="warranty.save.final" var="saveWarrantyFinal"/>
	<spring:message code="warranty.delete" var="deleteWarranty"/>
	<spring:message code="warranty.confirm.delete" var="confirmDeleteWarranty"/>
	<spring:message code="warranty.cancel" var="cancelWarranty"/>
	
	<input	type="submit" id="submit" name="saveFinal"
	value="${saveWarrantyFinal}"	/>&nbsp;
	
	<jstl:if test="${warranty.id != 0}">
		<input	type="submit" name="delete" value="${deleteWarranty}"
		onclick="return confirm('${confirmDeleteWarranty}')"	/>&nbsp;
	</jstl:if>
	<input	type="button" name="cancel"	
	value="${cancelWarranty}"
	onclick="javascript: relativeRedir('warranty/administrator/list.do');"/>
	<br/>
	
</form:form>

</jstl:otherwise>
</jstl:choose>
