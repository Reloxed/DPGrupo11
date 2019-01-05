<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('HANDYWORKER')">


<p><spring:message code="curriculum.name" var="name"/></p>
<h3> <jstl:out value="${name} ${curriculum.personalRecord.fullName}"> </jstl:out> </h3>

<table class="displayStyle">

<tr>
<td> <strong> <spring:message code="curriculum.ticker" /> : </strong> </td>
<td> <jstl:out value="${ curriculum.ticker}"></jstl:out> </td>
</tr>

<!-- PERSONAL RECORD -->

<tr>
<td> <strong> <spring:message code="curriculum.personalRecord" /> : </strong> </td>
<td>
	<table class="displayStyle">
	
	<tr>
	<td><strong> <spring:message code="curriculum.personalRecord.fullName" /> : </strong></td>
	<td><jstl:out value="${curriculum.personalRecord.fullName}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.personalRecord.photo" /> : </strong></td>
	<td><jstl:out value="${curriculum.personalRecord.photo}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.personalRecord.email" /> : </strong></td>
	<td><jstl:out value="${curriculum.personalRecord.email}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.personalRecord.phoneNumber" /> : </strong></td>
	<td><jstl:out value="${curriculum.personalRecord.phoneNumber}"/></td>
	</tr>

	<tr>
	<td><strong> <spring:message code="curriculum.personalRecord.linkedinLink" /> : </strong></td>
	<td><a href="${curriculum.personalRecord.linkedinLink }">${curriculum.personalRecord.linkedinLink } </a></td>
	</tr>
	</table>
</td>
</tr>

</table>

<!-- PROFESSIONAL RECORDS -->

	<jstl:if test="${ not empty curriculum.professionalRecords}" >

<tr>
<td> <strong> <spring:message code="curriculum.professionalRecords" /> : </strong> </td>
<td>
	<jstl:forEach var="pr" items="${curriculum.professionalRecords}">
	
	
	<table class="displayStyle">
	
	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecords.companyName" /> : </strong></td>
	<td><jstl:out value="${pr.companyName}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecords.startDate" /> : </strong></td>
	<td><jstl:out value="${pr.startDate}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecords.endDate" /> : </strong></td>
	<td><jstl:out value="${pr.endDate}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecords.role" /> : </strong></td>
	<td><jstl:out value="${pr.role}"/></td>
	</tr>

	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecords.attachment" /> : </strong></td>
	<td><a href="${pr.attachment }">${pr.attachment } </a></td>
	</tr>

	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecords.comments" /> : </strong></td>
	<td>
		<ul>
			<jstl:forEach var="comment" items="${pr.comments}">
			<jstl:out value="${comment }"/>
			</jstl:forEach>
		</ul>
	</td>
	</tr>
	
	</table>
	</jstl:forEach>
</td>
</tr>

	</jstl:if>
<jstl:if test="${ not empty curriculum.educationRecords}" >
<!-- EDUCATION RECORDS -->

<tr>
<td> <strong> <spring:message code="curriculum.educationRecords" /> : </strong> </td>
<td>

<jstl:forEach var="er" items="${curriculum.educationRecords}">

	<table class="displayStyle">

	<tr>
	<td><strong> <spring:message code="curriculum.educationRecords.diplomaTitle" /> : </strong></td>
	<td><jstl:out value="${er.diplomaTitle}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.educationRecords.startDate" /> : </strong></td>
	<td><jstl:out value="${er.startDate}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.educationRecords.endDate" /> : </strong></td>
	<td><jstl:out value="${er.endDate}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.educationRecords.institutionName" /> : </strong></td>
	<td><jstl:out value="${er.institutionName}"/></td>
	</tr>

	<tr>
	<td><strong> <spring:message code="curriculum.educationRecords.linkAttachment" /> : </strong></td>
	<td><a href="${er.linkAttachment }">${er.linkAttachment } </a></td>
	</tr>

	<tr>
	<td><strong> <spring:message code="curriculum.educationRecords.comments" /> : </strong></td>
	<td>
	
			<ul>
			<jstl:forEach var="comment" items="${er.comments}">
			<jstl:out value="${comment }"/>
			</jstl:forEach>
		</ul>
	
	</td>
	</tr>
	
	</table>
	</jstl:forEach>
</td>
</tr>
</jstl:if>


<jstl:if test="${ not empty curriculum.endorserRecords}" >
<!-- ENDORSER RECORDS -->

<tr>
<td> <strong> <spring:message code="curriculum.endorserRecords" /> : </strong> </td>
<td>
	
	<jstl:forEach var="ed" items="${curriculum.endorserRecords}">
	
	<table class="displayStyle">

	<tr>
	<td><strong> <spring:message code="curriculum.endorserRecords.fullName" /> : </strong></td>
	<td><jstl:out value="${ed.fullName}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.endorserRecords.email" /> : </strong></td>
	<td><jstl:out value="${ed.email}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.endorserRecords.phoneNumber" /> : </strong></td>
	<td><jstl:out value="${ed.phoneNumber}"/></td>
	</tr>

	<tr>
	<td><strong> <spring:message code="curriculum.endorserRecords.linkedinLink" /> : </strong></td>
	<td><a href="${ed.linkedinLink }">${ed.linkedinLink } </a></td>
	</tr>

	<tr>
	<td><strong> <spring:message code="curriculum.endorserRecords.comments" /> : </strong></td>
	<td>
			<ul>
			<jstl:forEach var="comment" items="${ed.comments}">
			<jstl:out value="${comment }"/>
			</jstl:forEach>
		</ul>

	</tr>
	
	</table>
	</jstl:forEach>
</td>
</tr>
</jstl:if>

<jstl:if test="${ not empty curriculum.miscellaneousRecords}" >

<!-- MISCELLANEOUS RECORDS -->
<tr>
<td> <strong> <spring:message code="curriculum.miscellaneousRecords" /> : </strong> </td>
<td>

<jstl:forEach var="mr" items="${curriculum.miscellaneousRecords}">

	<table class="displayStyle">

	<tr>
	<td><strong> <spring:message code="curriculum.miscellaneousRecords.title" /> : </strong></td>
	<td><jstl:out value="${mr.title}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.miscellaneousRecords.linkAttachment" /> : </strong></td>
	<td><a href="${mr.linkAttachment }">${mr.linkAttachment } </a></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.miscellaneousRecords.comments" /> : </strong></td>
	<td>
	<ul>
			<jstl:forEach var="comment" items="${mr.comments}">
			<jstl:out value="${comment }"/>
			</jstl:forEach>
		</ul>
	</tr>
	
	</table>
	</jstl:forEach>
</td>
</tr>

</jstl:if>

	
	
		<input type="button" name="edit"
		value="<spring:message code="curriculum.edit" />"
		onclick="redirect: location.href = 'curriculum/handyWorker/edit.do?curriculumID=${curriculum.id}';" />
	


</security:authorize>
