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



<h3> <jstl:out value="${audit.title}"> </jstl:out> </h3>

<table class="displayStyle">


<tr>
<th> <jstl:out value="${audit.title}"> </jstl:out>  </th>
<th>   </th>
</tr>


<tr>
<td> <strong> <spring:message code="audit.moment" /> : </strong> </td>
<td> <fmt:formatDate value="${audit.moment}" pattern="dd/MM/yyyy HH:mm" /> </td>
</tr>


<tr>
<td> <strong> <spring:message code="audit.description" /> : </strong> </td>
<td> <jstl:out value="${audit.description}"> </jstl:out> </td>
</tr>

<tr>
<td> <strong> <spring:message code="audit.linkAttachment" /> : </strong> </td>
<td>
<jstl:choose>
<jstl:when test="${not empty audit.linkAttachment}"> 
<ul>
<jstl:forEach items="${audit.linkAttachment}" var="link">
<li> <a href="${link}"> ${link} </a> </li>
</jstl:forEach>
</ul> 
</jstl:when>
<jstl:otherwise>

<spring:message code="audit.linkAttachment.empty" />

</jstl:otherwise>
</jstl:choose>

 </td>
</tr>

<tr>
<td> <strong> <spring:message code="audit.trip" /> : </strong> </td>
<spring:message code="audit.trip.show" var="showTrip" />
	<td>  <jstl:out value = "${audit.trip.title}"/> &nbsp; (<a href="trip/display.do?tripId=${audit.trip.id}"> ${showTrip} </a>) </td>

</tr>

<tr>
<td> <strong> <spring:message code="audit.auditor" /> : </strong> </td>
<td> <a href="actor/display.do?actorId=${audit.auditor.id}"> <jstl:out value = "${audit.auditor.userAccount.username}"/> </a> </td>

</tr>





</table>

<security:authorize access="isAuthenticated()">
<security:authentication property="principal.username" var="user" /> 

<jstl:if test="${audit.auditor.userAccount.username == user}">

<div>


<jstl:if test="${audit.isDraft}">
					<a href="audit/auditor/edit.do?auditId=${audit.id}" >
						<spring:message code="audit.edit" />
					</a>					
</jstl:if>
				
			
</div>


</jstl:if>
</security:authorize>
