<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form modelAttribute="miscellaneousRecord" action="miscellaneousRecord/handyWorker/edit.do"
	id="form">

	<form:hidden path="id" />
		
		<form:hidden path="version" />
		
		
		<fieldset>
		<legend> <spring:message code="mr.miscellaneousRecord" />: </legend>
		
		<form:label path="title">
			<spring:message code="mr.title" />:
		</form:label>
		<form:input path="title"  />
		<form:errors cssClass="error" path="title" />
		<br />
		<br />
		
		
		
		<form:label path="linkAttachment">
			<spring:message code="mr.linkAttachment" />:
		</form:label>
		<form:input path="linkAttachment"  />
		<form:errors cssClass="error" path="linkAttachment" />
		<br />
		<br />
		
		
		<form:label path="comments">
			<spring:message code="pr.comments" />:
		</form:label>
		<form:textarea path="comments"  />
		<form:errors cssClass="error" path="comments" />
		<br />
		<br />
	
	</fieldset>
	
	
	
	
	
	<input type="submit" name="save" id="save" value="<spring:message code="mr.save" />" />&nbsp; 
		<jstl:if test="${miscellaneousRecord.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="mr.delete" />"
				onclick="return confirm('<spring:message code="mr.confirm.delete" />')" />&nbsp;
		</jstl:if>
		<jstl:if test="${curriculum.id!=0}">
		<input type="button" name="cancel" value="<spring:message code="mr.cancel" />"
			onclick="javascript: relativeRedir('/curriculum/handyWorker/display.do');" />
		<br />
		</jstl:if>
		
</form:form>