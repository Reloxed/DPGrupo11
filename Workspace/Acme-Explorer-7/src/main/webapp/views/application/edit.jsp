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

 <security:authorize access="hasRole('MANAGER')" var="principalIsManager"/>

<jstl:choose>
<jstl:when test="${permission && (comment == true || application.status == 'DUE' || principalIsManager)}">
 <security:authorize access="hasRole('EXPLORER')">

<form:form action="${actionURI}" modelAttribute="application">

	<form:hidden path="id" />
	
	<form:hidden path="version" />
	
	<form:hidden path="trip" />	
	
	<form:hidden path="moment" />
	
	<form:hidden path="rejectionReason" />
	
	
	<form:hidden path="status" />
	
	<form:hidden path="applicant"/>
	
	

	<jstl:choose>
	<jstl:when test="${comment == true}">
	
	<form:hidden path="creditcard" />
	
	
	<fieldset>

	
	<legend> <spring:message code="application.comments"/> : </legend>

    <div id="list">
    <jstl:choose> 
	<jstl:when test="${empty application.comments}">
	<div class="list-item">
	 <form:input path="comments[0]"/>
	 <a href="#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="application.comments.remove"/> </a>
	 </div>
	 <br /> 
	</jstl:when>
	<jstl:otherwise>
	<jstl:forEach items="${application.comments}" var="c" varStatus="i" begin="0">
    <div class="list-item">
      <form:input path="comments[${i.index}]"/>
      <a href= "#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="application.comments.remove"/> </a>
	    </div>
            <br />
        </jstl:forEach>
	</jstl:otherwise>
	</jstl:choose>
     
    <a href="#" class="list-add" onclick="event.preventDefault();"> <spring:message code="application.comments.add"/> </a>&nbsp;&nbsp;
    </div>
    
	
	<form:errors cssClass="error" path="comments" />

	</fieldset>
	

	
	<script>
    $(document).ready(function() {
        $("#list").dynamiclist();
    });
</script>
	
	
	
	
	</jstl:when>
	
	<jstl:otherwise>
	
	
	
	
	<form:hidden path="comments" />


	  <fieldset>
	
	
	<legend>     <spring:message code="application.creditCard" />:   </legend>
	
	
	<form:label path="creditcard.holderName">
		<spring:message code="application.creditCard.holderName" />:
	</form:label>
	<form:input path="creditcard.holderName" id="holderName" />
	<form:errors cssClass="error" path="creditcard.holderName" />
	<br />
	
		<form:label path="creditcard.brandName">
		<spring:message code="application.creditCard.brandName" />:
	</form:label>
	<form:input path="creditcard.brandName" id="brandName"/>
	<form:errors cssClass="error" path="creditcard.brandName" />
	<br />
	
	
			<form:label path="creditcard.number">
		<spring:message code="application.creditCard.number" />:
	</form:label>
	<form:input path="creditcard.number" placeholder = "4024007148621138" id="number"/>
	<form:errors cssClass="error" path="creditcard.number" />
	<br />
	
				<form:label path="creditcard.expirationMonth">
		<spring:message code="application.creditCard.expirationMonth" />:
	</form:label>
	<form:input path="creditcard.expirationMonth" placeholder = "08" id="expirationMonth"/>
	<form:errors cssClass="error" path="creditcard.expirationMonth" />
	<br />
	
				<form:label path="creditcard.expirationYear">
		<spring:message code="application.creditCard.expirationYear" />:
	</form:label>
	<form:input path="creditcard.expirationYear" placeholder = "21" id="expirationYear"/>
	<form:errors cssClass="error" path="creditcard.expirationYear" />
	<br />
	
				<form:label path="creditcard.CVV">
		<spring:message code="application.creditCard.CVV" />:
	</form:label>
	<form:input path="creditcard.CVV" placeholder = "422" id="CVV"/>
	<form:errors cssClass="error" path="creditcard.CVV" />
	<br />
	
	</fieldset>
	
	
	</jstl:otherwise>
	</jstl:choose>


	<spring:message code="application.save" var="saveApplication"  />
	<spring:message code="application.cancel" var="cancelApplication"  />
	
	<input type="submit" name="save"
		value="${saveApplication}" />&nbsp; 


	<input type="button" name="cancel"
		value="${cancelApplication}"
		onclick="javascript: relativeRedir('application/explorer/list.do');" />
	<br />

	

</form:form>

<%-- Script para que eliminar de los input la creditcard ficticia --%>
<script type="text/javascript">
	
$(document).ready(function() {
	quitarCreditCardFicticia();
});
	
</script>

</security:authorize>

<security:authorize access="hasRole('MANAGER')">


<form:form action="application/manager/reject.do" modelAttribute="application">

	<form:hidden path="id" />
	
	<form:hidden path="version" />
	
	<form:hidden path="trip" />	
	
	<form:hidden path="moment" />
	
	<form:hidden path="status" />
	
	<form:hidden path="applicant"/>
	
	<form:hidden path="creditcard"/>
	
	<form:hidden path="comments"/>
	
	<form:label path="rejectionReason">
	<spring:message code="application.rejectionReason" />:
	</form:label>
	<br />
	<form:textarea path="rejectionReason" />
	<form:errors cssClass="error" path="rejectionReason" />
	<br />
	<br />
	
	<spring:message code="application.save" var="saveApplication"  />
	<spring:message code="application.cancel" var="cancelApplication"  />
	
	<input type="submit" name="save"
		value="${saveApplication}" />&nbsp; 


	<input type="button" name="cancel"
		value="${cancelApplication}"
		onclick="javascript: relativeRedir('application/manager/list.do');" />
	<br />
	
	

</form:form>


</security:authorize>


</jstl:when>
<jstl:otherwise>

<h3><spring:message code="application.nopermission"  /></h3>

</jstl:otherwise>
</jstl:choose>



