<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div style="text-align: center;">
	<h2 style="font-family: sans-serif;">
		<spring:message code="message.box.edit.title" />
	</h2>
</div>

<jstl:if test="${messageBox.id != 0}">
	<form:form action="message-box/edit.do" modelAttribute="messageBox"
		id="editBox">
		<form:label path="messageBox">
			<spring:message code="message.box.edit.name" />
		</form:label>
		<form:input path="name" value="${messageBox.name}" />
		<form:errors cssClass="error" path="messageBox" />
		<br />
	</form:form>
</jstl:if>
<jstl:otherwise>
	<form:form action="message-box/edit.do" modelAttribute="messageBox"
		id="createBox">
		<form:label path="messageBox">
			<spring:message code="message.box.create.name" />
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
	</form:form>
</jstl:otherwise>