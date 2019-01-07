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
<table class="displayStyle">

<tr>
<th><jstl:out value="${report.description}"></jstl:out></th>
<th>   </th>
</tr>

<tr>
<td> <strong> <spring:message code="report.moment"/> : </strong>  </td>
<td> <fmt:formatDate value="${report.publishedMoment}" pattern="dd/MM/yyyy HH:mm"/></td>
</tr>

<tr>
<td> <strong> <spring:message code="report.attachments"/> : </strong></td>
<td>
<jstl:choose>
<jstl:when test="${not empty report.attachments}">
<ul>
<jstl:forEach items="${report.attachments}" var="link">
<li> <a href="${link}"> ${link} </a> </li>

</jstl:forEach>
</ul>
</jstl:when>

<jstl:otherwise>

<spring:message code="report.attachments.empty"/>

</jstl:otherwise>
</jstl:choose>

</td>
</tr>

<tr>
<td> <strong>  <spring:message code="report.complaint"/> : </strong>  </td>
<spring:message code="report.complaint.show" var="showComplaint"/>
	<td> <jstl:out value="${report.complaint.description}"/> &nbsp; (<a href="complaint/display.do?complainId=${report.complaint.id}"> ${showComplaint}</a>)</td>

</tr>

<tr>
<td> <strong> <spring:message code="report.notes"/> : </strong></td>
<td>
<jstl:choose>
<jstl:when test="${not empty report.notes}">
<ul>
<jstl:forEach items="${report.notes}" var="note">
<li> <jstl:out value="${note.refereeComment}"/> &nbsp; (<a href="note/display.d?noteId=${note.id}"> <spring:message code="report.show.note"/></a>)</li>
</jstl:forEach>
</ul>

</jstl:when>
<jstl:otherwise>

<spring:message code="report.note.empty"/>

</jstl:otherwise>
</jstl:choose>

</td>
</tr>

</table>

<security:authorize access="hasRole('REFEREE')"/>


<jstl:if test="${report.isFinal}">
	<a href="report/referee/edit.do?reportId=${report.id}">
		<spring:message code="report.edit"/>
	</a>

</jstl:if>



