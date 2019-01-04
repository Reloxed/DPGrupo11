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



<h3> <jstl:out value="${survivalClass.title}"> </jstl:out> </h3>

<table class="displayStyle">


<tr>
<th> <jstl:out value="${survivalClass.title}"> </jstl:out>  </th>
<th>   </th>
</tr>



<tr>
<td> <strong> <spring:message code="survivalClass.description" /> : </strong> </td>
<td> <jstl:out value="${survivalClass.description}"> </jstl:out> </td>
</tr>


<tr>
<td> <strong> <spring:message code="survivalClass.moment" /> : </strong> </td>
<td> <fmt:formatDate value="${survivalClass.moment}" pattern="dd/MM/yyyy HH:mm" /> </td>
</tr>


<tr>
<td> <strong> <spring:message code="survivalClass.place" /> : </strong> </td>
<td>
<jstl:out value="${survivalClass.place.name}"/> &nbsp; (<jstl:out value="${survivalClass.place.latitude}"/> , <jstl:out value="${survivalClass.place.longitude}"/>)
 </td>
</tr>



<tr>
<td> <strong> <spring:message code="survivalClass.trip" /> : </strong> </td>
<spring:message code="survivalClass.trip.show" var="showTrip" />
	<td>  <jstl:out value = "${survivalClass.trip.title}"/> &nbsp; (<a href="trip/display.do?tripId=${survivalClass.trip.id}"> ${showTrip} </a>) </td>

</tr>





<tr>
<td> <strong> <spring:message code="survivalClass.manager" /> : </strong> </td>
<spring:message code="survivalClass.actor.show" var="showPersonalData" />
<td>  <jstl:out value = "${survivalClass.trip.manager.userAccount.username}"/> &nbsp; (<a href="actor/display.do?actorId=${survivalClass.trip.manager.id}"> ${showPersonalData} </a>) </td>

</tr>



<tr>
<td> <strong> <spring:message code="survivalClass.explorers" /> : </strong> </td>
<td>
<jstl:choose>
<jstl:when test="${not empty survivalClass.explorers}"> 
<ul>
<jstl:forEach items="${survivalClass.explorers}" var="explorer">
<li> <jstl:out value="${explorer.userAccount.username}"/> &nbsp; (<a href="actor/display.do?actorId=${explorer.id}"> ${showPersonalData} </a>) </li>
</jstl:forEach>
</ul> 
</jstl:when>
<jstl:otherwise>

<spring:message code="survivalClass.explorers.empty" />

</jstl:otherwise>
</jstl:choose>

 </td>
</tr>





</table>

<br/>

<security:authorize access="isAuthenticated()">
<security:authentication property="principal.username" var="user" /> 

<jstl:if test="${survivalClass.trip.manager.userAccount.username == user}">


<div>
		
		<jstl:if test="${futureNoCancelledTrips.contains(survivalClass.trip)}">
				<a href="survivalClass/manager/edit.do?survivalClassId=${survivalClass.id}"> <spring:message
					code="survivalClass.edit" />
			</a>			
		</jstl:if>				
			
</div>
</jstl:if>

</security:authorize>


<spring:message code="survivalClasses.enrol" var="enrolSurvivalClass" />
<spring:message code="survivalClasses.unregister" var="unregisterSurvivalClass" />

<security:authorize access="hasRole('EXPLORER')">
		<jstl:if test="${canExplorerEnrolSurvivalClass}">

		
					<a href="survivalClass/explorer/enrol.do?survivalClassId=${survivalClass.id}" > ${enrolSurvivalClass}
					</a>	
					</jstl:if>
					
							<jstl:if test="${canExplorerUnregisterSurvivalClass}">

		
					<a href="survivalClass/explorer/unregister.do?survivalClassId=${survivalClass.id}" > ${unregisterSurvivalClass}
					</a>	
					</jstl:if>
					
									

 </security:authorize>



