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


<security:authorize access="hasRole('REFEREE')">

	
		<jstl:if test="${requestURI.endsWith('list2.do')}">

		<h2 style="font-family: sans-serif;">
			<spring:message code="complaint.list" />
		</h2>

		<display:table name="notAssigned" id="row"
			requestURI="complaint/referee/list2.do" pagesize="10"
			class="displaytag">
			<display:column property="fixUpTask.description"
				titleKey="complaint.fixuptask" />
			<display:column property="moment" titleKey="complaint.moment"
				sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
			<display:column property="description"
				titleKey="complaint.description" />
			<display:column property="attachements"
				titleKey="complaint.attachements" />
			<display:column property="ticker" titleKey="complaint.ticker" />
			<spring:message code="complaint.assign" var="assignRefereeHeader" />

			<display:column title="${assignRefereeHeader}">
				<a href="complaint/referee/assignReferee.do?complaintId=${row.id}">
					<spring:message code="complaint.assign" />
				</a>
			</display:column>

		</display:table>
	</jstl:if>

	<jstl:if test="${requestURI.endsWith('list1.do')}">

		<h2 style="font-family: sans-serif;">
			<spring:message code="complaint.list" />
		</h2>

		<display:table name="complaints" id="row"
			requestURI="complaint/referee/list.do" pagesize="10"
			class="displaytag">
			<display:column property="fixUpTask.description"
				titleKey="complaint.fixuptask" />
			<display:column property="moment" titleKey="complaint.moment"
				sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
			<display:column property="description"
				titleKey="complaint.description" />
			<display:column property="attachements"
				titleKey="complaint.attachements" />
			<display:column property="ticker" titleKey="complaint.ticker" />
			<spring:message code="complaint.assign" var="assignRefereeHeader" />

			
				
			<spring:message code="complaint.create.report"
			var="createReportHeader" />
		<display:column title="${createReportHeader}">
			<a href="report/complaint/create.do?complaintId=${row.id}"> <spring:message
					code="complaint.create.report" />

			</a>
		</display:column>
			
		</display:table>
		

	</jstl:if>
</security:authorize>








