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


<jstl:if test="${permission }">
<form:form action="story/explorer/edit.do" modelAttribute="story">

	<form:hidden path="id" />
	
	<form:hidden path="version" />
	
	<form:hidden path="explorer"/>
	
	<form:hidden path="trip"/>


	<form:label path="title">
		<spring:message code="story.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
<br/>

	<form:label path="text">
		<spring:message code="story.text" />:
	</form:label>
	<form:textarea path="text"/>
	<form:errors cssClass="error" path="text" />
	<br />
	<br/>
	
	<fieldset>

	
	<legend> <form:label path="linkAttachment"> <spring:message code="story.linkAttachment"/> :  </form:label> </legend>
	
    <div id="list">
    <jstl:choose> 
	<jstl:when test="${empty story.linkAttachment}">
	<div class="list-item">
	 <form:input path="linkAttachment[0]"/>
	 <a href="#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="story.linkAttachment.remove"/> </a>
	 </div>
	 <br /> 
	</jstl:when>
	<jstl:otherwise>
	<jstl:forEach items="${story.linkAttachment}" var="link" varStatus="i" begin="0">
    <div class="list-item">
      <form:input path="linkAttachment[${i.index}]"/>
      <a href= "#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="story.linkAttachment.remove"/> </a>
	    </div>
            <br />
        </jstl:forEach>
	</jstl:otherwise>
	</jstl:choose>
     
    <a href="#" class="list-add" onclick="event.preventDefault();"> <spring:message code="story.linkAttachment.add"/> </a>&nbsp;&nbsp;
    </div>
    
	
	<form:errors cssClass="error" path="linkAttachment" />

	</fieldset>
	
	
	
	
	<br/>
	<br/>
	
	
	
	
	<spring:message code="story.save" var="saveStory"  />
	<spring:message code="story.delete" var="deleteStory"  />
	<spring:message code="story.confirm.delete" var="confirmDeleteStory"  />
	<spring:message code="story.cancel" var="cancelStory"  />
	
	<input type="submit" name="save"
		value="${saveStory}" />&nbsp; 
	<jstl:if test="${story.id != 0}">
		<input type="submit" name="delete"
			value="${deleteStory}"
			onclick="return confirm('${confirmDeleteStory}')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="${cancelStory}"
		onclick="javascript: relativeRedir('story/explorer/list.do');" />
	<br />

	
	

</form:form>

<script>
    $(document).ready(function() {
        $("#list").dynamiclist();
    });
</script>

</jstl:if>

<jstl:if test="${!permission }">
<h3><spring:message code="story.nopermission"  /></h3>
</jstl:if>
