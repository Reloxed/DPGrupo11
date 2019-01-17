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

	<display:table pagesize="5" class="displaytag" name="reports"
		requestURI="report/referee/list.do" id="row">


		<!-- Action links -->

		<security:authorize access="hasRole('REFEREE')">
			<display:column>
				<jstl:if test="${row.isFinal == false}">
					<a href="report/referee/edit.do?reportId=${row.id}"> <spring:message
							code="report.edit" />
					</a>
				</jstl:if>

			</display:column>
		</security:authorize>

		<!-- Attributes-->

		<spring:message code="report.description" var="descriptionHeader" />
		<display:column property="description" title="${descriptionHeader}"
			sortable="true" />

		<spring:message code="report.moment" var="momentHeader" />
		<display:column property="publishedMoment" title="${momentHeader}"
			sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

		<spring:message code="report.complaint" var="complaintHeader" />
		<spring:message code="report.complaint.show" var="showComplaint" />
		<security:authorize access="hasRole('CUSTOMER')">
			<display:column title="${complaintHeader}">

				<div>
					<jstl:out value="${row.complaint.description}" />
					&nbsp; (<a
						href="complaint/customer/display.do?complaintId=${row.complaint.id}">
						${showComplaint}</a>)
				</div>

			</display:column>
		</security:authorize>

		<security:authorize access="hasRole('REFEREE')">
			<display:column title="${complaintHeader}">

				<div>
					<jstl:out value="${row.complaint.description}" />
					&nbsp; (<a
						href="complaint/referee/display.do?complaintId=${row.complaint.id}">
						${showComplaint}</a>)
				</div>

			</display:column>
			<display:column>
				<a href="report/referee/display.do?reportId=${row.id}"> <spring:message
						code="report.display" />
				</a>

			</display:column>
		</security:authorize>
		<security:authorize access="hasRole('CUSTOMER')">
			<display:column>
				<a href="report/customer/display.do?reportId=${row.id}"> <spring:message
						code="report.display" />
				</a>

			</display:column>
		</security:authorize>
		<security:authorize access="hasRole('HANDYWORKER')">
			<display:column>
				<a href="report/handyWorker/display.do?reportId=${row.id}"> <spring:message
						code="report.display" />
				</a>

			</display:column>
		</security:authorize>

	</display:table>
</security:authorize>