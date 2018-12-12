<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div style="text-align: center;">
	<h2 style="font-family: sans-serif;">
		<spring:message code="register.title" />
	</h2>
</div>

<form:form action="register/edit.do" modelAttribute="actor" id="form">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:hidden path="userAccount.authorities[0]"/>

	<form:label path="name">
		<spring:message code="register.name" />
	</form:label>
	<spring:message code="register.name.placeholder" var="placeholder" />
	<form:input placeholder="${placeholder}" path="name" />
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="middleName">
		<spring:message code="register.middle.name" />
	</form:label>
	<form:input path="middleName" />
	<form:errors cssClass="error" path="middleName" />
	<br />

	<form:label path="surname">
		<spring:message code="register.surname" />
	</form:label>
	<spring:message code="register.surname.placeholder" var="placeholder" />
	<form:input path="surname" placeholder="${placeholder }" />
	<form:errors cssClass="error" path="surname" />
	<br />

	<form:label path="photo">
		<spring:message code="register.photo" />
	</form:label>
	<spring:message code="register.photo.placeholder" var="placeholder" />
	<form:input path="photo" placeholder="${placeholder}" />
	<form:errors cssClass="error" path="photo" />
	<br />

	<form:label path="email">
		<spring:message code="register.email" />
	</form:label>
	<spring:message code="register.email.placeholder" var="placeholder" />
	<form:input path="email" placeholder="${placeholder }" />
	<form:errors cssClass="error" path="email" />
	<br />

	<form:label path="phoneNumber">
		<spring:message code="register.phone.number" />
	</form:label>
	<spring:message code="register.phone.placeholder" var="placeholder" />
	<form:input path="phoneNumber" placeholder="${placeholder }" id="phone" />
	<form:errors cssClass="error" path="phoneNumber" />
	<br />

	<form:label path="address">
		<spring:message code="register.address" />
	</form:label>
	<spring:message code="register.address.placeholder" var="placeholder" />
	<form:input path="address" placeholder="${placeholder }" />
	<form:errors cssClass="error" path="addres" />
	<br />

	<form:hidden path="isSuspicious" />
	<form:hidden path="socialProfiles" />
	<form:hidden path="messageBoxes" />

	<form:label path="userAccount.username">
		<spring:message code="register.username" />
	</form:label>
	<spring:message code="register.username.placeholder" var="placeholder" />
	<form:input path="userAccount.username" placeholder="${placeholder }" />
	<form:errors cssClass="error" path="userAccount.username" />
	<br />

	<form:label path="userAccount.password">
		<spring:message code="register.password" />
	</form:label>
	<spring:message code="register.password.placeholder" var="placeholder" />
	<form:password path="userAccount.password"
		placeholder="${placeholder }" />
	<form:errors cssClass="error" path="userAccount.password" />
	<br />

	<input type="submit" name="save" id="save" onclick="checkPhone()"
		value="<spring:message code="register.save" />" />
	<input type="button" name="cancel" id="cancel"
		onclick="window.history.back()"
		value="<spring:message code="register.cancel" />" />
</form:form>

<script>
	function checkPhone() {
		var phone = document.getElementById("phone");
		if (/^(+[0-9]{3}[0-9]{9})$/.test(phone) == false) {
			return confirm("<spring:message code="register.phone.confirmation" />");
		}
	}
</script>