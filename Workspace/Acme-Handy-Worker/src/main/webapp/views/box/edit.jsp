<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${possible}">
<form:form action="box/actor/edit.do" modelAttribute="messageBox">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="isPredefined"/>
	<form:hidden path="messages"/>



	<form:label path="name">
		<spring:message code="messageBox.name"/>
	
	</form:label>
	<form:input path="name"/>
	<form:errors	cssClass="error" path="name"	/>
	<br/>
	<br/>
	
	<input type="submit" name="save"	
		value="<spring:message code="messageBox.save"	/>	"	/>&nbsp;
	
	<jstl:if test="${messageBox.id != 0 and empty messages}">
		<input type="submit" name="delete"
			value="<spring:message code="messageBox.delete"/>	"	
			onclick="return confirm('<spring:message code="messageBox.confirm.delete"/>')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="messageBox.cancel"/>" 
		onclick="javascript: relativeRedir('box/actor/list.do');"		/>
	
	<br/>
	
</form:form>



</jstl:if>

<jstl:if test="${!possible}">
<h3>	<spring:message code="messageBox.nopossible"/>	</h3>

</jstl:if>