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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<security:authentication property="principal.username" var="user" /> 

<jstl:choose> 
<jstl:when test="${(user == note.auditor.userAccount.username) || (user == note.trip.manager.userAccount.username)}">




<h3> <spring:message code="note.title.1"/> <jstl:out value="${note.auditor.userAccount.username}" /> 
<spring:message code="note.title.2"/> <jstl:out value="${note.trip.manager.userAccount.username}" /> </h3>
<br>
 
<div>(<fmt:formatDate value="${note.moment}" pattern="dd/MM/yyyy HH:mm" />) </div>



<table class="displayStyle">


<tr>
<th> <spring:message code="note"/>  </th>
<th>   </th>
</tr>



<tr>
<td> <strong> <spring:message code="note.moment" /> : </strong> </td>
<td> <fmt:formatDate value="${note.moment}" pattern="dd/MM/yyyy HH:mm" /> </td>
</tr>


<tr>
<td> <strong> <spring:message code="note.remark" /> : </strong> </td>
<td> <jstl:out value="${note.remark}"> </jstl:out> </td>
</tr>

<tr>
<td> <strong> <spring:message code="note.trip" /> : </strong> </td>
<spring:message code="note.trip.show" var="showTrip" />
	<td>  <jstl:out value = "${note.trip.title}"/> &nbsp; (<a href="trip/display.do?tripId=${note.trip.id}"> ${showTrip} </a>) </td>

</tr>

<tr>
<td> <strong> <spring:message code="note.auditor" /> : </strong> </td>
<spring:message code="note.actor.show" var="showPersonalData" />
<td>  <jstl:out value = "${note.auditor.userAccount.username}"/> &nbsp; (<a href="actor/display.do?actorId=${note.auditor.id}"> ${showPersonalData} </a>) </td>

</tr>

<tr>
<td> <strong> <spring:message code="note.manager" /> : </strong> </td>
<td>  <jstl:out value = "${note.trip.manager.userAccount.username}"/> &nbsp; (<a href="actor/display.do?actorId=${note.trip.manager.id}"> ${showPersonalData} </a>)  </td>

</tr>

<tr>
<td> <strong> <spring:message code="note.managerReply" /> : </strong> </td>
<jstl:choose>
<jstl:when test="${not empty note.managerReply}">
<td>  ( <fmt:formatDate value="${note.momentReply}" pattern="dd/MM/yyyy HH:mm" /> ) : <jstl:out value = "${note.managerReply}"/>  </td>
</jstl:when>
<jstl:otherwise>
<td>  <spring:message code="note.managerReply.empty" /> </td>
</jstl:otherwise>
</jstl:choose>


</tr>

</table>


<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${empty note.managerReply}">
		<a href="note/manager/reply.do?noteId=${note.id}" >
						<spring:message code="note.reply" />
		</a>					
	</jstl:if>


</security:authorize>

</jstl:when>
<jstl:otherwise>

<div> <spring:message code="note.unauthorizedAccess.message" /> </div>

</jstl:otherwise>
</jstl:choose>





