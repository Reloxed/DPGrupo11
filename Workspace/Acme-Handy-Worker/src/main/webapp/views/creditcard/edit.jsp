<%--
 * action-1.jsp
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

<p>
	<spring:message code="actor.edit" />
</p>

<form:form action="creditcard/edit.do" modelAttribute="creditCard"
	methodParam="post">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="holderName">
		<spring:message code="creditcard.holdername" />:
			</form:label>
	<form:input path="holderName" />
	<form:errors cssClass="error" path="holderName" />
	<br />

	<form:select path="brandName" items="${makes}" />
	<br />

	<form:label path="number">
		<spring:message code="creditcard.number" />:
			</form:label>
	<form:input path="number" />
	<form:errors cssClass="error" path="number" />
	<br />

	<form:label path="expirationMonth">
		<spring:message code="creditcard.expirationmonth" />:
			</form:label>
	<form:input path="expirationMonth" />
	<form:errors cssClass="error" path="expirationMonth" />
	<br />

	<form:label path="expirationYear">
		<spring:message code="creditcard.expirationyear" />:
			</form:label>
	<form:input path="expirationYear" />
	<form:errors cssClass="error" path="expirationYear" />
	<br />

	<form:label path="CVV">
		<spring:message code="creditcard.cvv" />:
			</form:label>
	<form:input path="CVV" />
	<form:errors cssClass="error" path="CVV" />
	<br />

	<input type="submit" name="save" id="save"
		value='<spring:message code="creditcard.save"/>' />
	<input type="button" name="cancel"
		value="<spring:message code="creditcard.cancel" />"
		onclick="javascript: relativeRedir('actor/display.do?actorID=${principal.id}');" />
	<br />

</form:form>