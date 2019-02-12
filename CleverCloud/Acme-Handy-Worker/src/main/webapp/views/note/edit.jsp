<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize
	access="hasAnyRole('REFEREE','CUSTOMER','HANDYWORKER')">

	<form:form action="${requestUri}" modelAttribute="note">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<jstl:if test="${note.id == 0}">
			<form:hidden path="publishedMoment"  value="01/01/2001 00:00" />
		</jstl:if>
		<jstl:if test="${note.id != 0 }">
			<form:hidden path="publishedMoment" />
		</jstl:if>
		
		<security:authorize access="hasRole('REFEREE')">
			<form:hidden path="handyWorkerComment" />
			<form:hidden path="customerComment" />
			
			<form:label path="refereeComment">
				<spring:message code="note.comment" />
			</form:label>
			<form:input path="refereeComment" />
			<form:errors cssClass="error" path="refereeComment" />
			<br><br>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<form:hidden path="handyWorkerComment" />
			<form:hidden path="refereeComment" />
			
			<form:label path="customerComment">
				<spring:message code="note.comment" />
			</form:label>
			<form:input path="customerComment" />
			<form:errors cssClass="error" path="customerComment" />
			<br><br>
		</security:authorize> 
		
		<security:authorize access="hasRole('HANDYWORKER')">
			<form:hidden path="refereeComment" />
			<form:hidden path="customerComment" />
			
			<form:label path="handyWorkerComment">
				<spring:message code="note.comment" />
			</form:label>
			<form:input path="handyWorkerComment" />
			<form:errors cssClass="error" path="handyWorkerComment" />
			<br><br>
		</security:authorize>
		
		<form:hidden path="report" />
		<form:errors cssClass="error" path="report" />
		
		<input type="submit" id="submit" name="save" value="<spring:message code="note.save" />" />
		
		<input type="button" name="cancel" value="<spring:message code="note.cancel" />"
				onclick="window.history.back()" />

	</form:form>

</security:authorize>

<script>
	$(document).ready(function() {
		$("#list").dynamiclist();
	});
</script>