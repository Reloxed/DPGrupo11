

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jstl:if test="${permission }">

<jstl:choose>
<jstl:when test="${audit.id == 0 || audit.isDraft == true}">


<form:form action="audit/auditor/edit.do" modelAttribute="audit">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="auditor" />
	<form:hidden path="moment" />
	<form:hidden path="trip" />


	<form:label path="title">
		<spring:message code="audit.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	<br />



	<form:label path="description">
		<spring:message code="audit.description" />:
	</form:label>
	<form:textarea path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	<br />
	
	
	<fieldset>

	
	<legend> <form:label path="linkAttachment"> <spring:message code="audit.linkAttachment"/> :  </form:label> </legend>
	
    <div id="list">
    <jstl:choose> 
	<jstl:when test="${empty audit.linkAttachment}">
	<div class="list-item">
	 <form:input path="linkAttachment[0]"/>
	 <a href="#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="audit.linkAttachment.remove"/> </a>
	 </div>
	 <br /> 
	</jstl:when>
	<jstl:otherwise>
	<jstl:forEach items="${audit.linkAttachment}" var="link" varStatus="i" begin="0">
    <div class="list-item">
      <form:input path="linkAttachment[${i.index}]"/>
      <a href= "#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="audit.linkAttachment.remove"/> </a>
	    </div>
            <br />
        </jstl:forEach>
	</jstl:otherwise>
	</jstl:choose>
     
    <a href="#" class="list-add" onclick="event.preventDefault();"> <spring:message code="audit.linkAttachment.add"/> </a>&nbsp;&nbsp;
    </div>
    
	
	<form:errors cssClass="error" path="linkAttachment" />

	</fieldset>
	
	
	
	
	<br/>
	<br/>
	
	



<spring:message code="audit.save.draft" var="saveAuditDraft"  />
	<spring:message code="audit.save.final" var="saveAuditFinal"  />
	<spring:message code="audit.delete" var="deleteAudit"  />
	<spring:message code="audit.confirm.delete" var="confirmDeleteAudit"  />
	<spring:message code="audit.cancel" var="cancelAudit"  />
	
	<input type="submit" id="submit" name="saveDraft"
		value="${saveAuditDraft}" />&nbsp; 
		
	<input type="submit" name="saveFinal"
		value="${saveAuditFinal}" />&nbsp; 
		
	<jstl:if test="${audit.id != 0}">
		<input type="submit" name="delete"
			value="${deleteAudit}"
			onclick="return confirm('${confirmDeleteAudit}')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="${cancelAudit}"
		onclick="javascript: relativeRedir('audit/auditor/list.do');" />
	<br />

	

</form:form>


<script>
    $(document).ready(function() {
        $("#list").dynamiclist();
    });
</script>

</jstl:when>
<jstl:otherwise>
<h3><spring:message code="audit.nopermission.2"   /></h3>
</jstl:otherwise>
</jstl:choose>

</jstl:if>

<jstl:if test="${!permission }">
<h3><spring:message code="audit.nopermission"   /></h3>
</jstl:if>
