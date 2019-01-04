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






<h3>
	<spring:message code="category.name" />
	:
	<jstl:out value="${currentCategory.name}" />
</h3>


<div>
	<h3>
		<spring:message code="category.children" />
		:
	</h3>


	<jstl:set var="subcategories"
		value="${currentCategory.childCategories}" />

	<jstl:if test="${not empty subcategories}">

		<uL>
			<jstl:forEach items="${subcategories}" var="subcategory">

				<li><a href="category/list.do?categoryId=${subcategory.id}">
						<jstl:out value="${subcategory.name}" />
				</a></li>

			</jstl:forEach>

		</uL>

	</jstl:if>

	<jstl:if test="${empty subcategories}">
		<h4>
			<spring:message code="category.noCategories" />
			<jstl:out value="${currentCategory.name}" />
		</h4>
	</jstl:if>

</div>

<div>
	<h3>
		<spring:message code="category.trips" />
		:
	</h3>



	<jstl:if test="${not empty tripsInCategory}">

		<display:table pagesize="10" class="displaytag" name="tripsInCategory"
			requestURI="category/list.do" id="trip">

			<spring:message code="category.ticker" var="ticker" />
			<display:column property="ticker" title="${ticker }" sortable="true" />

			<spring:message code="category.title" var="title" />
			<display:column property="title" title="${title }" sortable="true" />


			<spring:message code="category.price" var="price" />
			<display:column property="price" title="${price }" sortable="true" />

			<spring:message code="category.startDate" var="startDate" />
			<display:column property="startDate" title="${startDate }"
				sortable="true" format="{0,date,dd/MM/yyyy}" />

			<spring:message code="category.endDate" var="endDate" />
			<display:column property="endDate" title="${endDate }"
				sortable="true" format="{0,date,dd/MM/yyyy}" />

			

			<spring:message code="category.stages" var="stages" />
			<display:column title="${stages }">
				<ul>
					<jstl:forEach items="${trip.stage }" var="stage">
						<li><jstl:out value="${stage.title }"></jstl:out></li>
					</jstl:forEach>
				</ul>
			</display:column>



			<spring:message code="category.tags" var="tags" />
			<display:column title="${tags }">
				<jstl:choose>
					<jstl:when test="${not empty trip.tags}">

						<ul>
							<jstl:forEach items="${trip.tags}" var="tag">

								<li><jstl:out value="${tag.name}">
									</jstl:out></li>


							</jstl:forEach>
						</ul>

					</jstl:when>
					<jstl:otherwise>

						<spring:message code="category.tags.empty" />

					</jstl:otherwise>

				</jstl:choose>
				
			</display:column>


			<display:column>
				<a href="trip/display.do?tripId=${trip.id }"><spring:message
						code="category.displayTrip" /></a>
			</display:column>
		</display:table>


	</jstl:if>

	<jstl:if test="${empty tripsInCategory}">
		<h4>
			<spring:message code="category.noTrips" />
			<jstl:out value="${currentCategory.name}" />
		</h4>
	</jstl:if>

</div>



