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


<security:authorize access="hasRole('HANDYWORKER')">


	<tr>
		<td><strong> <spring:message code="fixUpTask.customer"
					var="showCustomer" /> :
		</strong></td>
		<td><jstl:out value="${fixUpTask.customer.userAccount.username}" />
			&nbsp; (<a href="actor/display.do?actorId=${fixUpTask.customer.id}">
				${showCustomer} </a>)</td>
	</tr>

	<spring:message code="trip.manager.display" var="showManager" />

	<tr>
		<td><strong> <spring:message
					code="fixUpTask.description" /> :
		</strong></td>
		<td><jstl:out value="${fixUpTask.description}">
			</jstl:out></td>
	</tr>

	<tr>
		<td><strong> <spring:message
					code="fixUpTask.description" /> :
		</strong></td>
		<td><jstl:out value="${fixUpTask.description}">
			</jstl:out></td>
	</tr>

	<tr>
		<td><strong> <spring:message code="fixUpTask.address" />
				:
		</strong></td>
		<td><jstl:out value="${fixUpTask.address}">
			</jstl:out></td>
	</tr>


	<tr>
		<td><strong> <spring:message code="fixUpTask.maxPrice" />
				:
		</strong></td>
		<td><jstl:out value="${fixUpTask.maxPrice}">
			</jstl:out></td>
	</tr>


	<tr>
		<td><strong> <spring:message
					code="fixUpTask.publishedMoment" /> :
		</strong></td>
		<td><jstl:out value="${fixUpTask.publishedMoment}">
			</jstl:out></td>
	</tr>


	<tr>
		<td><strong> <spring:message
					code="fixUpTask.startMoment" /> :
		</strong></td>
		<td><jstl:out value="${fixUpTask.startMoment}">
			</jstl:out></td>
	</tr>

	<tr>
		<td><strong> <spring:message code="fixUpTask.endMoment" />
				:
		</strong></td>
		<td><jstl:out value="${fixUpTask.endMoment}">
			</jstl:out></td>
	</tr>


	<tr>
		<td><strong> <spring:message code="fixUpTask.category" />
				:
		</strong></td>
		<td><jstl:out value="${fixUpTask.category.name}">
			</jstl:out></td>
	</tr>

	<tr>
		<td><strong> <spring:message code="fixUpTask.warranty" />
				:
		</strong></td>
		<td><jstl:out value="${fixUpTask.warranty.title}">
			</jstl:out></td>
	</tr>


	<tr>
		<td><strong> <spring:message code="fixUpTask.ticker" />
				:
		</strong></td>
		<td><jstl:out value="${fixUpTask.ticker}">
			</jstl:out></td>
	</tr>



</security:authorize>