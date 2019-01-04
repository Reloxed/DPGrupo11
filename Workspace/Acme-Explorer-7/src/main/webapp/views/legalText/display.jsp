<%--
 * 
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<h3> <jstl:out value="${legalText.title}"> </jstl:out> </h3>

<table class="displayStyle">


<tr>
<th> <jstl:out value="${legalText.title}"> </jstl:out>  </th>
<th>   </th>
</tr>

<tr>
<td> <strong> <spring:message code="legalText.body" /> : </strong> </td>
<td> <jstl:out value="${legalText.body}"> </jstl:out> </td>
</tr>

<tr>
<td> <strong> <spring:message code="legalText.registerDate" /> : </strong> </td>
<td> <fmt:formatDate value="${legalText.registerDate}" pattern="dd/MM/yyyy" /> </td>
</tr>

<tr>
<td> <strong> <spring:message code="legalText.laws" /> : </strong> </td>

<td>


<ul>
<jstl:forEach items="${legalText.laws}" var="law">

<li> <jstl:out value="${law}"> </jstl:out> </li>


</jstl:forEach>
</ul> 

 </td>
</tr>

<tr>
<td> <strong> <spring:message code="legalText.mode" /> : </strong> </td>

<td>

<jstl:choose> 
<jstl:when test="${legalText.isDraft}"> 

<spring:message code = "legalText.mode.draft"/>
 </jstl:when>
 <jstl:otherwise>
 <spring:message code = "legalText.mode.final"/>
 </jstl:otherwise>

</jstl:choose>
 </td>
</tr>

</table>

<div>

	<jstl:if test="${legalText.isDraft}">
			<a href="legalText/administrator/edit.do?legalTextId=${legalText.id}"> <spring:message
					code="legalText.edit" />
			</a>
	</jstl:if>
				
</div>



