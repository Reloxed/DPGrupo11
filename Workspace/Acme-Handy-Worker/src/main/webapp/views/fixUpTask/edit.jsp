<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<form:form action="fixUpTask/customer/edit.do"
	modelAttribute="fixUpTask" id="form">

	<form:hidden path="ticker" value="000000-AAAAAA"/>
	<form:hidden path="publishedMoment" value="01/01/2001 00:00"/>
	<form:hidden path="applications" />
	<form:hidden path="complaints" />

	<br />

	<form:label path="description">
		<spring:message code="fixUpTask.description" />
	</form:label>
	<spring:message code="fixuptask.description.placeholder"
		var="placeholder" />
	<form:input placeholder="${placeholder}" path="description" />
	<form:errors cssClass="error" path="description" />
	<br />

	<form:label path="address">
		<spring:message code="fixUpTask.address" />
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
	
	<spring:message code="fixUpTask.category" />
	<form:select path="category" style="width:400px;">
		<form:options items="${categories}" itemLabel="name"
			itemValue="id" />
	</form:select>
	
		<br />
		
		<spring:message code="fixUpTask.warranty" />
	<form:select path="warranty" style="width:400px;">
		<form:options items="${warranties}" itemLabel="title"
			itemValue="id" />
	</form:select>

	<br />


<%-- 	<spring:message code="fixuptask.save" var="saveFixUpTask" />
	<spring:message code="fixUpTask.delete" var="deleteFixUpTask" /> --%>
	
	<input type="submit" id="save" name="save" 
		value="<spring:message code="fixuptask.save" />" />


<%-- 	<jstl:if test="${fixUpTask.id != 0}">

		<input type="submit" id="delete" name="delete"
			value="${deleteFixUpTask}" />

	</jstl:if> --%>

	<input type="button" name="cancel" id="cancel"
		onclick="window.history.back()"
		value="<spring:message code="fixuptask.cancel" />" />

</form:form>