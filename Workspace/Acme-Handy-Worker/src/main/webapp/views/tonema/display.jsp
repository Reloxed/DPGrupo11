<%--
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
table, th, td {
  border: 1px solid black;
}
</style>

<img height="150px" width="150px" alt="picture" src="${tonema.picture}">

<table class="displayStyle">

<tr>
<td> <strong> <spring:message code="tonema.ticker"/> : </strong></td>
<td> <jstl:out value="${tonema.ticker}"></jstl:out>	</td>

</tr>

<tr>
<td> <strong> <spring:message code="tonema.publicationMoment"/> : </strong></td>
<td> <jstl:out value="${tonema.publicationMoment}"></jstl:out>	</td>

</tr>

<tr>
<td>  <strong>  <spring:message code="tonema.body"/> </strong> : </td>
<td> <jstl:out value="${tonema.body}"></jstl:out>	</td>
</tr>

<tr>
<td>	<strong>	<spring:message code="tonema.isFinal"/> :	</strong>	</td>
<td>	<jstl:out value="${tonema.isFinal}"></jstl:out>	</td>
</tr>

</table>

<div>
	<input type="button" name="cancel"
			value="<spring:message code="tonema.back" />"
			onclick="window.history.back()" />
			
	<jstl:if test="${!tonema.isFinal}">
		<input style="margin-left: 1%" type="button"
			name="editTonema" value="<spring:message code="tonema.edit" />"
			onclick="redirect: location.href = 'tonema/edit.do?tonemaId=${tonema.id}'" />
	</jstl:if>

<br>
</div>
