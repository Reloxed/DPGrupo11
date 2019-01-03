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

<security:authorize access="hasRole('ADMINISTRATOR')">

	<p>
		<spring:message code="system.configuration.title" />
	</p>

	<h5>
		<strong><spring:message code="system.name" />:</strong>
		<jstl:out value="${systemConfiguration.systemName}"></jstl:out>
	</h5>

	<h5>
		<strong><spring:message code="system.bannerURL" />:</strong>
		<jstl:out value="${systemConfiguration.banner}"></jstl:out>
	</h5>

	<h5>
		<strong><spring:message code="system.VAT" />:</strong>
		<jstl:out value="${systemConfiguration.VAT}"></jstl:out>
	</h5>

	<h5>
		<strong><spring:message code="system.resultscached" />:</strong>
		<jstl:out value="${systemConfiguration.timeResultsCached}"></jstl:out>
	</h5>

	<h5>
		<strong><spring:message code="system.resultspersearch" />:</strong>
		<jstl:out value="${systemConfiguration.maxResults}"></jstl:out>
	</h5>

	<div style="width: 20%; float: left">
		<table class="displayStyle">
			<tr>
				<td><display:table pagesize="5" class="displaytag"
						name="spamWords"
						requestURI="system-configuration/administrator/display.do"
						id="spamWords">

						<display:column titleKey="system.spamwords" value="${spamWords}"
							sortable="true" />

					</display:table></td>
			</tr>
		</table>
	</div>

	<div style="width: 20%; float: left; position: static">
		<table class="displayStyle">
			<tr>
				<td><display:table pagesize="5" class="displaytag"
						name="negativeWords"
						requestURI="system-configuration/administrator/display.do"
						id="negativeWords">

						<display:column titleKey="system.negativewords"
							value="${negativeWords}" sortable="true" />

					</display:table></td>
			</tr>
		</table>
	</div>

	<div style="width: 20%; float: left; position: static">
		<table class="displayStyle">
			<tr>
				<td><display:table pagesize="5" class="displaytag"
						name="positiveWords"
						requestURI="system-configuration/administrator/display.do"
						id="positiveWords">

						<display:column titleKey="system.positivewords"
							value="${positiveWords}" sortable="true" />

					</display:table></td>
		</table>
	</div>

	<div style="width: 20%; float: left; position: static">
		<table class="displayStyle">
			<tr>
				<td><display:table pagesize="5" class="displaytag"
						name="creditCardMakes"
						requestURI="system-configuration/administrator/display.do"
						id="creditCardMakes">

						<display:column titleKey="system.creditcardmakes"
							value="${creditCardMakes}" sortable="true" />

					</display:table></td>
		</table>
	</div>

	<div style="width: 20%; float: left; position: static">
		<table class="displayStyle">
			<tr>
				<td><display:table pagesize="5" class="displaytag"
						name="welcomeMessage"
						requestURI="system-configuration/administrator/display.do"
						id="welcomeMessage">

						<display:column titleKey="system.welcomemessage"
							value="${welcomeMessage}" sortable="true" />

					</display:table></td>
		</table>
	</div>
	<input type="button" name="edit"
		value='<spring:message code="system.edit"/>'
		onclick="redirect: location.href = 'system-configuration/administrator/edit.do?systemconfigurationID=${systemConfiguration.id}';" />
</security:authorize>
