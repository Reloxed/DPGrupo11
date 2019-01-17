<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<style>
table,th,td {
	border: 1px solid black;
}
</style>
<table class="displayStyle">

	<tr>
		<td><strong> <spring:message code="note.publishedMoment" /></strong></td>
		<td><fmt:formatDate value="${note.publishedMoment}"
				pattern="dd/MM/yyyy HH:mm" /></td>
	</tr>

	<tr>
		<td><strong> <spring:message
					code="note.handyWorker.comment" /> :
		</strong></td>
		<td><jstl:out value="${note.handyWorkerComment }" /></td>
	</tr>

	<tr>
		<td><strong><spring:message code="note.customer.comment" /></strong></td>
		<td><jstl:out value="${note.customerComment }" /></td>
	</tr>

	<tr>
		<td><strong><spring:message code="note.referee.comment" /></strong></td>
		<td><jstl:out value="${note.refereeComment }" /></td>
	</tr>
</table>

<jstl:if test="${note.report.isFinal == true }">
	<security:authorize access="hasRole('CUSTOMER')">
		<a href="note/customer/edit.do?noteId=${note.id}"><spring:message
				code="note.create" /></a>
	</security:authorize>
	<security:authorize access="hasRole('HANDYWORKER')">
		<a href="note/handyWorker/edit.do?noteId=${note.id}"><spring:message
				code="note.create" /></a>
	</security:authorize>
	<security:authorize access="hasRole('REFEREE')">
		<a href="note/referee/edit.do?noteId=${note.id}"><spring:message
				code="note.create" /></a>
	</security:authorize>
</jstl:if>