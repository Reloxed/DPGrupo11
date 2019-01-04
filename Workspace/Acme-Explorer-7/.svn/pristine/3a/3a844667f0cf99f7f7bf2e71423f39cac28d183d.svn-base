

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:choose>
<jstl:when test="${replying}">

<jstl:if test="${permission}" >

<form:form action="note/manager/reply.do" modelAttribute="note">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="trip" />
	<form:hidden path="auditor" />
	<form:hidden path="remark" />
	

	
	<form:textarea path="remark" disabled="true"/>
	<br/>
	<br/>	



	<form:label path="managerReply">
		<spring:message code="note.managerReply" />:
	</form:label>
	<form:textarea path="managerReply"  />
	<form:errors cssClass="error" path="managerReply" />
	<br />
	<br />


	

	<spring:message code="note.save" var="saveNote"  />
	<spring:message code="note.cancel" var="cancelNote"  />
	
	
	<input type="submit" name="save"
		value="${saveNote}" />&nbsp; 
	
	
	<input type="button" name="cancel"
		value="${cancelNote}"
		onclick="javascript: relativeRedir('note/manager/list.do');" />
	<br />

</form:form>

</jstl:if>
<jstl:if test="${!permission }">
<h3><spring:message code="note.nopermission" /></h3>
</jstl:if>

</jstl:when>
<jstl:otherwise>
<jstl:if test="${permission}" >



<form:form action="note/auditor/edit.do" modelAttribute="note">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	<form:hidden path="auditor" />
	<form:hidden path="trip" />
	

	<form:label path="remark">
		<spring:message code="note.remark" />:
	</form:label>
	<form:textarea path="remark" />
	<form:errors cssClass="error" path="remark" />
	<br />
	<br />


	

	<spring:message code="note.save" var="saveNote"  />
	<spring:message code="note.cancel" var="cancelNote"  />
	
	
	<input type="submit" name="save"
		value="${saveNote}" />&nbsp; 
	
	
	<input type="button" name="cancel"
		value="${cancelNote}"
		onclick="javascript: relativeRedir('note/auditor/list.do');" />
	<br />

</form:form>

</jstl:if>

<jstl:if test="${!permission }">
<h3><spring:message code="note.nopermission" /></h3>
</jstl:if>

</jstl:otherwise>
</jstl:choose>


