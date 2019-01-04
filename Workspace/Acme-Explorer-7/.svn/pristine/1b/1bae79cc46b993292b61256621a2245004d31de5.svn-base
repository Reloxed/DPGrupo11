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



<security:authorize access="isAuthenticated()" var="authenticated" />

<jstl:if test="${authenticated}" >
<security:authentication property="principal.username" var="user" /> 
</jstl:if>

<jstl:choose>
		<jstl:when test="${not empty sponsor}">
		<jstl:set var="actorType" value="sponsor" />
		
		<jstl:if test="${sponsor.userAccount.username == user}">
		<jstl:set var="permission" value="true" />
		</jstl:if>
		</jstl:when>
		
		
		<jstl:when test="${not empty auditor}">
		<jstl:set var="actorType" value="auditor" />
		
		<jstl:if test="${auditor.userAccount.username == user}">
		<jstl:set var="permission" value="true" />
		</jstl:if>
		</jstl:when>
		
		
		<jstl:when test="${not empty ranger}">
		<jstl:set var="actorType" value="ranger" />
		
		<jstl:if test="${ranger.userAccount.username == user}">
		<jstl:set var="permission" value="true" />
		
		</jstl:if>
		</jstl:when>
		
		
		<jstl:when test="${not empty explorer}">
		<jstl:set var="actorType" value="explorer" />
		
		<jstl:if test="${explorer.userAccount.username == user}">
		<jstl:set var="permission" value="true" />
		</jstl:if>
		</jstl:when>
		
		
		
		<jstl:when test="${not empty manager}">
		<jstl:set var="actorType" value="manager" />
		
		<jstl:if test="${manager.userAccount.username == user}">
		<jstl:set var="permission" value="true" />
		</jstl:if>
		</jstl:when>
		
		
		
		<jstl:when test="${not empty administrator}">
		<jstl:set var="actorType" value="administrator" />
		
		<jstl:if test="${administrator.userAccount.username == user}">
		<jstl:set var="permission" value="true" />
		</jstl:if>
		</jstl:when>
		</jstl:choose>

	
		



<jstl:choose>
<jstl:when test="${authenticated || actorType == 'ranger'}">




<h2> <spring:message code="actor.profile"/> </h2>


<h3> <jstl:out value="${actor.userAccount.username}"> </jstl:out> </h3>


<table class="displayStyle">


<tr>
<th> <spring:message code="actor.rol.${actorType}" /> </th>
<th>   </th>
</tr>


<tr>
<td> <strong> <spring:message code="actor.name" /> : </strong> </td>
<td> <jstl:out value="${actor.name}"> </jstl:out>  <jstl:out value="${actor.surname}"> </jstl:out>  </td>
</tr>


<tr>
<td> <strong> <spring:message code="actor.email" /> : </strong> </td>
<td> <jstl:out value="${actor.email}"> </jstl:out> </td>
</tr>

<tr>
<td> <strong> <spring:message code="actor.phone" /> : </strong> </td>
<td> <jstl:choose> <jstl:when test="${not empty actor.phone}">  <jstl:out value="${actor.phone}"> </jstl:out>
</jstl:when> <jstl:otherwise> <spring:message code="actor.phone.empty" /> </jstl:otherwise> </jstl:choose></td>
</tr>


<tr>
<td> <strong> <spring:message code="actor.address" /> : </strong> </td>
<td> <jstl:choose> <jstl:when test="${not empty actor.address}">  <jstl:out value="${actor.address}"> </jstl:out>
</jstl:when> <jstl:otherwise> <spring:message code="actor.address.empty" /> </jstl:otherwise> </jstl:choose></td>
</tr>

<tr>
<td> <strong> <spring:message code="actor.socialIdentities" /> : </strong> </td>

<td>

<jstl:choose>
<jstl:when test="${not empty actor.socialIdentities}">



<jstl:set var="identities" value="${actor.socialIdentities}"  /> 
<spring:message code="actor.socialIdentities.nick" var="identityNick"/>
<spring:message code="actor.socialIdentities.socialNetwork" var="identityNetwork"/>
<spring:message code="actor.socialIdentities.linkProfile" var="identityProfile"/>
<spring:message code="actor.socialIdentities.linkPhoto" var="identityPhoto"/>

<display:table name="${identities}" id="child${parent_rowNum}" pagesize="15" class="displaytag">
<jstl:if test="${permission == true}">
<spring:message code="actor.socialIdentities.delete" var="deleteSocialIdentity"/>
<jstl:set value="${child.id}" var="socialId"/>
<display:column><a href="socialIdentity/actor/edit.do?socialIdentityId=${socialId}"> ${deleteSocialIdentity} </a> </display:column>
</jstl:if>



<display:column title="${identityPhoto}" sortable="false"> <jstl:choose> <jstl:when test="${not empty child.linkPhoto}"> 
<img src="${child.linkPhoto}" height="80" width="80">
</jstl:when> 
<jstl:otherwise> <img src="images/nophoto.png" alt="-"  height="65" width="90"> </jstl:otherwise> </jstl:choose>   </display:column>


<display:column property="nick" title="${identityNick}" sortable="false" />
<display:column property="socialNetwork" title="${identityNetwork}" sortable="false" />
<display:column title="${identityProfile}" sortable="false"> <a href="${child.linkProfile}" target="_blank"> ${child.linkProfile} </a></display:column>

</display:table>


</jstl:when>
<jstl:otherwise>

<spring:message code="actor.socialIdentities.empty" />   

</jstl:otherwise>

</jstl:choose>


 </td>
</tr>
<jstl:if test="${actorType == 'sponsor'}">

<tr>
<td> <strong> <spring:message code="sponsor.sponsorships" /> : </strong> </td>
<spring:message code="sponsor.sponsorships.trip.view" var="showTrip"/>
<td>

<jstl:choose>
<jstl:when test="${not empty sponsor.sponsorships}">


<jstl:set var="sponsorships" value="${sponsor.sponsorships}"  /> 
<spring:message code="sponsor.sponsorships.linkInfoPage" var="linkPage"/>
<spring:message code="sponsor.sponsorships.trip.title" var="trip"/>


<display:table name="${sponsorships}" id="childSs" pagesize="15" class="displaytag">


<display:column title="${linkPage}" sortable="false" ><a href="${childSs.linkInfoPage}">${childSs.linkInfoPage} </a> </display:column>
<display:column  title="${trip}" sortable="false" > <div><jstl:out value = "${row.trip.title}"/> &nbsp; (<a href="trip/display.do?tripId=${childSs.trip.id}"> ${showTrip} </a>) </div></display:column>

</display:table>

</jstl:when>

<jstl:otherwise>

<spring:message code="sponsor.sponsorships.empty" />   

</jstl:otherwise>

</jstl:choose>
</td>
</tr>

</jstl:if>

<jstl:if test="${actorType == 'auditor'}">

<tr>
<td> <strong> <spring:message code="auditor.audits" /> : </strong> </td>

<td>

<jstl:choose>
<jstl:when test="${not empty auditor.audits}">


<jstl:set var="audits" value="${auditor.audits}"  /> 
<spring:message code="auditor.audits.moment" var="momentAudit"/>
<spring:message code="auditor.audits.title" var="titleAudit"/>
<spring:message code="auditor.audits.trip.title" var="tripAudit"/>
<spring:message code="auditor.audits.trip.show" var="showTrip"/>

<display:table name="${audits}" id="childAud" pagesize="15" class="displaytag">


<display:column property="moment" title="${momentAudit}" sortable="false" format = "{0,date,dd/MM/yyyy HH:mm}" />
<display:column property="title" title="${titleAudit}" sortable="false" />
<display:column  title="${tripAudit}" sortable="false" >
 <jstl:out value = "${childAud.trip.title}"/> &nbsp; (<a href="trip/display.do?tripId=${childAud.trip.id}"> ${showTrip} </a>)
</display:column>
</display:table>

</jstl:when>

<jstl:otherwise>

<spring:message code="auditor.audits.empty" />   

</jstl:otherwise>

</jstl:choose>
</td>
</tr>

</jstl:if>

<jstl:if test="${actorType == 'manager'}">

<tr>
<td> <strong> <spring:message code="manager.trips" /> : </strong> </td>

<td>

<jstl:choose>
<jstl:when test="${not empty manager.trips}">


<jstl:set var="trips" value="${manager.trips}"  /> 
<spring:message code="manager.trips.ticker" var="tickerTrip"/>
<spring:message code="manager.trips.title" var="titleTrip"/>
<spring:message code="manager.trips.startDate" var="startDateTrip"/>
<spring:message code="manager.trips.endDate" var="endDateTrip"/>
<spring:message code="manager.trips.category" var="categoryTrip"/>

<display:table name="${trips}" id="childTrip" pagesize="15" class="displaytag">


<display:column property="ticker" title="${tickerTrip}" sortable="false" />
<display:column property="title" title="${titleTrip}" sortable="false" />
<display:column property="startDate" title="${startDateTrip}" sortable="false" format = "{0,date,dd/MM/yyyy}" />
<display:column property="endDate" title="${endDateTrip}" sortable="false" format = "{0,date,dd/MM/yyyy}" />
<display:column property="category.name" title="${categoryTrip}" sortable="false" />
</display:table>

</jstl:when>

<jstl:otherwise>

<spring:message code="manager.trips.empty" />   

</jstl:otherwise>

</jstl:choose>
</td>
</tr>

</jstl:if>

<jstl:if test="${actorType == 'ranger'}">

<tr>
<td> <strong> <spring:message code="ranger.trips" /> : </strong> </td>

<td>

<jstl:choose>
<jstl:when test="${not empty ranger.trips}">


<jstl:set var="trips" value="${ranger.trips}"  /> 
<spring:message code="ranger.trips.ticker" var="tickerTrip"/>
<spring:message code="ranger.trips.title" var="titleTrip"/>
<spring:message code="ranger.trips.startDate" var="startDateTrip"/>
<spring:message code="ranger.trips.endDate" var="endDateTrip"/>
<spring:message code="ranger.trips.category" var="categoryTrip"/>

<display:table name="${trips}" id="childTrip" pagesize="15" class="displaytag">


<display:column property="ticker" title="${tickerTrip}" sortable="false" />
<display:column property="title" title="${titleTrip}" sortable="false" />
<display:column property="startDate" title="${startDateTrip}" sortable="false" format = "{0,date,dd/MM/yyyy}" />
<display:column property="endDate" title="${endDateTrip}" sortable="false" format = "{0,date,dd/MM/yyyy}" />
<display:column property="category.name" title="${categoryTrip}" sortable="false" />
</display:table>

</jstl:when>

<jstl:otherwise>

<spring:message code="ranger.trips.empty" />   

</jstl:otherwise>

</jstl:choose>
</td>
</tr>


<tr>
<td> <strong> <spring:message code="ranger.CV" /> : </strong> </td>
<td> <jstl:choose> <jstl:when test="${not empty ranger.CV}"> 
<spring:message code="ranger.CV.display" var="display"/>
<a href="curriculum/display.do?curriculumId=${ranger.CV.id}"> ${display} </a>
</jstl:when> <jstl:otherwise> <spring:message code="ranger.CV.empty" /> </jstl:otherwise> </jstl:choose></td>
</tr>



</jstl:if>



</table>






<jstl:if test="${permission == true}">
<a href="${actorType}/${actorType}/edit.do?${actorType}Id=${actor.id}"> <spring:message code= "actor.edit"/> </a>
<br />
<a href="socialIdentity/actor/create.do"> <spring:message code= "actor.socialIdentities.add"/> </a>
</jstl:if>



</jstl:when>
<jstl:otherwise>


<div> <spring:message code="actor.unauthorizedAccess.message" /> </div>
</jstl:otherwise>
</jstl:choose>




