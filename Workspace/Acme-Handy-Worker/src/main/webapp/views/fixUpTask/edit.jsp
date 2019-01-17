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

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="ticker"/>
	<form:hidden path="publishedMoment"/>
	<form:hidden path="applications" />
	<form:hidden path="complaints" />

	<br /><br />

	<form:label path="description">
		<spring:message code="fixUpTask.description" />
	</form:label>
	<spring:message code="fixuptask.description.placeholder"
		var="placeholder" />
	<form:input placeholder="${placeholder}" path="description" />
	<form:errors cssClass="error" path="description" />
	<br /><br />

	<form:label path="address">
		<spring:message code="fixUpTask.address" />
	</form:label>
	<spring:message code="fixuptask.address.placeholder" var="placeholder" />
	<form:input placeholder="${placeholder}" path="address" />
	<form:errors cssClass="error" path="address" />
	<br /><br />

	<form:label path="maxPrice">
		<spring:message code="fixuptask.max.price" />
	</form:label>
	<spring:message code="fixuptask.max.price.placeholder"
		var="placeholder" />
	<form:input placeholder="${placeholder}" path="maxPrice" />
	<form:errors cssClass="error" path="maxPrice" />
	<br /><br />
	
	<form:label path="startMoment">
		<spring:message code="fixuptask.start.moment" />
	</form:label>
	<spring:message code="fixuptask.startMoment.placeholder" var="placeholder" />
	<form:input path="startMoment" type="date" placeholder="${placeholder}"/>
	<form:errors cssClass="error" path="startMoment"/>
	
	&#160;

	<form:label path="endMoment">
		<spring:message code="fixuptask.end.moment" />
	</form:label>
	<spring:message code="fixuptask.startMoment.placeholder" var="placeholder" />
	<form:input path="endMoment" type="date" placeholder="${placeholder}"/>
	<form:errors cssClass="error" path="endMoment" />

	<br /><br />
	
	<form:label path="category">	
		<spring:message code="fixUpTask.category" />
	</form:label>
	<form:select path="category" style="width:400px;">
		<form:options items="${categories}" itemLabel="name" itemValue="id" />
	</form:select>
	
		<br /><br />
	<form:label path="warranty">	
		<spring:message code="fixUpTask.warranty" />
	</form:label>
	<form:select path="warranty" style="width:400px;">
		<form:options items="${warranties}" itemLabel="title"
			itemValue="id" />
	</form:select>

	<br /><br />

	
	<input type="submit" id="save" name="save" 
		value="<spring:message code="fixuptask.save" />" />
		
<%-- 	<input type="submit" id="delete" name="delete" 
		value="<spring:message code="fixuptask.delete" />" /> --%>


	<jstl:if test="${empty fixUpTask.applications}">

		<input type="submit" id="delete" name="delete"
			value="<spring:message code="fixuptask.delete" />" />

	</jstl:if>

	<input type="button" name="cancel" id="cancel"
		onclick="window.history.back()"
		value="<spring:message code="fixuptask.cancel" />" />

</form:form>

