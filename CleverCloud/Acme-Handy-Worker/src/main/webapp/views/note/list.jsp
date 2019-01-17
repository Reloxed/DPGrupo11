<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize
	access="hasAnyRole('REFEREE','CUSTOMER','HANDYWORKER')">
	<!-- Listing grid -->

	<display:table pagesize="5" class="displaytag" name="notes"
		requestURI="note/referee/list.do" id="row">

		<!-- Attributes-->

		<spring:message code="note.publish.moment" var="publishHeader" />
		<display:column property="publishedMoment" title="${publishHeader}"
			sortable="true" />

		<security:authorize access="hasRole('REFEREE')">
			<spring:message code="note.display" var="displayHeader" />
			<display:column title="${displayHeader}" sortable="true">
				<a href="note/referee/display.do?noteId=${row.id}"> <spring:message
						code="report.display" />
				</a>
			</display:column>
		</security:authorize>

		<security:authorize access="hasRole('CUSTOMER')">
			<spring:message code="note.display" var="displayHeader" />
			<display:column title="${displayHeader}" sortable="true">
				<a href="note/customer/display.do?noteId=${row.id}"> <spring:message
						code="report.display" />
				</a>
			</display:column>
		</security:authorize>

		<security:authorize access="hasRole('HANDYWORKER')">
			<spring:message code="note.display" var="displayHeader" />
			<display:column title="${displayHeader}" sortable="true">
				<a href="note/handyWorker/display.do?noteId=${row.id}"> <spring:message
						code="report.display" />
				</a>
			</display:column>
		</security:authorize>

	</display:table>
</security:authorize>