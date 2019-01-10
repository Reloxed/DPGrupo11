<%--
 *
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


<<<<<<< HEAD
<display:table name="complaints" id="complaints"
	requestURI="complaints/referee/list.do" pagesize="10"
	class="displaytag">
=======
<security:authorize access="hasRole('CUSTOMER')">
	<jstl:set var="uri" value="complaint/fixUpTask.do" />
</security:authorize>
<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:set var="uri" value="complaint/handyWorker/list.do" />
</security:authorize>
<security:authorize access="hasRole('REFEREE')">
	<jstl:set var="uri" value="complaint/list.do" />
</security:authorize>

<display:table name="complaints" id="row" requestURI="${ uri }" pagesize="10" class="displaytag">
	
>>>>>>> Lucia
	<display:column property="fixUpTask.description" titleKey="complaint.fixuptask" />
	
	<display:column property="moment" titleKey="complaint.moment"
		sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
	
	<display:column property="description" titleKey="complaint.description" />
	
	<display:column property="attachements"
		title=<spring:message code="complaint.attachments" /> />
	
	<display:column property="ticker" titleKey="complaint.ticker" />
<<<<<<< HEAD
=======
	
	<display:column>
		<!-- TODO: solo para el customer que lo ha escrito: <a href="${requestURI}"><spring:message	code="complaint.edit" /></a>-->
		<a href="complaint/handyWorker/display.do?complaintId=${row.id}"><spring:message code="complaint.display" /></a>
	</display:column>
>>>>>>> Lucia
</display:table>
