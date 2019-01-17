<%--
 * action-2.jsp
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

<security:authorize access="hasRole('HANDYWORKER')">

	<form:form action="endorserment/handyWorker/edit.do"
		modelAttribute="endorsement" methodParam="post" style="margin-left: 20px">
		<form:hidden path="id" />
		<form:hidden path="version" />
		
		<jstl:if test="${report.id == 0 }">
			<form:hidden path="publishedMoment" value="01/01/2001 00:00" />
		</jstl:if>
		<jstl:if test="${report.id != 0 }">
			<form:hidden path="publishedMoment" />
		</jstl:if>

		<div>
			<p><form:label path="comments"><spring:message code="endorsement.comments" />: </form:label></p>
			<form:textarea path="comments" />
		</div>
		<jstl:if test="${endorsement.id==0}">
			<div>
				<p><spring:message code="endorsement.recipient" /></p>
				<form:select path="recipient" style="width:100px;">
					<jstl:forEach var="x" items="${endorsers}">
						<form:option value="${x.name}" />
						<jstl:out value="${x.name}" />
					</jstl:forEach>
				</form:select>
				<br />
			</div>
		</jstl:if>
		<br />
		<spring:message code="endorsement.save" var="saveEndorsement" />
		<spring:message code="endorsement.cancel" var="cancelEndorsement" />

		<input type="submit" name="save" value="${saveEndorsement}" />&nbsp; 
	<input type="button" name="cancel" value="${cancelEndorsement}"
			onclick="javascript: relativeRedir('endorsement/handyWorkers/list.do');" />
		<br />
	</form:form>
</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">

	<form:form action="endorserment/customer/edit.do"
		modelAttribute="endorsement" methodParam="post" style="margin-left: 20px">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="publishedMoment" />

		<div>
			<p><form:label path="comments"><spring:message code="endorsement.comments" />: </form:label></p>
			<form:textarea path="comments" />
		</div>
		<jstl:if test="${endorsement.id==0}">
			<div>
				<p><spring:message code="endorsement.recipient" /></p>
				<form:select path="recipient" style="width:100px;">
					<jstl:forEach var="x" items="${recipients}">
						<form:option value="${x.name}" />
						<jstl:out value="${x.name}" />
					</jstl:forEach>
				</form:select>
				<br />
			</div>
		</jstl:if>
		<br />
		<spring:message code="endorsement.save" var="saveEndorsement" />
		<spring:message code="endorsement.cancel" var="cancelEndorsement" />

		<input type="submit" name="save" value="${saveEndorsement}" />&nbsp; 
	<input type="button" name="cancel" value="${cancelEndorsement}"
			onclick="window.history.back()" />
		<br />
	</form:form>
</security:authorize>