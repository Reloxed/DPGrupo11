<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<security:authorize access="isAuthenticated()" var="authenticated" />

<jstl:if test="${authenticated}">
	<security:authentication property="principal.username" var="user" />
</jstl:if>

<style>
table,th,td {
	border: 1px solid black;
}
</style>
<table class="displayStyle">

	<tr>
		<td><strong> <spring:message code="tapu.ticker" /></strong></td>
		<td><jstl:out value="${tapu.ticker}"></jstl:out></td>
	</tr>

	<tr>
		<td><strong> <spring:message code="tapu.body" /></strong></td>
		<td><jstl:out value="${tapu.body}"></jstl:out></td>
	</tr>

	<tr>
		<td><strong> <spring:message code="tapu.moment" /> :
		</strong></td>
		<jstl:choose>
			<jstl:when test="${language == espanyol}">
				<td><fmt:formatDate value="${tapu.publishedMoment}"
						pattern="dd-MM-YY HH:mm" /></td>
			</jstl:when>
			<jstl:otherwise>
				<td><fmt:formatDate value="${tapu.publishedMoment}"
						pattern="YY/MM/dd HH:mm" /></td>
			</jstl:otherwise>
		</jstl:choose>
	</tr>

	<tr>
		<td><strong> <spring:message code="tapu.fix" /></strong></td>
		<security:authorize access="hasRole('CUSTOMER')">
			<td><a
				href="fixUpTask/customer/display.do?fixUpTaskId=${tapu.fixUpTask.id}">
					<jstl:out value="${tapu.fixUpTask.ticker}" />
			</a></td>
		</security:authorize>
		<security:authorize access="hasRole('HANDYWORKER')">
			<td><a
				href="fixUpTask/handyWorker/display.do?taskId=${tapu.fixUpTask.id}">
					<jstl:out value="${tapu.fixUpTask.ticker}" />
			</a></td>
		</security:authorize>
	</tr>

</table>

<security:authorize access="hasRole('CUSTOMER')">

	<jstl:if test="${user == author.userAccount.username}">
		<jstl:if test="${!tapu.isFinal}">
			<a href="tapu/edit.do?tapuID=${tapu.id}"> <spring:message
					code="tapu.edit" />
			</a>
		</jstl:if>
	</jstl:if>

</security:authorize>
