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

<script>
	function checkPhone(msg) {
		var phone = document.getElementById("phoneNumber");
		var phonePattern = new RegExp(
				/^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{3})$/);

		if (phonePattern.test(phone)) {
			return true;
		} else {
			return confirm(msg);
		}
	}
</script>

<p>
	<spring:message code="actor.edit" />
</p>

<spring:message code="phone.confirmation" var="confirmTelephone" />
<form:form action="administrator/administrator/edit.do"
	modelAttribute="administrator" methodParam="post"
	onsubmit="javascript: return checkPhone('${confirmTelephone}');">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:hidden path="socialProfiles" />
	<form:hidden path="messageBoxes" />
	<form:hidden path="isSuspicious" />

	<jstl:choose>
		<jstl:when test="${administrator.id == 0}">

			<form:hidden path="userAccount.isBanned" value="false" />
			<form:hidden path="userAccount.authorities[0].authority" />

			<form:label path="userAccount.username">
				<spring:message code="actor.userAccount.username" />:
			</form:label>
			<form:input path="userAccount.username" />
			<form:errors cssClass="error" path="userAccount.username" />
			<br />

			<form:label path="userAccount.password">
				<spring:message code="actor.userAccount.password" />:
			</form:label>
			<form:password path="userAccount.password" />
			<form:errors cssClass="error" path="userAccount.password" />
			<br />

		</jstl:when>
		<jstl:otherwise>

			<form:hidden path="userAccount" />

		</jstl:otherwise>
	</jstl:choose>

	<form:label path="surname">
		<spring:message code="actor.surname" />:
		</form:label>
	<form:input path="surname" value="${administrator.surname}" />
	<form:errors cssClass="error" path="surname" />
	<br>

	<form:label path="name">
		<spring:message code="actor.name" />:
		</form:label>
	<form:input path="name" value="${administrator.name}" />
	<form:errors cssClass="error" path="name" />
	<br>

	<form:label path="middleName">
		<spring:message code="actor.middlename" />:
		</form:label>
	<form:input path="middleName" value="${administrator.middleName}" />
	<form:errors cssClass="error" path="middleName" />
	<br>

	<form:label path="email">
		<spring:message code="actor.email" />:
		</form:label>
	<form:input path="email" value="${administrator.email}" id="email" />
	<form:errors cssClass="error" path="email" />
	<br>

	<form:label path="photo">
		<spring:message code="actor.photo" />:
		</form:label>
	<form:input path="photo" value="${administrator.photo}" />
	<form:errors cssClass="error" path="photo" />
	<br>

	<form:label path="phoneNumber">
		<spring:message code="actor.phone" />:
		</form:label>
	<form:input path="phoneNumber" value="${administrator.phoneNumber}"
		id="phoneNumber" />
	<form:errors cssClass="error" path="phoneNumber" />
	<br>

	<form:label path="address">
		<spring:message code="actor.address" />:
		</form:label>
	<form:input path="address" value="${administrator.address}" />
	<form:errors cssClass="error" path="address" />
	<br>

	<input type="submit" name="save" id="save"
		value='<spring:message code="actor.save"/>' onclick="checkPhone()" />
	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel" />"
		onclick="javascript: relativeRedir('actor/display.do');" />
	<br />
</form:form>
<script>
	function checkEmail() {
		var email = document.getElementById("email");
		if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email.value)) {
			return (true);
		} else if (/^(\w+ ?)*\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
				.test(email.value)) {
			return (true);
		} else {
			alert("<spring:message code="alertEmail" />");
			return (false);
		}
	}
</script>