<%--
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jstl:if test="${permission }">



<form:form action="${actionLink}" modelAttribute="trip">



	<form:hidden path="id" />

	<form:hidden path="version" />
	
	<form:hidden path="ticker" />
	<form:hidden path="price" />
	
    <form:hidden path="audits"/>
	<form:hidden path="sponsorships"/>
	<form:hidden path="applications"/>
	<form:hidden path="stories"/>
	<form:hidden path="notes"/>
	<form:hidden path="survivalClasses"/>
	
	<form:hidden path="manager" />


	<jstl:choose>
	<jstl:when test="${cancel == false}">



	<form:label path="title">
		<spring:message code="trip.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	<br />

	<form:label path="description">
		<spring:message code="trip.description" />:
	</form:label>
	<form:textarea path="description" />
	<form:errors cssClass="error" path="description" />
		<br />
		<br />


	<fieldset>

	
	<legend> <spring:message code="trip.requirements"/> : </legend>
	
    <div id="list1">
    <jstl:choose> 
	<jstl:when test="${empty trip.requirements}">
	<div class="list-item">
	 <form:input path="requirements[0]" />
	 <a href="#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="trip.requirements.remove"/> </a>
	 </div>
	 <br /> 
	</jstl:when>
	<jstl:otherwise>
	<jstl:forEach items="${trip.requirements}" var="requirement" varStatus="j" begin="0">
    <div class="list-item">
      <form:input path="requirements[${j.index}]"/>
      <a href= "#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="trip.requirements.remove"/> </a>
	    </div>
            <br />
        </jstl:forEach>
	</jstl:otherwise>
	</jstl:choose>
     
    <a href="#" class="list-add" onclick="event.preventDefault();"> <spring:message code="trip.requirements.add"/> </a>&nbsp;&nbsp;
    </div>
    
	
	<form:errors cssClass="error" path="requirements" />

	</fieldset>
	
	<br />
	


	<form:label path="publicationDate">
		<spring:message code="trip.publicationDate" />:
	</form:label>
	<form:input path="publicationDate" placeholder="dd/MM/yyyy" />
	<form:errors cssClass="error" path="publicationDate" />
	<br />
	<br />

	<form:label path="startDate">
		<spring:message code="trip.startDate" />:
	</form:label>
	<form:input path="startDate" placeholder="dd/MM/yyyy" />
	<form:errors cssClass="error" path="startDate" />
	<br />
	<br />
	
	
	<form:label path="endDate">
		<spring:message code="trip.endDate" />:
	</form:label>
	<form:input path="endDate" placeholder="dd/MM/yyyy" />
	<form:errors cssClass="error" path="endDate" />
	<br />
	<br />


<fieldset>

	
	<legend> <form:label path="stage"> <spring:message code="trip.stages" />: </form:label> </legend>



		<div id="list2">
		<table class="displayStyle">
			<tr>
			<th>  <spring:message code="trip.stage.title" /> :  </th>
			<th>  <spring:message code="trip.stage.description" /> : </th>
			<th>  <spring:message code="trip.stage.price" /> : </th>
			<th> </th>
			 </tr>
			
			<jstl:choose> 
			<jstl:when test="${empty trip.stage}">
			<tr class="list-item">
			
			
			
			<td>  <input type="hidden" name="stage[0].number"  value="0"/>	<form:input path="stage[0].title" /> </td>
			<td>	<form:textarea path="stage[0].description" /> </td>
			<td>	<form:input path="stage[0].price" /> </td>
			<td>	<a href="#" onclick="event.preventDefault();"
					class="list-remove"> <spring:message code="trip.stage.remove" /> </a> </td>
			</tr>
			
			</jstl:when>
			<jstl:otherwise>
			<jstl:forEach items="${trip.stage}" var="stg" varStatus="i" begin="0">
   			 <tr class="list-item">
			<td> <input type="hidden" name="stage[${i.index}].number"  value="0"/>	<form:input path="stage[${i.index}].title" /> </td>
			<td>	<form:textarea path="stage[${i.index}].description" /> </td>
			<td>	<form:input path="stage[${i.index}].price" /> </td>
     		<td>	<a href="#" onclick="event.preventDefault();"
					class="list-remove"> <spring:message code="trip.stage.remove" /> </a> </td>
	    </tr>
            <br />
        </jstl:forEach>
			</jstl:otherwise>
			</jstl:choose>
			
		</table>
		<a href="#" onclick="event.preventDefault();" class="list-add"><spring:message code="trip.stage.add" /></a>
		</div>
		<br />
		<form:errors cssClass="error" path="stage" />

</fieldset>
	
	<br />
	<br />
	
	<form:label path="ranger">
		<spring:message code="trip.ranger" />:
	</form:label>
	<form:select path="ranger">
		<form:options items="${rangers}" itemValue="id" itemLabel="name" />
	</form:select>
	<form:errors cssClass="error" path="ranger" />
	<br />
	<br />
	
	
	
	<form:label path="category">
		<spring:message code="trip.category" />:
	</form:label>
	<form:select path="category">
		<form:options items="${categories}" itemValue="id" itemLabel="name" />
	</form:select>
	<form:errors cssClass="error" path="category" />
	<br />
	<br />
	

	<form:label path="tags">
		<spring:message code="trip.tags" />:
	</form:label>
	<form:select multiple="true" path="tags">
		<form:option value="" label="----" />
		<form:options items="${tags}" itemValue="id" itemLabel="name" />
	</form:select>
	<form:errors cssClass="error" path="tags" />
	<br />
	<br />

	<form:label path="legaltext">
		<spring:message code="trip.legaltext" />:
	</form:label>
	<form:select path="legaltext">
		<form:options items="${legalTexts}" itemValue="id" itemLabel="title" />
	</form:select>
	<form:errors cssClass="error" path="legaltext" />
	<br />
	<br />
	
	
	
	
	
	<br />
	
	
	</jstl:when>
	<jstl:otherwise>
	
	
	<form:hidden path="title" />
	<form:hidden path="description" />
	<form:hidden path="requirements" />
	<form:hidden path="stage" />
	<form:hidden path="category" />
	<form:hidden path="startDate" />
	<form:hidden path="endDate" />
	<form:hidden path="publicationDate" />
	<form:hidden path="tags" />
	<form:hidden path="legaltext" />
	<form:hidden path="ranger" />
	
	
	
	
	<form:label path="cancellationReason">
	<spring:message code="trip.cancellationReason.placeholder" />:
	</form:label>
	<br/>
	<form:textarea path="cancellationReason" />
	<form:errors cssClass="error" path="cancellationReason" />
	
	<br />
	
	</jstl:otherwise>
	</jstl:choose>
	
	
	
	
	<spring:message code="trip.save" var="saveTrip" />
	<spring:message code="trip.delete" var="deleteTrip" />
	<spring:message code="trip.confirm.delete" var="confirmDeleteTrip" />
	<spring:message code="trip.cancel" var="cancelTrip" />

	<input type="submit" name="save" value="${saveTrip}" />&nbsp; 
	<jstl:if test="${trip.id != 0 && cancel == false}">
		<input type="submit" name="delete" value="${deleteTrip}"
			onclick="return confirm('${confirmDeleteTrip}')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel" value="${cancelTrip}"
		onclick="javascript: relativeRedir('trip/manager/list.do');" />
	<br />








</form:form>


<script>
    $(document).ready(function() {
        $("#list1").dynamiclist();
    });
</script>

<script>
    $(document).ready(function() {
        $("#list2").dynamiclist();
    });
</script>

<div> <jstl:out value="${mensaje}" /> </div>

</jstl:if>
<jstl:if test="${!permission }">
<h3><spring:message code="trip.nopermission"  /></h3>
</jstl:if>

