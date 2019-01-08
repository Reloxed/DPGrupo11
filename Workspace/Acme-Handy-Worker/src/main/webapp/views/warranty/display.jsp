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
<h3> <jstl:out value="${warranty.title}"></jstl:out>   </h3>

<table class="displayStyle">

<tr>
<th> <jstl:out value="${warranty.title}"></jstl:out>     </th>
<th>	</th>
</tr>

<tr>
<td> <strong> <spring:message code="warranty.terms"/> : </strong></td>
<td> <jstl:out value="${warranty.terms}"></jstl:out>	</td>

</tr>

<tr>
<td>  <strong>  <spring:message code="warranty.final"/> </strong> : </td>
<td> <jstl:out value="${warranty.isFinal}"></jstl:out>	</td>
</tr>

<tr>
<td>	<strong>	<spring:message code="warranty.laws"/> :	</strong>	</td>
<td>	<jstl:out value="${warranty.laws}"></jstl:out>	</td>
</tr>



</table>

<div>
	<jstl:if test="${warranty.isFinal == false }">
		<a href="warranty/administrator/edit.do?warrantyId=${warranty.id}">
		<spring:message code="warranty.edit"/>
		
		</a>
	
	</jstl:if>

</div>
