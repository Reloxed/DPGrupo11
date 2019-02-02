<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<style>
table, th, td{

border: 1px solid black;

}
</style>

<security:authorize access="hasAnyRole('CUSTOMER','HANDYWORKER','ADMINISTRATOR')">
<table class="displayStyle">

<tr>
<td>	<strong>	<spring:message code="observation.ticker"/>	</strong>	</td>
<td> <jstl:out value="${observation.ticker}"></jstl:out></td>

</tr>

<tr>
<td>	<strong>	<spring:message code="observation.publishedMoment"/>	</strong>	</td>
<td> <jstl:out value="${observation.publishedMoment}"></jstl:out></td>

</tr>

<tr>
<td>	<strong>	<spring:message code="observation.body"/>	</strong>	</td>
<td> <jstl:out value="${observation.body}"></jstl:out></td>

</tr>

<tr>
<td>	<strong>	<spring:message code="observation.picture"/>	</strong>	</td>
<td> <jstl:out value="${observation.picture}"></jstl:out></td>

</tr>

<tr>
<td>	<strong>	<spring:message code="observation.fix"/>	</strong>	</td>
<td> <jstl:out value="${observation.fixUpTask.id}"></jstl:out></td>

</tr>

</table>

<security:authorize access= "hasRole('CUSTOMER')">

<div>
	<jstl:if test="${observation.isFinal == false}">
	<a href="observation/customer/edit.do?observationId=${observation.id}">
	<spring:message code="observation.edit"/>
	
	</a>
	
	</jstl:if>
</div>

</security:authorize>





</security:authorize>