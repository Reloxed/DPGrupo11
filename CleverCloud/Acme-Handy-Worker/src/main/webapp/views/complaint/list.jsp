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

<display:table name="complaints" id="row" requestURI="${requestURI}"
	pagesize="10" class="displaytag">
	<display:column property="fixUpTask.description"
		titleKey="complaint.fixuptask" />
	<display:column property="moment" titleKey="complaint.moment"
		sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
	<display:column property="description" titleKey="complaint.description" />
	<display:column property="attachments" titleKey="complaint.attachments" />
	<display:column property="ticker" titleKey="complaint.ticker" />

	<security:authorize access="hasRole('REFEREE')">
		<jstl:if test="${requestURI.endsWith('listNotAssigned.do')}">
			<spring:message code="complaint.assign" var="assignRefereeHeader" />
			<display:column title="${assignRefereeHeader}">
				<a href="complaint/referee/assignReferee.do?complaintId=${row.id}">
					<spring:message code="complaint.assign" />
				</a>
			</display:column>
		</jstl:if>

		<jstl:if test="${requestURI.endsWith('listAssigned.do')}">
			<spring:message code="complaint.create.report"
				var="createReportHeader" />
			<display:column title="${createReportHeader}">
				<jstl:set var="bool" value="${0}" />
				<jstl:forEach var="x" items="${reports}">
					<jstl:if test="${x.complaint.id == row.id}">
						<jstl:set var="bool" value="${1}" />
					</jstl:if>
				</jstl:forEach>
				<jstl:if test="${bool == 0}">
					<a href="report/referee/create.do?complaintId=${row.id}"><spring:message
							code="complaint.create.report" /></a>
				</jstl:if>
			</display:column>
		</jstl:if>
	</security:authorize>

	<security:authorize access="hasRole('HANDYWORKER')">
		<display:column>
			<a href="complaint/handyWorker/display.do?complaintId=${row.id}">
				<spring:message code="complaint.display" />
			</a>
		</display:column>
		<display:column>
			<jstl:forEach var="x" items="${reports}">
				<jstl:if test="${x.complaint.id == row.id && x.isFinal == true}">
					<a href="report/handyWorker/display.do?reportId=${x.id}"> <spring:message
							code="report.display" />
					</a>
				</jstl:if>
			</jstl:forEach>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<a href="complaint/customer/display.do?complaintId=${row.id}"> <spring:message
					code="complaint.display" />
			</a>
		</display:column>
		<display:column>
			<jstl:forEach var="x" items="${reports}">
				<jstl:if test="${x.complaint.id == row.id && x.isFinal == true}">
					<a href="report/customer/display.do?reportId=${x.id}"> <spring:message
							code="report.display" />
					</a>
				</jstl:if>
			</jstl:forEach>
		</display:column>
	</security:authorize>

</display:table>

<security:authorize access="hasRole('CUSTOMER')">
	<p>
		<a href="complaint/customer/create.do"><spring:message
				code="complaint.create" /></a>
	</p>
</security:authorize>
