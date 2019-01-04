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


<jstl:if test="${permission}">
	<form:form action="endorserRecord/ranger/edit.do" modelAttribute="endorserRecord" id="form">
	
		<form:hidden path="id" />
		
		<form:hidden path="version" />
		
		
		<fieldset>
		<legend> <spring:message code="enr.endorserRecord" />: </legend>
		
		<form:label path="name">
			<spring:message code="enr.name" />:
		</form:label>
		<form:input path="name"  />
		<form:errors cssClass="error" path="name" />
		<br />
		<br />
		
		<form:label path="surname">
			<spring:message code="enr.surname" />:
		</form:label>
		<form:input path="surname"  />
		<form:errors cssClass="error" path="surname" />
		<br />
		<br />
		
		<form:label path="email">
			<spring:message code="enr.email" />:
		</form:label>
		<form:input path="email"  />
		<form:errors cssClass="error" path="email" />
		<br />
		<br />
		
		<form:label path="phone">
			<spring:message code="enr.phone" />:
		</form:label>
		<spring:message code="enr.phonePlaceholder" var="placeholder" />
		<form:input path="phone"  id="phone" placeholder="${placeholder }" size="30"/>
		<form:errors cssClass="error" path="phone" />
		<br />
		<br />
		
		
		<form:label path="linkedInProfile">
			<spring:message code="enr.linkedInProfile" />:
		</form:label>
		<form:input path="linkedInProfile"  />
		<form:errors cssClass="error" path="linkedInProfile" />
		<br />
		<br />
		
		
		<%-- Lista din�mica para los comments --%>
		
		<fieldset style="width: 40%;">

	
	<legend> <form:label path="comments"> <spring:message code="enr.comments" />: </form:label> </legend>



		<div id="list1">
		
		<table class="displayStyle">
			<tr>
			<th>  <spring:message code="enr.comment" /> :  </th>
			<th> </th>
			 </tr>
			
			<jstl:choose> 
			<jstl:when test="${empty educationRecord.comments}">
			<tr class="list-item">
			
			
			
			<td>	<form:textarea path="comments[0]" /> </td>
			
			<td>	<a href="#" onclick="event.preventDefault();"
					class="list-remove"> <spring:message code="enr.comment.remove" /> </a> </td>
			</tr>
			
			</jstl:when>
			<jstl:otherwise>
			<jstl:forEach items="${educationRecord.comments}" var="comment" varStatus="i" begin="0">
   			 <tr class="list-item">
			<td> 	<form:textarea path="comments[${i.index}]" /> </td>
			
     		<td>	<a href="#" onclick="event.preventDefault();"
					class="list-remove"> <spring:message code="enr.comment.remove" /> </a> </td>
	    </tr>
            <br />
        </jstl:forEach>
			</jstl:otherwise>
			</jstl:choose>
			
		</table>
		<a href="#" onclick="event.preventDefault();" class="list-add"><spring:message code="enr.comment.add" /></a>
		</div>
		<br />
		<form:errors cssClass="error" path="comments" />

</fieldset>
		
	
	<br>
	<br>
	
	
	<input type="button" name="save" id="save" value="<spring:message code="enr.save" />" />&nbsp; 
		<jstl:if test="${endorserRecord.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="enr.delete" />"
				onclick="return confirm('<spring:message code="enr.confirm.delete" />')" />&nbsp;
		</jstl:if>
		<jstl:if test="${curriculum.id!=0}">
		<input type="button" name="cancel" value="<spring:message code="enr.cancel" />"
			onclick="javascript: relativeRedir('/curriculum/ranger/display.do');" />
		<br />
		</jstl:if>
		
	</fieldset>
	</form:form>
	<script>
    $(document).ready(function() {
        $("#list1").dynamiclist();
    });
</script>

<script type="text/javascript">
$("#save").on("click",function(){validatePhone("<spring:message code='enr.confirmationPhone'/>","${countryCode}");});


	 
</script>

</jstl:if>
<jstl:if test="${!permission}">
<h3><spring:message code="enr.nopermisiontobehere" /></h3>
</jstl:if>






