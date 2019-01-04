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





<jstl:if test="${searching == true}"> 
<h2> <spring:message code="trip.results" />  </h2>
</jstl:if>

<spring:message code="trip.search" var="searchTrips"  /> 




<div>

<jstl:choose>
<jstl:when test="${principal == 'EXPLORER'}" >


<form:form action="trip/explorer/search.do"  modelAttribute="finder" >

<form:hidden path="id"/>
<form:hidden path="version" />

<form:hidden path="results"/>

	<form:label path="keyword">
		<spring:message code="trip.finder.keyword" />:
	</form:label>
	<form:input path="keyword" />
	<form:errors cssClass="error" path="keyword" />
	
	<br />
	
	<fieldset>
	<legend> 
	<spring:message code="trip.finder.price" />:
	 </legend>
	
		<form:label path="priceMin">
		<spring:message code="trip.finder.priceMin" />:
	</form:label>
	<form:input path="priceMin" />
	<form:errors cssClass="error" path="priceMin" />
	
	<br />
	
		<form:label path="priceMax">
		<spring:message code="trip.finder.priceMax" />:
	</form:label>
	<form:input path="priceMax" />
	<form:errors cssClass="error" path="priceMax" />
	
	<br />
	</fieldset>


	<fieldset>
	<legend> 
	<spring:message code="trip.finder.date" />:
	 </legend>
	
		<form:label path="dateMin">
		<spring:message code="trip.finder.dateMin" />:
	</form:label>
	<form:input path="dateMin" />
	<form:errors cssClass="error" path="dateMin" />
	
	<br />
	
		<form:label path="dateMax">
		<spring:message code="trip.finder.dateMax" />:
	</form:label>
	<form:input path="dateMax" />
	<form:errors cssClass="error" path="dateMax" />
	
	<br />
	</fieldset>

<input type="submit" name="search" value="${searchTrips}" />&nbsp; 

<form:hidden path="explorer"/>



</form:form>

</jstl:when>

<jstl:when test="${principal == 'MANAGER'}">

<form action="trip/manager/search.do" method="post" > 

<label for=""> <spring:message code="trip.finder.keyword"/>:  </label>
<input type="text"  id="keyWord" name="keyword">
<br/>
<br/>

<input type="submit" name="save" value="${searchTrips}" >


</form>

</jstl:when>

<jstl:otherwise>

<form action="trip/search.do" method="post" > 

<label for=""> <spring:message code="trip.finder.keyword"/>:  </label>
<input type="text"  id="keyWord" name="keyword">
<br/>
<br/>

<input type="submit" name="save" value="${searchTrips}" >


</form>






</jstl:otherwise>
</jstl:choose>

</div>


<!-- Listing grid -->



<display:table name="trips" id="row"
	requestURI="${requestURI}" pagesize="5"
	class="displaytag">
	


	<!-- Action links -->
	
	<jstl:if test="${requestURI == 'trip/manager/list.do'}">
	
	<security:authorize access="hasRole('MANAGER')">
		<display:column>
				
				<jstl:if test="${!publishedTrips.contains(row)}">
					<a href="trip/manager/edit.do?tripId=${row.id}" > <spring:message
					code="trip.edit" />
					</a>					
				</jstl:if>
				
		</display:column>
		
		
		<display:column>
				
				<jstl:if test="${pendingTrips.contains(row)}">
				<spring:message code="trip.confirm.cancel" var="confirmCancel" />
					<a href="trip/manager/cancel.do?tripId=${row.id}"  onclick="javascript: return confirm('${confirmCancel}')">
						<spring:message code="trip.cancel" />
					</a>					
				</jstl:if>
				
		</display:column>
		
		<display:column>
		
				
				<jstl:if test="${futureNoCancelledTrips.contains(row)}">
				<a href="survivalClass/manager/create.do?tripId=${row.id}"> <spring:message
					code="trip.createSurvivalClass" />
				</a>			
				</jstl:if>
				
		
	
		</display:column>
		
		
		
	</security:authorize>
		
		
		</jstl:if>
		

	<security:authorize access="hasRole('EXPLORER')">
		<display:column>
				<jstl:if test="${pendingTrips.contains(row)}">
				<spring:message code="trip.confirm.apply" var="confirmApply" />
					<a href="application/explorer/create.do?tripId=${row.id}"   onclick="javascript: return confirm('${confirmApply}')" >
						<spring:message code="trip.apply" />
					</a>					
				</jstl:if>
				
		</display:column>
		
		<display:column>
			
				<jstl:if test="${paidPassedTrips.contains(row)}">
				<spring:message code="trip.confirm.write" var="confirmWrite" />
					<a href="story/explorer/create.do?tripId=${row.id}"   onclick="javascript: return confirm('${confirmWrite}')" >
						<spring:message code="trip.write" />
					</a>					
				</jstl:if>
				
		</display:column>
		
		
		
	</security:authorize>


<security:authorize access="hasRole('SPONSOR')">
		<display:column>
			
				<jstl:if test="${pendingTrips.contains(row)}">
				<spring:message code="trip.confirm.sponsor" var="confirmSponsor" />
					<a href="sponsorship/sponsor/create.do?tripId=${row.id}"   onclick="javascript: return confirm('${confirmSponsor}')" >
						<spring:message code="trip.sponsor" />
					</a>					
				</jstl:if>
				
		</display:column>
		
		
		
		
	</security:authorize>



<security:authorize access="hasRole('AUDITOR')">
		<display:column>
			
				<jstl:if test="${auditableTrips.contains(row)}">
				<spring:message code="trip.confirm.audit" var="confirmAudit" />
					<a href="audit/auditor/create.do?tripId=${row.id}"   onclick="javascript: return confirm('${confirmAudit}')" >
						<spring:message code="trip.audit" />
					</a>					
				</jstl:if>
				
		</display:column>
		
		<display:column>
			
				<jstl:if test="${auditableTrips.contains(row)}">
				<spring:message code="trip.confirm.note" var="confirmNote" />
					<a href="note/auditor/create.do?tripId=${row.id}"   onclick="javascript: return confirm('${confirmNote}')" >
						<spring:message code="trip.note" />
					</a>					
				</jstl:if>
				
		</display:column>
		

	</security:authorize>






	<!-- Attributes -->

	
	<display:column>
	<jstl:if test="${not empty row.cancellationReason}">
	<span> <spring:message code="trip.isCancelled" /> </span>
	</jstl:if>
	</display:column>



	
	<spring:message code="trip.ticker"
	var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}"
		sortable="true" />

	<spring:message code="trip.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="true" />
		
		
		<spring:message code="trip.price" var="priceHeader" />
	<display:column property="price" title="${priceHeader}"
		sortable="true" />	

	
	<security:authorize access="hasRole('MANAGER')">
	<spring:message code="trip.publicationDate"
	var="publicationDateHeader" />
	<display:column property="publicationDate" title="${publicationDateHeader}"
		sortable="true" format = "{0,date,dd/MM/yyyy}"/>
	
	</security:authorize>
	
	<spring:message code="trip.startDate"
	var="startDateHeader" />
	<display:column property="startDate" title="${startDateHeader}"
		sortable="true" format = "{0,date,dd/MM/yyyy}"/>
		
		
	<spring:message code="trip.endDate"
	var="endDateHeader" />
	<display:column property="endDate" title="${endDateHeader}"
		sortable="true" format = "{0,date,dd/MM/yyyy}"/>
	
	
	
	<spring:message code="trip.stages.title"
	var="stagesHeader" />
	
	<display:column title="${stagesHeader}">
	
	<ul>
	<jstl:forEach items="${row.stage}" var="idStage">
	 
	<li> <jstl:out value="${idStage.title}"> </jstl:out>   </li>
	</jstl:forEach>
	
	</ul>
	
	</display:column>
	
	
	<spring:message code="trip.category"
	var="categoryNameHeader" />
	<display:column property="category.name" title="${categoryNameHeader}"
		sortable="true" />
	
	
	<spring:message code="trip.tags"
	var="tagsHeader" />
	
	<display:column title="${tagsHeader}">
	
	<jstl:choose>
<jstl:when test="${not empty row.tags}">

<ul>
<jstl:forEach items="${row.tags}" var="tag">

<li> <jstl:out value="${tag.name}"> </jstl:out> </li>


</jstl:forEach>
</ul> 

</jstl:when>
<jstl:otherwise>

<spring:message code="trip.tags.empty" />   

</jstl:otherwise>

</jstl:choose>
	
	</display:column>
	
	
	<display:column>
			<a href="trip/display.do?tripId=${row.id}"> <spring:message
					code="trip.display" />
			</a>
	</display:column>
	
	
	
</display:table>



<!-- Action links -->

<security:authorize access="hasRole('MANAGER')">
	<div>
		<a href="trip/manager/create.do"> <spring:message
				code="trip.create" />
		</a>
	</div>
</security:authorize>


