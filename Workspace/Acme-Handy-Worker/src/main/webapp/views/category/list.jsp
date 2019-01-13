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

<security:authorize access="hasRole('ADMINISTRATOR')"> 

	<display:table name="categories" id="row" requestURI="category/administrator/list.do"
		pagesize="10" class="displaytag">
		
		
		<jstl:if test="${language==español}">
			<display:column value="${row.name.get('Español')}" titleKey="category.name"
			sortable="true" >
			 
			</display:column>
			
			<spring:message code="category.parentCategory" var="parentCategoryHeader" />
		
			<display:column value="${row.parentCategory.name.get('Español')}"   titleKey="category.parentCategory"
			sortable="true" />
			
			</jstl:if>
			<jstl:if test="${language==english}">
			
			<display:column value="${row.name.get('English')}" titleKey="category.name"
			sortable="true" >
			 
			</display:column>
			
			<display:column value="${row.parentCategory.name.get('English')}"   titleKey="category.parentCategory"
			sortable="true" />
			
			</jstl:if>
			
			
			<display:column>
			<a href="category/administrator/display.do?categoryId=${row.id}"> <spring:message
					code="category.display" />
			</a>
	</display:column>

		<display:column>
			<a href="category/administrator/edit.do?categoryId=${row.id}"> <spring:message
					code="category.edit" />
			</a>
	</display:column>
	
	

	
	</display:table>

</security:authorize>