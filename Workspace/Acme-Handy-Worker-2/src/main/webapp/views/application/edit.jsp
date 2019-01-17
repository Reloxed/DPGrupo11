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
			<td><strong> <spring:message
						code="application.registeredMoment" /> :
			</strong></td>
			<td><jstl:out value="${application.registeredMoment}">
				</jstl:out></td>
		</tr>

		<tr>
			<td><strong> <spring:message code="application.status" />
					:
			</strong></td>
			<td><jstl:out value="${application.status}">
				</jstl:out></td>
		</tr>


		<tr>
			<td><strong> <spring:message code="application.offeredPrice" />
					:
			</strong></td>

			<jstl:set var="vat" value="${application.offeredPrice * 0.21}" />
			<fmt:formatNumber var="vatv2" maxFractionDigits="2" value="${vat}" />

			<td><jstl:out value="${application.offeredPrice} (${vatv2})"></jstl:out>
			</td>
		</tr>


		<tr>
			<td><strong> <spring:message
						code="application.customerComment" /> :
			</strong></td>
			<td>

			<jstl:out value="${application.customerComment}">
			</jstl:out>
			</td>
		</tr>


		<tr>
			<td><strong> <spring:message
						code="application.handyWorkerComment" /> :
			</strong></td>
			<td><jstl:out value="${application.handyWorkerComment}">
				</jstl:out></td>
		</tr>

		<tr>
			<td><strong> <spring:message code="application.fixuptask" />
					:
			</strong></td>
			<td><jstl:out value="${application.fixUpTask.description}">
				</jstl:out></td>
		</tr>

		<tr>
			<td><strong> <spring:message code="application.applicant" />
					:
			</strong></td>
			<td><jstl:out value="${application.applicant.name}">
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
			<td><a href="actor/display.do?actorID=${application.applicant.id}"> <spring:message
						code="profile.handyWorker" />
			</a></td>
		</tr>
		<tr>
			<td><strong> <spring:message
						code="application.registeredMoment" /> :
			</strong></td>
			<td><jstl:out value="${application.registeredMoment}">
				</jstl:out></td>
		</tr>

		<tr>
			<td><strong> <spring:message code="application.status" />
					:
			</strong></td>
			<td><jstl:out value="${application.status}">
				</jstl:out></td>
		</tr>


		<tr>
			<td><strong> <spring:message code="application.offeredPrice" />
					:
			</strong></td>

			<jstl:set var="vat" value="${application.offeredPrice * 0.21}" />
			<fmt:formatNumber var="vatv2" maxFractionDigits="2" value="${vat}" />

			<td><jstl:out value="${application.offeredPrice} (${vatv2})"></jstl:out>
			</td>
		</tr>


		<tr>
			<td><strong> <spring:message
						code="application.customerComment" /> :
			</strong></td>
			<td>

			<jstl:out value="${application.customerComment}">
			</jstl:out>
			</td>
		</tr>


		<tr>
			<td><strong> <spring:message
						code="application.handyWorkerComment" /> :
			</strong></td>
			<td><jstl:out value="${application.handyWorkerComment}">
				</jstl:out></td>
		</tr>

		<tr>
			<td><strong> <spring:message code="application.fixuptask" />
					:
			</strong></td>
			<td><jstl:out value="${application.fixUpTask.description}">
				</jstl:out></td>
		</tr>

		<tr>
			<td><strong> <spring:message code="application.applicant" />
					:
			</strong></td>
			<td><jstl:out value="${application.applicant.name}">
				</jstl:out></td>
		</tr>

	</table>
	
		<input type="button" name="back"
		value="<spring:message code="fixuptask.back" />"
		onclick="window.history.back()" />


</security:authorize>