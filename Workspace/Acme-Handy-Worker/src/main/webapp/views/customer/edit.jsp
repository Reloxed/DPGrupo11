<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div style="text-align: center;">
	<h2 style="font-family: sans-serif;"><spring:message code="customer.register.title" /></h2>
</div>

<form:form action="customer/edit.do" modelAttribute="customer" id="form">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="name">
		<spring:message code="customer.name" />
	</form:label>
	<spring:message code="customer.namePlaceholder" var="placeholder" />
	<form:input placeholder="${placeholder}" path="name" />
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="middleName">
		<spring:message code="customer.middle.name" />
	</form:label>
	<form:input path="middleName" />
	<form:errors cssClass="error" path="middleName" />
	<br />

	<form:label path="surname">
		<spring:message code="customer.surname" />
	</form:label>
	<spring:message code="customer.surnamePlaceholder" var="placeholder" />
	<form:input path="surname" placeholder="${placeholder }" />
	<form:errors cssClass="error" path="surname" />
	<br />

	<form:label path="photo">
		<spring:message code="customer.photo" />
	</form:label>
	<spring:message code="customer.photoPlaceholder" var="placeholder" />
	<form:input path="photo" placeholder="${placeholder}" />
	<form:errors cssClass="error" path="photo" />
	<br />

	<form:label path="email">
		<spring:message code="customer.email" />
	</form:label>
	<spring:message code="customer.emailPlaceholder" var="placeholder" />
	<form:input path="email" placeholder="${placeholder }"/>
	<form:errors cssClass="error" path="email" />
	<br />

	<form:label path="phoneNumber">
		<spring:message code="customer.phone.number" />
	</form:label>
	<spring:message code="customer.phonePlaceholder" var="placeholder" />
	<form:input path="phoneNumber" placeholder="${placeholder }"/>
	<form:errors cssClass="error" path="phoneNumber" />
	<br />

	<form:label path="address">
		<spring:message code="customer.address" />
	</form:label>
	<spring:message code="customer.addressPlaceholder" var="placeholder" />
	<form:input path="address" placeholder="${placeholder }"/>
	<form:errors cssClass="error" path="addres" />
	<br />

	<form:hidden path="isSuspicious" />
	<form:hidden path="socialProfiles" />
	<form:hidden path="messageBoxes" />
	<form:hidden path="fixUpTasks" />
	<form:hidden path="complaints" />

	<jstl:choose>
		<jstl:when test="${customer.id == 0}">

			<form:label path="userAccount.username">
				<spring:message code="customer.username" />
			</form:label>
			<spring:message code="customer.usernamePlaceholder" var="placeholder" />
			<form:input path="userAccount.username" placeholder="${placeholder }"/>
			<form:errors cssClass="error" path="userAccount.username" />
			<br />

			<form:label path="userAccount.password">
				<spring:message code="customer.password" />
			</form:label>
			<spring:message code="customer.passwordPlaceholder" var="placeholder" />
			<form:password path="userAccount.password" placeholder="${placeholder }"/>
			<form:errors cssClass="error" path="userAccount.password" />
			<br />

		</jstl:when>
		<jstl:otherwise>

			<form:hidden path="userAccount" />

		</jstl:otherwise>
	</jstl:choose>

	<input type="button" name="save" id="save"
		value="<spring:message code="customer.save" />" />
	<input type="button" name="cancel" id="cancel"
		value="<spring:message code="customer.cancel" />" />
</form:form>