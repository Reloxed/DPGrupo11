<%--
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('HANDYWORKER')">
	<security:authentication property="principal.username" var="user" />

	<p>
		<spring:message code="phases.title" />
	</p>
	<!-- Tabla de fases -->

	<table class="displayStyle">
		<tr>
			<th><spring:message code="phases.title" /></th>
		</tr>

		<tr>
			<td><display:table pagesize="5" class="displaytag" name="phases"
					requestURI="phase/handy-worker,customer/list.do" id="phases">

					<display:column property="title" titleKey="phases.title"
						sortable="true" />

					<display:column property="description" titleKey="phase.description"
						sortable="true" />

					<display:column property="startMoment" titleKey="phase.startmoment"
						sortable="true" />

					<display:column property="endMoment" titleKey="phase.endmoment"
						sortable="true" />

					<jstl:if test="${user == creator.userAccount.username}">
						<display:column>
							<input type="button" name="delete"
								value="<spring:message code="phase.delete" />"
								onclick="redirect: location.href = 'phase/handy-worker/delete.do?phaseID=${phases.id}';" />
						</display:column>
					</jstl:if>
				</display:table></td>
		</tr>
	</table>
	<jstl:if test="${user == creator.userAccount.username}">
		<a href="phase/handy-worker/create.do?fixuptaskID=${fixuptaskID}"><spring:message
				code="phase.create" /></a>
	</jstl:if>
</security:authorize>
