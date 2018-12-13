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
		<spring:message code="fixuptask.edit.title" />
	</h2>
</div>

<form:form action="fixuptask/edit.do" modelAttribute="fixUpTask"
	id="form">
	<form:hidden path="ticker" />
	<form:hidden path="publishedMoment" />

	<form:label path="description">
		<spring:message code="fixuptask.description" />
	</form:label>
	<spring:message code="fixuptask.description.placeholder"
		var="placeholder" />
	<form:input placeholder="${placeholder}" path="description" />
	<form:errors cssClass="error" path="description" />
	<br />

	<form:label path="address">
		<spring:message code="fixuptask.address" />
	</form:label>
	<spring:message code="fixuptask.address.placeholder" var="placeholder" />
	<form:input placeholder="${placeholder}" path="address" />
	<form:errors cssClass="error" path="address" />
	<br />

	<form:label path="maxPrice">
		<spring:message code="fixuptask.max.price" />
	</form:label>
	<spring:message code="fixuptask.max.price.placeholder"
		var="placeholder" />
	<form:input placeholder="${placeholder}" path="maxPrice" />
	<form:errors cssClass="error" path="maxPrice" />
	<br />

	<form:label path="startMoment">
		<spring:message code="fixuptask.start.moment" />
	</form:label>
	<form:input path="startMoment" />
	<form:errors cssClass="error" path="startMoment" />
	<br />
	
	<form:label path="endMoment">
		<spring:message code="fixuptask.end.moment" />
	</form:label>
	<form:input path="endMoment" />
	<form:errors cssClass="error" path="endMoment" />
	<br />
	
	<form:hidden path="applications" />
	
	<form:label path="category">
		<spring:message code="fixuptask.category" />
	</form:label>
	<spring:message code="fixuptask.category.placeholder"
		var="placeholder" />
	<form:input placeholder="${placeholder}" path="category" />
	<form:errors cssClass="error" path="category" />
	<br />
	
	<form:label path="warranty">
		<spring:message code="fixuptask.warranty" />
	</form:label>
	<spring:message code="fixuptask.warranty.placeholder"
		var="placeholder" />
	<form:input placeholder="${placeholder}" path="warranty" />
	<form:errors cssClass="error" path="warranty" />
	<br />
	
	<form:hidden path="complaints" />
	
	<input type="submit" name="save" id="save"
		value="<spring:message code="fixuptask.save" />" />
	<input type="button" name="cancel" id="cancel"
		onclick="window.history.back()"
		value="<spring:message code="fixuptask.cancel" />" />

</form:form>