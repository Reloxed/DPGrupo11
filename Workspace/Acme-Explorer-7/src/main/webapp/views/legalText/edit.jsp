<%--
 * 
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jstl:choose>
<jstl:when test="${legalText.isDraft == false}">
<h3><spring:message code="legalText.nopermission" /></h3>
</jstl:when>
<jstl:otherwise>


<form:form action="legalText/administrator/edit.do" modelAttribute="legalText"  id="legalTextForm">

	<form:hidden path="id" />
	
	<form:hidden path="version" />
	
	
	<form:hidden path="registerDate" />

	<form:label path="title">
		<spring:message code="legalText.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	<br />

	<form:label path="body">
		<spring:message code="legalText.body" />:
	</form:label>
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body" />
	<br />
	<br />
	
	
	<fieldset>

	
	<legend> <spring:message code="legalText.laws"/> : </legend>

	
	
	
	
    <div id="list">
    <jstl:choose> 
	<jstl:when test="${empty legalText.laws}">
	<div class="list-item">
	 <form:input path="laws[0]"/>
	 <a href="#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="legalText.laws.remove"/> </a>
	 </div>
	 <br /> 
	</jstl:when>
	<jstl:otherwise>
	<jstl:forEach items="${legalText.laws}" var="law" varStatus="i" begin="0">
    <div class="list-item">
      <form:input path="laws[${i.index}]"/>
      <a href= "#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="legalText.laws.remove"/> </a>
	    </div>
            <br />
        </jstl:forEach>
	</jstl:otherwise>
	</jstl:choose>
     
    <a href="#" class="list-add" onclick="event.preventDefault();"> <spring:message code="legalText.laws.add"/> </a>&nbsp;&nbsp;
    </div>
    
	
	<form:errors cssClass="error" path="laws" />

	</fieldset>
	
	<br />
	
	
	<spring:message code="legalText.save.draft" var="saveLegalTextDraft"  />
	<spring:message code="legalText.save.final" var="saveLegalTextFinal"  />
	<spring:message code="legalText.delete" var="deleteLegalText"  />
	<spring:message code="legalText.confirm.delete" var="confirmDeleteLegalText"  />
	<spring:message code="legalText.cancel" var="cancelLegalText"  />
	
	<input type="submit" id="submit" name="saveDraft"
		value="${saveLegalTextDraft}" />&nbsp; 
		
	<input type="submit" name="saveFinal"
		value="${saveLegalTextFinal}" />&nbsp; 
		
	<jstl:if test="${legalText.id != 0}">
		<input type="submit" name="delete"
			value="${deleteLegalText}"
			onclick="return confirm('${confirmDeleteLegalText}')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="${cancelLegalText}"
		onclick="javascript: relativeRedir('legalText/administrator/list.do');" />
	<br />


</form:form>


<script>
    $(document).ready(function() {
        $("#list").dynamiclist();
    });
</script>

</jstl:otherwise>
</jstl:choose>

