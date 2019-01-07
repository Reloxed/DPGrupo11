<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jstl:choose>
<jstl:when test="${report.id == 0 || report.isFinal == false}">

<form:form action="report/referee/edit.do" modelAttribute="report">

	<form:hidden path="id" />
	<form:hidden path="version"/>
	<form:hidden path="complaint"/>	
	<form:hidden path="publishedMoment"/>	
	<form:hidden path="notes"/>	
	
	<form:label path="description">
		<spring:message code="report.description"/>
	</form:label>
	<form:textarea path="description"/>
	<form:errors cssClass="error" path="description"/>
	<br/>
	<br/>
	
	<fieldset>
	
	<legend> <form:label path="attachments"> <spring:message code="report.attachments"/> : </form:label>  </legend>
	<div id="list">
	<jstl:choose>
	<jstl:when test="${empty report.attachments}">
	<div class="list-item">
		<form:input path="attachments"/>
		<a href="#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="report.attachment.remove"/></a>
	</div>
	<br/>
	
	</jstl:when>
	<jstl:otherwise>
	<jstl:forEach items="${report.attachments}" var="attachment" varStatus="i" begin="0">
	<div class="list-item">
		<form:input path="attachments"/>
		<a href="#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="report.attachments.remove"/></a>
		
	</div>
	
	
	</jstl:forEach>
	</jstl:otherwise>
		
	</jstl:choose>
	<a href="#" class="list-add" onclick="event.preventDefault();"> <spring:message code="report.attachment.add"/></a> &nbsp;&nbsp;
	</div>
	
	<form:errors cssClass="error" path="attachments"/>
	
	</fieldset>
	
	<br/>
	<br/>
	

<spring:message code="report.save.final" var="saveReportFinal"/>
<spring:message code="report.delete" var="deleteReport"/>
<spring:message code="report.confirm.delete" var="confirmDeleteReport"/>
<spring:message code="report.cancel" var="cancelReport"/>

	<jstl:if test="${report.isFinal == false}">
	
		<input type="submit" id="sumbit" name="saveFinal"
			value="${saveReportFinal}"/>&nbsp;
	</jstl:if>
	<jstl:if test="${report.id != 0}">
	
		<input type="submit" name="delete"
			value="${deleteReport}" 
			onclick="return confirm('${confirmDeleteReport}')"/>&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="${cancelReport}"
		onclick="javascript: relativeRedir('report/referee/list.do)');"/>
	<br/>
	
	
	
</form:form>

<script>
    $(document).ready(function() {
        $("#list").dynamiclist();
    });
</script>

</jstl:when>
<jstl:otherwise>
<h3> <spring:message code="report.nopermission"/> </h3>
</jstl:otherwise>
</jstl:choose>