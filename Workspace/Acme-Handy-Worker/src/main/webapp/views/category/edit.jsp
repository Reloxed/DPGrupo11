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
<link href="styles/misc.css" rel="stylesheet" type="text/css"/>

<security:authorize access="hasRole('ADMINISTRATOR')">

	<form:form action="category/administrator/edit.do"
		modelAttribute="category" id="categoryForm">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="childCategories" />


<div class="tooltip"><spring:message code="category.name" />
  <span class="tooltiptext"><spring:message code="category.info" /></span>
	</div>
		<form:label path="name" value="${category.name.get('Español')}">
			
		</form:label>
		<form:input path="name" value="${category.name}" style="width:400px;" />
		<form:errors cssClass="error" path="name"
			value="${category.name.get('Español')}" />
			
		<br />
		<br />

		<jstl:choose>
			<jstl:when test="${category.id==0}">


				<spring:message code="category.parentCategory" />
				<form:select path="parentCategory" style="width:400px;">
					<form:options items="${parents}" itemLabel="name" itemValue="id" />
				</form:select>

			</jstl:when>
			<jstl:otherwise>
				<form:hidden path="parentCategory" />
			</jstl:otherwise>

		</jstl:choose>


		<input type="submit" name="save" id="save"
			value='<spring:message code="category.save"/>' />
		<jstl:if test="${category.id!=0}">
		<input type="submit" name="delete" id="delete"
			value='<spring:message code="category.delete"/>' />
		</jstl:if>
	</form:form>
	<br />
	<br />




</security:authorize>