<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="professionalRecord/handyWorker/edit.do" modelAttribute="professionalRecord" id="form">
	
		<form:hidden path="id" />
		
		<form:hidden path="version" />
		
		
		<fieldset>
		<legend> <spring:message code="pr.professionalRecord" />: </legend>
		
		<form:label path="companyName">
			<spring:message code="pr.companyName" />:
		</form:label>
		<form:input path="companyName"  />
		<form:errors cssClass="error" path="companyName" />
		<br />
		<br />
		
		<form:label path="startDate">
			<spring:message code="pr.startDate" />:
		</form:label>
		<form:input path="startDate" placeholder="dd/MM/yyyy" />
		<form:errors cssClass="error" path="startDate" />
		<br />
		<br />
		
		<form:label path="endDate">
			<spring:message code="pr.endDate" />:
		</form:label>
		<form:input path="endDate"  placeholder="dd/MM/yyyy"/>
		<form:errors cssClass="error" path="endDate" />
		<br />
		<br />
		
		<form:label path="role">
			<spring:message code="pr.role" />:
		</form:label>
		<form:input path="role"  />
		<form:errors cssClass="error" path="role" />
		<br />
		<br />
		
		
		<form:label path="attachment">
			<spring:message code="pr.linkAttachment" />:
		</form:label>
		<form:input path="attachment"  />
		<form:errors cssClass="error" path="attachment" />
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
		<input type="submit" name="save" id="save" value="<spring:message code="pr.save" />" />&nbsp; 
		<jstl:if test="${professionalRecord.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="pr.delete" />"
				onclick="return confirm('<spring:message code="pr.confirm.delete" />')" />&nbsp;
		</jstl:if>
		<jstl:if test="${curriculum.id!=0}">
		<input type="button" name="cancel" value="<spring:message code="pr.cancel" />"
			onclick="javascript: relativeRedir('/curriculum/handyWorker/display.do');" />
		<br />
		</jstl:if>
		
</form:form>