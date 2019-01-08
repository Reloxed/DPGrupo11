<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form modelAttribute="endorserRecord" action="endorserRecord/handyWorker/edit.do"
	id="form">

	<form:hidden path="id" />
		
		<form:hidden path="version" />
		
		
		<fieldset>
		<legend> <spring:message code="enr.endorserRecord" />: </legend>
		
		<form:label path="fullName">
			<spring:message code="enr.full.name" />:
		</form:label>
		<form:input path="fullName"  />
		<form:errors cssClass="error" path="fullName" />
		<br />
		<br />
		
		
		<form:label path="email">
			<spring:message code="enr.email" />:
		</form:label>
		<form:input path="email"  />
		<form:errors cssClass="error" path="email" />
		<br />
		<br />
		
		<form:label path="phoneNumber">
			<spring:message code="enr.phoneNumber" />:
		</form:label>
		<spring:message code="enr.phoneNumberPlaceholder" var="placeholder" />
		<form:input path="phoneNumber"  id="phoneNumber" placeholder="${placeholder }" size="30"/>
		<form:errors cssClass="error" path="phoneNumber" />
		<br />
		<br />
		
		
		<form:label path="linkedinLink">
			<spring:message code="enr.linkedInProfile" />:
		</form:label>
		<form:input path="linkedinLink"  />
		<form:errors cssClass="error" path="linkedinLink" />
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
	<input type="button" name="save" id="save" value="<spring:message code="enr.save" />" />&nbsp; 
		<jstl:if test="${endorserRecord.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="enr.delete" />"
				onclick="return confirm('<spring:message code="enr.confirm.delete" />')" />&nbsp;
		</jstl:if>
		<jstl:if test="${curriculum.id!=0}">
		<input type="button" name="cancel" value="<spring:message code="enr.cancel" />"
			onclick="javascript: relativeRedir('/curriculum/handyWorker/display.do');" />
		<br />
		</jstl:if>
		
		
</form:form>

<script type="text/javascript">
$("#save").on("click",function(){validatephoneNumber("<spring:message code='enr.confirmationphoneNumber'/>","${countryCode}");});
</script>

