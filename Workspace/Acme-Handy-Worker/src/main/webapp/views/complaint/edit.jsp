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
		<spring:message code="complaint.edit" />
	</h2>
</div>

<form:form action="complaint/edit.do" modelAttribute="complaint"
	id="form">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	<form:hidden path="moment" />

	<form:label path="fixUpTask">
		<spring:message code="complaint.fixuptask" />
	</form:label>
	<jstl:set var="listFixUpTask" value="" />
	<form:select path="fixUpTask">
		<jstl:forEach var="x" items="${listFixUpTask}">
			<form:option value="${x.description}">
				<jstl:out value="${x.description}" />
			</form:option>
		</jstl:forEach>
	</form:select>

	<br />

	<form:label path="attachement">
		<spring:message code="complaint.attachements" />
	</form:label>
	<spring:message code="complaint.attachmentPlaceholder"
		var="placeholder" />
	<form:input path="attachements" placeholder="${placeholder}" />
	<form:errors cssClass="error" path="attachments" />

	<br />

	<form:button type="submit" name="save" id="save"
		value="<spring:message code="complaint.save"/>" />
	<form:button type="reset" name="reset" id="reset"
		value="<spring:message code="complaint.reset"/>" />
	<form:button type="cancel" name="cancel" id="cancel"
		value="<spring:message code="complaint.cancel"/>"
		onclick="window.history.back()" />

</form:form>