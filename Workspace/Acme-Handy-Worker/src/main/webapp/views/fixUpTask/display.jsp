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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<security:authorize access="hasRole('HANDYWORKER')">

	<table class="displayStyle">
		<tr>
			<td><a href="actor/display.do?actorID=${customerId}"> <spring:message
						code="profile.customer" />
			</a></td>
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

			<jstl:set var="vat" value="${fixUpTask.maxPrice * 0.21}" />
			<fmt:formatNumber var="vatv2" maxFractionDigits="2" value="${vat}" />

			<td><jstl:out value="${fixUpTask.maxPrice} (${vatv2})"></jstl:out>
			</td>
		</tr>


		<tr>
			<td><strong> <spring:message
						code="fixUpTask.publishedMoment" /> :
			</strong></td>
			<td>

			<jstl:out value="${fixUpTask.publishedMoment}">
			</jstl:out>
			</td>
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
			<jstl:if test="${language==español}">

				<td><jstl:forEach items="${fixUpTask.category.name}"
						var="entry" end="0">
						<jstl:out value="${entry.value}" />
					</jstl:forEach></td>

			</jstl:if>
			<jstl:if test="${language==english}">

				<td><jstl:forEach items="${fixUpTask.category.name}"
						var="entry" begin="1" end="1">
						<jstl:out value="${entry.value}" />
					</jstl:forEach></td>

			</jstl:if>


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

	</table>
	
		<input type="button" name="back"
		value="<spring:message code="fixuptask.back" />"
		onclick="window.history.back()" />

</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">

	<table class="displayStyle">
		<tr>

			<td><a href="actor/display.do?actorID=${customerId}"> <spring:message
						code="profile.customer" />
			</a></td>
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

			<jstl:set var="vat" value="${fixUpTask.maxPrice * 0.21}" />
			<fmt:formatNumber var="vatv2" maxFractionDigits="2" value="${vat}" />

			<td><jstl:out value="${fixUpTask.maxPrice} (${vatv2})"></jstl:out>
			</td>
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
			<jstl:if test="${language==español}">

				<td><jstl:forEach items="${fixUpTask.category.name}"
						var="entry" end="0">
						<jstl:out value="${entry.value}" />
					</jstl:forEach></td>

			</jstl:if>
			<jstl:if test="${language==english}">

				<td><jstl:forEach items="${fixUpTask.category.name}"
						var="entry" begin="1" end="1">
						<jstl:out value="${entry.value}" />
					</jstl:forEach></td>

			</jstl:if>


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

	</table>



	<input type="button" name="back"
		value="<spring:message code="fixuptask.back" />"
		onclick="window.history.back()" />

	<br />



</security:authorize>