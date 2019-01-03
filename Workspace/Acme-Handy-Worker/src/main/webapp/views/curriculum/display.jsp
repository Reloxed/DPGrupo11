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


<!-- Botones para añadir records -->
<table class="displayStyle">
		<tr>
			<th colspan="5"><spring:message code="curriculum.buttonsCreationRecords" /></th>
		</tr>
		<tr>
			<td>
				<input type="button" name="addPR"
				value="<spring:message code="curriculum.addPR" />"
				onclick="redirect: location.href = 'professionalRecord/handyWorker/create.do';" />
			</td>
			
			<td>
				<input type="button" name="addER"
				value="<spring:message code="curriculum.addER" />"
				onclick="redirect: location.href = 'educationRecord/handyWorker/create.do';" />
			</td>
			<td>
				<input type="button" name="addENR"
				value="<spring:message code="curriculum.addENR" />"
				onclick="redirect: location.href = 'endorserRecord/handyWorker/create.do';" />
			</td>
			<td>
				<input type="button" name="addMR"
				value="<spring:message code="curriculum.addMR" />"
				onclick="redirect: location.href = 'miscellaneousRecord/handyWorker/create.do';" />
			</td>

		</tr>
		
	</table>

<table class="displayStyle">

<tr>
<td> <strong> <spring:message code="curriculum.ticker" /> : </strong> </td>
<td> <jstl:out value="${ curriculum.ticker}"></jstl:out> </td>
</tr>

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

<!-- Solo mostramos un record de cada, si se quiere ver todos los records de un mismo tip, nos vamos a la vista de display de ese record -->
<!-- ojo con los atributos vacios -->
<jstl:if test="${ not empty curriculum.professionalRecords}" >



<tr>
<td> <strong> <spring:message code="curriculum.professionalRecords" /> : </strong> </td>
<td>
	<table class="displayStyle">
	
	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecords.companyName" /> : </strong></td>
	<td><jstl:out value="${curriculum.professionalRecords.companyName}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecords.startDate" /> : </strong></td>
	<td><jstl:out value="${curriculum.professionalRecords.startDate}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecords.endDate" /> : </strong></td>
	<td><jstl:out value="${curriculum.professionalRecords.endDate}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecords.role" /> : </strong></td>
	<td><jstl:out value="${curriculum.professionalRecords.role}"/></td>
	</tr>

	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecords.attachment" /> : </strong></td>
	<td><a href="${curriculum.professionalRecords.attachment }">${curriculum.professionalRecords.attachment } </a></td>
	</tr>
	<!-- Solo primer comentario -->
	<tr>
	<td><strong> <spring:message code="curriculum.professionalRecords.comments" /> : </strong></td>
	<td><jstl:out value="${curriculum.professionalRecords.comments}"/></td>
	</tr>
	
	</table>
</td>
</tr>
</jstl:if>


<jstl:if test="${ not empty curriculum.educationRecords}" >


<tr>
<td> <strong> <spring:message code="curriculum.educationRecords" /> : </strong> </td>
<td>
	<table class="displayStyle">

	<tr>
	<td><strong> <spring:message code="curriculum.educationRecords.diplomaTitle" /> : </strong></td>
	<td><jstl:out value="${curriculum.educationRecords.diplomaTitle}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.educationRecords.startDate" /> : </strong></td>
	<td><jstl:out value="${curriculum.educationRecords.startDate}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.educationRecords.endDate" /> : </strong></td>
	<td><jstl:out value="${curriculum.educationRecords.endDate}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.educationRecords.institutionName" /> : </strong></td>
	<td><jstl:out value="${curriculum.educationRecords.institutionName}"/></td>
	</tr>

	<tr>
	<td><strong> <spring:message code="curriculum.educationRecords.linkAttachment" /> : </strong></td>
	<td><a href="${curriculum.educationRecords.linkAttachment }">${curriculum.educationRecords.linkAttachment } </a></td>
	</tr>
	<!-- Solo primer comentario -->
	<tr>
	<td><strong> <spring:message code="curriculum.educationRecords.comments" /> : </strong></td>
	<td><jstl:out value="${curriculum.educationRecords.comments}"/></td>
	</tr>
	
	</table>
</td>
</tr>
</jstl:if>


<jstl:if test="${ not empty curriculum.endorserRecords}" >


<tr>
<td> <strong> <spring:message code="curriculum.endorserRecords" /> : </strong> </td>
<td>
	<table class="displayStyle">

	<tr>
	<td><strong> <spring:message code="curriculum.endorserRecords.fullName" /> : </strong></td>
	<td><jstl:out value="${curriculum.endorserRecords.fullName}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.endorserRecords.email" /> : </strong></td>
	<td><jstl:out value="${curriculum.endorserRecords.email}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.endorserRecords.phoneNumber" /> : </strong></td>
	<td><jstl:out value="${curriculum.endorserRecords.phoneNumber}"/></td>
	</tr>

	<tr>
	<td><strong> <spring:message code="curriculum.endorserRecords.linkedinLink" /> : </strong></td>
	<td><a href="${curriculum.endorserRecords.linkedinLink }">${curriculum.endorserRecords.linkedinLink } </a></td>
	</tr>
	<!-- Solo primer comentario -->
	<tr>
	<td><strong> <spring:message code="curriculum.endorserRecords.comments" /> : </strong></td>
	<td><jstl:out value="${curriculum.endorserRecords.comments}"/></td>
	</tr>
	
	</table>
</td>
</tr>
</jstl:if>

<jstl:if test="${ not empty curriculum.miscellaneousRecords}" >


<tr>
<td> <strong> <spring:message code="curriculum.miscellaneousRecords" /> : </strong> </td>
<td>
	<table class="displayStyle">

	<tr>
	<td><strong> <spring:message code="curriculum.miscellaneousRecords.title" /> : </strong></td>
	<td><jstl:out value="${curriculum.miscellaneousRecords.title}"/></td>
	</tr>
	
	<tr>
	<td><strong> <spring:message code="curriculum.miscellaneousRecords.linkAttachment" /> : </strong></td>
	<td><a href="${curriculum.miscellaneousRecords.linkAttachment }">${curriculum.miscellaneousRecords.linkAttachment } </a></td>
	</tr>
	<!-- Solo primer comentario -->
	<tr>
	<td><strong> <spring:message code="curriculum.miscellaneousRecords.comments" /> : </strong></td>
	<td><jstl:out value="${curriculum.miscellaneousRecords.comments}"/></td>
	</tr>
	
	</table>
</td>
</tr>
</jstl:if>

	<jstl:if test="${curriculum.id!=0}">
		<input type="button" name="edit"
		value="<spring:message code="curriculum.edit" />"
		onclick="redirect: location.href = 'curriculum/handyWorker/edit.do?curriculumId=${curriculum.id}';" />
	</jstl:if>



</security:authorize>
