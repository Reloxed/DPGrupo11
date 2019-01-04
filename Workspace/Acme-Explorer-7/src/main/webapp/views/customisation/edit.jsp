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



<form:form action="customisation/administrator/edit.do" modelAttribute="customisation">

	<form:hidden path="id" />
	
	<form:hidden path="version" />
	
	


	<form:label path="welcomeBanner">
		<spring:message code="customisation.welcomeBanner" />:
	</form:label>
	<form:input path="welcomeBanner" />
	<form:errors cssClass="error" path="welcomeBanner" />
	<br />
	<br />

	<form:label path="welcomeMessageEn">
		<spring:message code="customisation.welcomeMessageEn" />:
	</form:label>
	<form:textarea path="welcomeMessageEn" />
	<form:errors cssClass="error" path="welcomeMessageEn" />
	<br />
	<br />


	<form:label path="welcomeMessageEs">
		<spring:message code="customisation.welcomeMessageEs" />:
	</form:label>
	<form:textarea path="welcomeMessageEs" />
	<form:errors cssClass="error" path="welcomeMessageEs" />
	<br />
	<br />
	
	<form:label path="countryCode">
		<spring:message code="customisation.countryCode" />:
	</form:label>
	<form:input path="countryCode" />
	<form:errors cssClass="error" path="countryCode" />
	<br />
	<br />
	
	
	<fieldset>

	
	<legend> <form:label path="spamWords"> <spring:message code="customisation.spamWords"/> : </form:label> </legend>
	
    <div id="list1">
    <jstl:choose> 
	<jstl:when test="${empty customisation.spamWords}">
	<div class="list-item">
	 <form:input path="spamWords[0]"/>
	 <a href="#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="customisation.spamWords.remove"/> </a>
	 </div>
	 <br /> 
	</jstl:when>
	<jstl:otherwise>
	<jstl:forEach items="${customisation.spamWords}" var="word" varStatus="j" begin="0">
    <div class="list-item">
      <form:input path="spamWords[${j.index}]"/>
      <a href= "#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="customisation.spamWords.remove"/> </a>
	    </div>
            <br />
        </jstl:forEach>
	</jstl:otherwise>
	</jstl:choose>
     
    <a href="#" class="list-add" onclick="event.preventDefault();"> <spring:message code="customisation.spamWords.add"/> </a>&nbsp;&nbsp;
    </div>
    
	
	<form:errors cssClass="error" path="spamWords" />

	</fieldset>
	
	<br />
	<br />
	
	
	
	<form:label path="vatTax">
		<spring:message code="customisation.vatTax" />:
	</form:label>
	<form:input path="vatTax" />
	<form:errors cssClass="error" path="vatTax" />
	<br />
	<br />
	
		<form:label path="finderCacheTime">
		<spring:message code="customisation.finderCacheTime" />:
	</form:label>
	<form:input path="finderCacheTime" />
	<form:errors cssClass="error" path="finderCacheTime" />
	<br />
	<br />
	


	<form:label path="finderMaxResults">
		<spring:message code="customisation.finderMaxResults" />:
	</form:label>
	<form:input path="finderMaxResults" />
	<form:errors cssClass="error" path="finderMaxResults" />
	<br />
	<br />
	
	
	
	<spring:message code="customisation.save" var="saveCustomisation"  />
	<spring:message code="customisation.cancel" var="cancelCustomisation"  />
	
	<input type="submit" name="save"
		value="${saveCustomisation}" />&nbsp; 
				
	
	<input type="button" name="cancel"
		value="${cancelCustomisation}"
		onclick="javascript: relativeRedir('customisation/administrator/display.do');" />
	<br />


</form:form>

<script>
    $(document).ready(function() {
        $("#list1").dynamiclist();
    });
</script>


