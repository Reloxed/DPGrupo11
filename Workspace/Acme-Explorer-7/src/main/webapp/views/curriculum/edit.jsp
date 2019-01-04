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


<jstl:if test="${principal.id == curriculum.ranger.id }" >
<form:form action="curriculum/ranger/edit.do" modelAttribute="curriculum" id="form">

	<form:hidden path="id" />
	
	<form:hidden path="version" />
	
	<form:hidden path="ranger"/>
	
	<form:hidden path ="ticker" />
	
	<form:hidden path ="professionalRecords" />
	
	<form:hidden path ="educationRecord" />
	
	<form:hidden path ="endorserRecord" />
	
	<form:hidden path ="miscellaneousRecord" />
	
	
	<%-- Parte del formulario para el personalRecord --%>
	<fieldset>
	<legend><form:label path="personalRecord"> <spring:message code="curriculum.personalRecord" />: </form:label></legend>
	
	<form:label path="personalRecord.name">
		<spring:message code="curriculum.personalRecord.name" />:
	</form:label>
	<form:input path="personalRecord.name"  />
	<form:errors cssClass="error" path="personalRecord.name" />
	<br />
	<br />
	
	<form:label path="personalRecord.surname">
		<spring:message code="curriculum.personalRecord.surname" />:
	</form:label>
	<form:input path="personalRecord.surname"  />
	<form:errors cssClass="error" path="personalRecord.surname" />
	<br />
	<br />
	
	<form:label path="personalRecord.linkPhoto">
		<spring:message code="curriculum.personalRecord.linkPhoto" />:
	</form:label>
	<spring:message code="curriculum.personalRecord.linkPhotoPlaceholder" var="linkPhotoPlaceholder"/>
	<form:input path="personalRecord.linkPhoto" placeholder="${linkPhotoPlaceholder }" size="25" />
	<form:errors cssClass="error" path="personalRecord.linkPhoto" />
	<br />
	<br />
	
	<form:label path="personalRecord.email">
		<spring:message code="curriculum.personalRecord.email" />:
	</form:label>
	<form:input path="personalRecord.email"  />
	<form:errors cssClass="error" path="personalRecord.email" />
	<br />
	<br />
	
	<spring:message code="curriculum.phonePlaceholder" var="placeholder" />
	<form:label path="personalRecord.phone">
		<spring:message code="curriculum.personalRecord.phone" />:
	</form:label>
	<form:input path="personalRecord.phone"  id="phone" placeholder="${placeholder }" size="30"/>
	<form:errors cssClass="error" path="personalRecord.phone" />
	<br />
	<br />
	
	<form:label path="personalRecord.linkedInProfile">
		<spring:message code="curriculum.personalRecord.linkedInProfile" />:
	</form:label>
	<form:input path="personalRecord.linkedInProfile"  />
	<form:errors cssClass="error" path="personalRecord.linkedInProfile" />
	<br />
	<br />
	</fieldset>
	<br>
	<br>
	
	




<input type="button" name="save" id="save" value="<spring:message code="curriculum.save" />" />&nbsp; 
	<jstl:if test="${curriculum.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="curriculum.delete" />"
			onclick="return confirm('<spring:message code="curriculum.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<jstl:if test="${curriculum.id!=0}">
	<input type="button" name="cancel" value="<spring:message code="curriculum.cancel" />"
		onclick="javascript: relativeRedir('/curriculum/ranger/display.do');" />
	<br />
	</jstl:if>
	<jstl:if test="${curriculum.id==0}">
	<input type="button" name="cancel" value="<spring:message code="curriculum.cancel" />"
		onclick="javascript: relativeRedir('/welcome/index.do');" />
	<br />
	</jstl:if>

</form:form>

<script type="text/javascript">
$("#save").on("click",function(){validatePhone("<spring:message code='curriculum.confirmationPhone'/>","${countryCode}");});


	 
</script>

</jstl:if>

<jstl:if test="${principal.id != curriculum.ranger.id }" >
<h3><spring:message code="curriculum.nopermisiontobehere" /></h3>
</jstl:if>









