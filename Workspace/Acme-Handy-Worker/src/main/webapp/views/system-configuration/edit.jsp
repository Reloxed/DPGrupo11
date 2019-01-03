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

	<form:form action="system-configuration/administrator/edit.do"
		modelAttribute="systemConfiguration" methodParam="post">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="countryCode"
			value="${systemConfiguration.countryCode }" />

		<form:label path="systemName">
			<spring:message code="system.name" />:
		</form:label>
		<form:input path="systemName"
			value="${systemConfiguration.systemName}" />
		<form:errors cssClass="error" path="systemName" />
		<br>

		<form:label path="banner">
			<spring:message code="system.bannerURL" />:
		</form:label>
		<form:input path="banner" value="${systemConfiguration.banner}"
			size="50%" />
		<form:errors cssClass="error" path="banner" />
		<br>

		<form:label path="VAT">
			<spring:message code="system.VAT" />:
		</form:label>
		<form:input path="VAT" value="${systemConfiguration.VAT}" size="5%" />
		<form:errors cssClass="error" path="VAT" />
		<br>

		<form:label path="timeResultsCached">
			<spring:message code="system.resultscached" />:
		</form:label>
		<form:input path="timeResultsCached"
			value="${systemConfiguration.timeResultsCached}" size="5%" />
		<form:errors cssClass="error" path="timeResultsCached" />
		<br>

		<form:label path="maxResults">
			<spring:message code="system.resultspersearch" />:
		</form:label>
		<form:input path="maxResults"
			value="${systemConfiguration.maxResults}" size="5%" />
		<form:errors cssClass="error" path="maxResults" />
		<br>

		<form:label path="welcomeMessage">
			<spring:message code="system.welcomemessage" />:
		</form:label>
		<form:input path="welcomeMessage"
			value="${systemConfiguration.welcomeMessage}" size="100%" />
		<form:errors cssClass="error" path="welcomeMessage" />
		<br>

		<form:label path="spamWords">
			<spring:message code="system.spamwords" />:
		</form:label>
		<form:input path="spamWords"
			placeholder="<spring:message code='system.lists.placeholder' />"
			value="${systemConfiguration.spamWords}" size="100%" />
		<form:errors cssClass="error" path="spamWords" />
		<br>

		<form:label path="negativeWords">
			<spring:message code="system.negativewords" />:
		</form:label>
		<form:input path="negativeWords"
			placeholder="<spring:message code='system.lists.placeholder' />"
			value="${systemConfiguration.negativeWords}" size="100%" />
		<form:errors cssClass="error" path="negativeWords" />
		<br>

		<form:label path="positiveWords">
			<spring:message code="system.positivewords" />:
		</form:label>
		<form:input path="positiveWords"
			placeholder="<spring:message code='system.lists.placeholder' />"
			value="${systemConfiguration.positiveWords}" size="100%" />
		<form:errors cssClass="error" path="positiveWords" />
		<br>

		<form:label path="listCreditCardMakes">
			<spring:message code="system.creditcardmakes" />:
		</form:label>
		<form:input path="listCreditCardMakes"
			placeholder="<spring:message code='system.lists.placeholder' />"
			value="${systemConfiguration.listCreditCardMakes}" size="100%" />
		<form:errors cssClass="error" path="listCreditCardMakes" />
		<br>

		<input type="submit" name="save" id="save"
			value='<spring:message code="system.save"/>' />
	</form:form>

</security:authorize>
