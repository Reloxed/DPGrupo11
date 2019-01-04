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

<security:authorize access="hasRole('MANAGER')" var="isManager"/>

<jstl:choose>
<jstl:when test="${permission || isManager}">




<h3> <jstl:out value="${trip.title}"> </jstl:out> </h3>

<table class="displayStyle">


<tr>
<th> <jstl:out value="${trip.title}"> </jstl:out>  </th>
<th>   </th>
</tr>

<tr>
<td> <strong> <spring:message code="trip.ticker" /> : </strong> </td>
<td> <jstl:out value="${trip.ticker}"> </jstl:out> </td>
</tr>

<tr>
<td> <strong> <spring:message code="trip.description" /> : </strong> </td>
  <td> <jstl:out value="${trip.description}"> </jstl:out> </td>
</tr>

<tr>
<td> <strong> <spring:message code="trip.price" /> : </strong> </td>
<td> <jstl:out value="${trip.price}"> </jstl:out> </td>
</tr>

<tr>
<td> <strong> <spring:message code="trip.requirements" /> : </strong> </td>

<td>

<jstl:choose>
<jstl:when test="${not empty trip.requirements}">

<ul>
<jstl:forEach items="${trip.requirements}" var="requirement">

<li> <jstl:out value="${requirement}"> </jstl:out> </li>


</jstl:forEach>
</ul> 

</jstl:when>
<jstl:otherwise>

<spring:message code="trip.requirements.empty" />   

</jstl:otherwise>

</jstl:choose>
 </td>
</tr>

<security:authorize access="hasRole('MANAGER')">
<tr>
<td> <strong> <spring:message code="trip.publicationDate" /> : </strong> </td>
<td> <fmt:formatDate value="${trip.publicationDate}" pattern="dd/MM/yyyy" /> </td>
</tr>
</security:authorize>

<tr>
<td> <strong> <spring:message code="trip.startDate" /> : </strong> </td>
<td> <fmt:formatDate value="${trip.startDate}" pattern="dd/MM/yyyy" /> </td>
</tr>

<tr>
<td> <strong> <spring:message code="trip.endDate" /> : </strong> </td>
<td> <fmt:formatDate value="${trip.endDate}" pattern="dd/MM/yyyy" /> </td>
</tr>

<jstl:if test="${not empty trip.cancellationReason}">
<tr >
<td class="redCell"> <strong> <spring:message code="trip.isCancelled" /> </strong> </td>
<td > <jstl:out value="${trip.cancellationReason}"> </jstl:out> </td>
</tr>

</jstl:if>

<tr>
<td> <strong> <spring:message code="trip.stages" /> : </strong> </td>
<td> 

<jstl:set var="stages" value="${trip.stage}"  /> 
<spring:message code="trip.stage.number" var="stageNumber"/>
<spring:message code="trip.stage.title" var="stageTitle"/>
<spring:message code="trip.stage.description" var="stageDescription"/>
<spring:message code="trip.stage.price" var="stagePrice"/>

<display:table name="${stages}" id="child${parent_rowNum}" pagesize="15" class="displaytag">

<display:column property="number" title="${stageNumber}" sortable="false" />
<display:column property="title" title="${stageTitle}" sortable="false" />
<display:column property="description" title="${stageDescription}" sortable="false" />
<display:column property="price" title="${stagePrice}" sortable="false" />

</display:table>
 </td>
</tr>

<tr>
<td> <strong> <spring:message code="trip.category" /> : </strong> </td>
<td> <jstl:out value="${trip.category.name}"> </jstl:out> </td>
</tr>


<tr >
<td> <strong> <spring:message code="trip.tags" /> : </strong> </td>
<td>

<jstl:choose>
<jstl:when test="${not empty trip.tags}">

<ul>
<jstl:forEach items="${trip.tags}" var="tag">

<li> <jstl:out value="${tag.name}"> </jstl:out> </li>


</jstl:forEach>
</ul> 

</jstl:when>
<jstl:otherwise>

<spring:message code="trip.tags.empty" />   

</jstl:otherwise>

</jstl:choose>
 </td>
</tr>


<tr>
<td> <strong> <spring:message code="trip.legaltext" /> : </strong> </td>
<td >

<div class="legalText">


<div> <jstl:out value="${trip.legaltext.title}"> </jstl:out> </div>
<br>

<div> <jstl:out value="${trip.legaltext.body}"> </jstl:out> </div>
<br>
<div> <fmt:formatDate value="${trip.legaltext.registerDate}" pattern="dd/MM/yyyy" />  </div>
<br>

<div>
<ul>
 <jstl:forEach items="${trip.legaltext.laws}" var="law">

<li> <jstl:out value="${law}"> </jstl:out> </li>


</jstl:forEach>
</ul>
 </div>

</div>
 
 </td>
</tr>


<spring:message code="trip.ranger.display"  var="showRanger"/>




<tr>
<td> <strong> <spring:message code="trip.ranger" /> : </strong> </td>
<td> <jstl:out value="${trip.ranger.userAccount.username}"/>  &nbsp; (<a href="actor/display.do?actorId=${trip.ranger.id}"> ${showRanger} </a>)  </td>
</tr>

<spring:message code="trip.manager.display"  var="showManager"/>

<tr>
<td> <strong> <spring:message code="trip.manager" /> : </strong> </td>
<td> <jstl:out value="${trip.manager.userAccount.username}"/>  &nbsp; (<a href="actor/display.do?actorId=${trip.manager.id}"> ${showManager} </a>)  </td>
</tr>


<tr>
<td> <strong> <spring:message code="trip.audits" /> : </strong> </td>
<spring:message code="trip.audits.show" var="showAudit" />
<td>

<jstl:choose>
<jstl:when test="${not empty trip.audits}">


<jstl:set var="audits" value="${trip.audits}"  /> 
<spring:message code="trip.audit.title" var="auditTitle"/>
<spring:message code="trip.audit.moment" var="auditMoment"/>
<spring:message code="trip.audit.auditor" var="auditAuditor"/>


<display:table name="${audits}" id="childA" pagesize="15" class="displaytag">


<display:column property="title" title="${auditTitle}" sortable="false" />
<display:column property="moment" title="${auditMoment}" sortable="false" format = "{0,date,dd/MM/yyyy HH:mm}" />

<display:column  title="${auditAuditor}" sortable="false" >
<a href="actor/display.do?actorId=${childA.auditor.id}"> ${childA.auditor.userAccount.username} </a>
</display:column>
<jstl:set value="${childA.id}" var="auditId"/>
<display:column><a href="audit/display.do?auditId=${auditId}"> ${showAudit} </a> </display:column>

</display:table>




</jstl:when>
<jstl:otherwise>

<spring:message code="trip.audits.empty" />   

</jstl:otherwise>

</jstl:choose>
</td>
</tr>




<tr>
<td> <strong> <spring:message code="trip.stories" /> : </strong> </td>
<spring:message code="trip.stories.show" var="showStory" />
<td>

<jstl:choose>
<jstl:when test="${not empty trip.stories}">


<jstl:set var="stories" value="${trip.stories}"  /> 
<spring:message code="trip.story.title" var="storyTitle"/>
<spring:message code="trip.story.explorer" var="storyExplorer"/>


<display:table name="${stories}" id="childS" pagesize="15" class="displaytag">


<display:column property="title" title="${storyTitle}" sortable="false" />
<display:column property="explorer.userAccount.username" title="${storyExplorer}" sortable="false" />
<jstl:set value="${childS.id}" var="storyId"/>
<display:column><a href="story/display.do?storyId=${storyId}"> ${showStory} </a> </display:column>

</display:table>

</jstl:when>

<jstl:otherwise>

<spring:message code="trip.stories.empty" />   

</jstl:otherwise>

</jstl:choose>
</td>
</tr>

<tr>
<td> <strong> <spring:message code="trip.survivalClasses" /> : </strong> </td>
<spring:message code="trip.survivalClasses.show" var="showSurvivalClass" />
<spring:message code="trip.survivalClasses.enrol" var="enrolSurvivalClass" />
<spring:message code="trip.survivalClasses.unregister" var="unregisterSurvivalClass" />
<security:authorize access="hasRole('EXPLORER')" var="isExplorer"/>
<td>

<jstl:choose>
<jstl:when test="${not empty trip.survivalClasses}">

<jstl:set var="survivalClasses" value="${trip.survivalClasses}"  /> 
<spring:message code="trip.survivalClass.title" var="survivalClassTitle"/>
<spring:message code="trip.survivalClass.moment" var="survivalClassMoment"/>
<spring:message code="trip.survivalClass.manager" var="survivalClassManager"/>


<display:table name="${survivalClasses}" id="childSC" pagesize="15" class="displaytag">


<display:column property="title" title="${survivalClassTitle}" sortable="false" />
<display:column property="moment" title="${survivalClassMoment}" sortable="false" format = "{0,date,dd/MM/yyyy HH:mm}" />
<display:column  title="${survivalClassManager}" sortable="false" >
<div> <a href="actor/display.do?actorId=${childSC.trip.manager.id}"> <jstl:out value="${childSC.trip.manager.userAccount.username}"></jstl:out> </a></div>
</display:column>
<jstl:set value="${childSC.id}" var="survivalClassId"/>
<display:column>
<jstl:choose>
<jstl:when test="${isExplorer}">

<a href="survivalClass/explorer/display.do?survivalClassId=${survivalClassId}"> ${showSurvivalClass} </a>
</jstl:when>
<jstl:otherwise>
<a href="survivalClass/display.do?survivalClassId=${survivalClassId}"> ${showSurvivalClass} </a>
</jstl:otherwise>
</jstl:choose>
 </display:column>


 
</display:table>


</jstl:when>
<jstl:otherwise>

<spring:message code="trip.survivalClasses.empty" />   

</jstl:otherwise>

</jstl:choose>
</td>
</tr>





</table>


<jstl:if test="${not empty randomSponsorship}">
<jstl:set var="banner" value="${randomSponsorship.linkBanner}"/>
<jstl:set var="infoPage" value="${randomSponsorship.linkInfoPage}"/>

<a href="${infoPage}" target="_blank"> <img src="${banner}"> </a>
</jstl:if>

<div>


</div>

</jstl:when>
<jstl:otherwise>

<h3><spring:message code="trip.nopermission.display"  /></h3>
</jstl:otherwise>
</jstl:choose>

