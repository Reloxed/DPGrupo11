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
<jstl:when test="${category.name == 'CATEGORY'}">
<h3><spring:message code="category.nopermission" /></h3>
</jstl:when>
<jstl:otherwise>





<form:form action="category/administrator/edit.do" modelAttribute="category">

	<form:hidden path="id" />
	
	<form:hidden path="version" />
	
	


	<form:label path="name">
		<spring:message code="category.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	<br />

	
		<form:label path="parentCategories">
		<spring:message code="category.parents" /> :
	</form:label>
	<form:select multiple="true" path="parentCategories">
			
		<form:options items="${allPossibleParents}" itemValue="id" itemLabel="name" />
	</form:select>
	<form:errors cssClass="error" path="parentCategories" />
	<br />
	<br />
	
	
	
	<form:hidden path="childCategories"/>
	
	<form:hidden path="trips"/>
	
	

	<spring:message code="category.save" var="saveCategory"  />
	<spring:message code="category.delete" var="deleteCategory"  />
	<spring:message code="category.confirm.delete" var="confirmDeleteCategory"  />
	<spring:message code="category.cancel" var="cancelCategory"  />
	
	<input type="submit" id="submit" name="save"
		value="${saveCategory}" />&nbsp; 
				
	<jstl:if test="${category.id != 0}">
		<input type="submit" name="delete"
			value="${deleteCategory}"
			onclick="return confirm('${confirmDeleteCategory}')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="${cancelCategory}"
		onclick="javascript: relativeRedir('category/administrator/list.do');" />
	<br />


</form:form>

</jstl:otherwise>
</jstl:choose>





