<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CUSTOMER')">

<form:form action="complaint/customer/edit.do" modelAttribute="complaint"
	id="form">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" value="000000-AAAAAA"/>
	<form:hidden path="moment" value="01/01/2001 00:00"/>
	
	<form:label path="fixUpTask">
		<spring:message code="complaint.fixuptask" />
	</form:label>
	<form:select path="fixUpTask">
		<jstl:forEach var="x" items="${listFixUpTasks}">
			<form:option value="${x}">
				<jstl:out value="${x.description}" />
			</form:option>
		</jstl:forEach>
	</form:select>
	<form:errors cssClass="error" path="fixUpTask" />

	<br />
	<br />
	
	<form:label path="description">
		<spring:message code="complaint.description" />
	</form:label>
	<br/>
	<spring:message code="complaint.description.placeholder"
		var="placeholder" />
	<form:textarea rows="4" cols="60" path="description" placeholder="${placeholder}" />
	<form:errors cssClass="error" path="description" />
	
	<br />
	<br />

	<form:label path="attachments">
		<spring:message code="complaint.attachments" />
	</form:label>
	<spring:message code="complaint.attachmentsPlaceholder"
		var="placeholder" />
	<form:textarea rows="4" cols="60" path="attachments" placeholder="${placeholder}" />
	<form:errors cssClass="error" path="attachments" />

	<br />
	<br />
	
	<input type="submit" name="save" id="save"
		value="<spring:message code="complaint.save"/>" />
	<input type="reset" name="reset" id="reset"
		value=" <spring:message code="complaint.reset"/>" />
	<input type="button" name="cancel" id="cancel"
		value=" <spring:message code="complaint.cancel"/>"
		onclick="window.history.back()" />

</form:form>

</security:authorize>