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
<table class="displayStyle">

	<jstl:if test="${language==español}">
	<tr>
		<td> <spring:message
					code="category.name" /> :
		</td>
		<td>
			<jstl:out value="${category.name.get('Español')}" >
			</jstl:out>
		</td>
	</tr>
		<tr>
		<td> <spring:message
					code="category.parentCategory" /> :
		</td>
		<td>
			<jstl:out value="${category.parentCategory.name.get('Español')}" >
			</jstl:out>
		</td>
	</tr>
	
	<tr>
	<td>
	<spring:message
					code="category.childCategories" /> :
					</td>
					<td>
	<jstl:forEach items="${category.childCategories}" var="entry">
      <jstl:out  value="${entry.name.get('Español')}"/></jstl:forEach> 
      </td>
	
	</tr>
	</jstl:if>
	
	<jstl:if test="${language==english}">
		<tr>
		<td> <spring:message
					code="category.name" /> :
		</td>
		<td>
			<jstl:out value="${category.name.get('English')}" >
			</jstl:out>
		</td>
	</tr>
		<tr>
		<td> <spring:message
					code="category.parentCategory" /> :
		</td>
		<td>
			<jstl:out value="${category.parentCategory.name.get('English')}" >
			</jstl:out>
		</td>
	</tr>
	
	<tr>
	<td>
	<spring:message
					code="category.childCategories" /> :
					</td>
					<td>
	<jstl:forEach items="${category.childCategories}" var="entry">
      <jstl:out  value="${entry.name.get('English')}"/></jstl:forEach> 
      </td>
	
	</tr>
	
	</jstl:if>
	</table>
	
	
	
	

</security:authorize>
