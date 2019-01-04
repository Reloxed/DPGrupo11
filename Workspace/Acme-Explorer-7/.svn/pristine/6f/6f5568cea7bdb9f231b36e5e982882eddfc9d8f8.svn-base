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



<h3> <jstl:out value="${story.title}"> </jstl:out> </h3>

<table class="displayStyle">


<tr>
<th> <jstl:out value="${story.title}"> </jstl:out>  </th>
<th>   </th>
</tr>



<tr>
<td> <strong> <spring:message code="story.text" /> : </strong> </td>
<td> <jstl:out value="${story.text}"> </jstl:out> </td>
</tr>


<tr>
<td> <strong> <spring:message code="story.linkAttachment" /> : </strong> </td>
<td>
 <ul>
<jstl:forEach items="${story.linkAttachment}" var="link">
<li> <a href="${link}"> ${link} </a> </li>
</jstl:forEach>
</ul> 
 </td>
</tr>

<tr>
<td> <strong> <spring:message code="story.trip" /> : </strong> </td>
<spring:message code="story.trip.show" var="showTrip" />
	<td>  <jstl:out value = "${story.trip.title}"/> &nbsp; (<a href="trip/display.do?tripId=${story.trip.id}"> ${showTrip} </a>) </td>

</tr>

<tr>
<td> <strong> <spring:message code="story.explorer" /> : </strong> </td>
<td>  <a href="actor/display.do?actorId=${story.explorer.id}"> ${story.explorer.userAccount.username}</a>  </td>

</tr>





</table>


<security:authorize access="isAuthenticated()">
<security:authentication property="principal.username" var="user" /> 

<jstl:if test="${story.explorer.userAccount.username == user}">


<div>
		<a href="story/explorer/edit.do?storyId=${story.id}" >
				<spring:message code="story.edit" />
		</a>					
			
</div>
</jstl:if>
</security:authorize>

