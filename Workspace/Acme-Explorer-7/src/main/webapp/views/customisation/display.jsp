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


<table class="displayStyle">


<tr>

<th> <spring:message code="customisation.parameters"/>  </th>
<th> <spring:message code="customisation.values"/>  </th>
</tr>

<tr>
<td> <strong> <spring:message code="customisation.welcomeBanner" /> : </strong> </td>
<td><a href="${customisation.welcomeBanner}"> <jstl:out value="${customisation.welcomeBanner}" />  </a></td>
</tr>

<tr>
<td> <strong> <spring:message code="customisation.welcomeMessageEn" /> : </strong> </td>
<td> <jstl:out value="${customisation.welcomeMessageEn}" /> </td>
</tr>

<tr>
<td> <strong> <spring:message code="customisation.welcomeMessageEs" /> : </strong> </td>

<td> <jstl:out value="${customisation.welcomeMessageEs}" /> </td>
</tr>

<tr>
<td> <strong> <spring:message code="customisation.vatTax" /> : </strong> </td>

<td> <jstl:out value="${customisation.vatTax}" /> </td>
</tr>


<tr>
<td> <strong> <spring:message code="customisation.countryCode" /> : </strong> </td>

<td> <jstl:out value="${customisation.countryCode}" /> </td>
</tr>

<tr>
<td> <strong> <spring:message code="customisation.spamWords" /> : </strong> </td>

<td>

<jstl:choose>
<jstl:when test="${not empty customisation.spamWords}">

<ul>
<jstl:forEach items="${customisation.spamWords}" var="spamWord">

<li> <jstl:out value="${spamWord}"> </jstl:out> </li>


</jstl:forEach>
</ul> 

</jstl:when>
<jstl:otherwise>

<spring:message code="customisation.spamWords.empty" />   

</jstl:otherwise>

</jstl:choose>
 </td>
</tr>


<tr>
<td> <strong> <spring:message code="customisation.finderCacheTime" /> : </strong> </td>

<td> <jstl:out value="${customisation.finderCacheTime}" /> </td>
</tr>

<tr>
<td> <strong> <spring:message code="customisation.finderMaxResults" /> : </strong> </td>

<td> <jstl:out value="${customisation.finderMaxResults}" /> </td>
</tr>


</table>

<div>

<a href="customisation/administrator/edit.do"> <spring:message code="customisation.edit"/> </a>

</div>



