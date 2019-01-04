<%--
 * 
 *
 * Copyright (C) 2017 Universidad de Sevilla
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


<!-- Listing grid -->


<display:table name="categories" id="row"
	requestURI="category/administrator/list.do" pagesize="5"
	class="displaytag">


	<!-- Action links -->

	<display:column>
	 <jstl:if test="${row.name != 'CATEGORY'}">
				<a href="category/administrator/edit.do?categoryId=${row.id}"> <spring:message
						code="category.edit" />
				</a>
		</jstl:if>
	</display:column>



	<!-- Attributes -->

	<spring:message code="category.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />


	<spring:message code="category.parents" var="parentsHeader" />

	<display:column title="${parentsHeader}">

		<jstl:choose>
			<jstl:when test="${not empty row.parentCategories}">

				<ul>
					<jstl:forEach items="${row.parentCategories}" var="parent">

						<li><jstl:out value="${parent.name}">
							</jstl:out></li>


					</jstl:forEach>
				</ul>

			</jstl:when>
			<jstl:otherwise>

				<spring:message code="category.parents.empty" />

			</jstl:otherwise>

		</jstl:choose>




	</display:column>

	<spring:message code="category.children" var="childrenHeader" />

	<display:column title="${childrenHeader}">

		<jstl:choose>
			<jstl:when test="${not empty row.childCategories}">

				<ul>
					<jstl:forEach items="${row.childCategories}" var="child">

						<li><jstl:out value="${child.name}">
							</jstl:out></li>


					</jstl:forEach>
				</ul>

			</jstl:when>
			<jstl:otherwise>

				<spring:message code="category.children.empty" />

			</jstl:otherwise>

		</jstl:choose>

	</display:column>


</display:table>


<div>
	<a href="category/administrator/create.do"> <spring:message
			code="category.create" />
	</a>
</div>




