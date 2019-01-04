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


	<security:authorize access="hasRole('EXPLORER')">
	
	<form action="application/explorer/list.do" method="get">
	
	<input type="radio" name="applicationStatus" value="0" checked> <spring:message code="application.status.all" />
	<input type="radio" name="applicationStatus" value="1"> <spring:message code="application.status.accepted" />
	<input type="radio" name="applicationStatus" value="2">  <spring:message code="application.status.pending" />
	<input type="radio" name="applicationStatus" value="3"> 		<spring:message code="application.status.due" />
	<input type="radio" name="applicationStatus" value="4">  <spring:message code="application.status.rejected" />
	<input type="radio" name="applicationStatus" value="5">  <spring:message code="application.status.cancelled" />
	<br />
	<spring:message code="application.status.choose" var="choose"/>
	<input type="submit" value="${choose}">
	</form>
	
	
	
	</security:authorize>



<!-- Listing grid -->

<display:table name="applications" id="row"
	requestURI="${requestURI}" pagesize="5"
	class="displaytag">
	

	<!-- Action links -->
	
	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			
			<jstl:if test="${row.status == 'PENDING'}">
				<a href="application/manager/reject.do?applicationId=${row.id}" >
						<spring:message code="application.reject" />
				</a>					
			</jstl:if>
			
		</display:column>
		
		
		<display:column>
			<jstl:if test="${row.status == 'PENDING'}">
					<a href="application/manager/approve.do?applicationId=${row.id}" >
						<spring:message code="application.approve" />
					</a>					
			</jstl:if>
				
			
		</display:column>
		
	</security:authorize>



	<security:authorize access="hasRole('EXPLORER')">
		<display:column>
				<jstl:if test="${cancellableApplications.contains(row)}">
					<a href="application/explorer/cancel.do?applicationId=${row.id}">
						<spring:message code="application.cancelApplication" />
					</a>					
				</jstl:if>
			
		</display:column>
		
		<display:column>
			
				<jstl:if test="${row.status == 'DUE'}">
					<a href="application/explorer/pay.do?applicationId=${row.id}" >
						<spring:message code="application.pay" />
					</a>					
				</jstl:if>
			
		</display:column>
		
		
		
		<display:column>
			<a href="application/explorer/edit.do?applicationId=${row.id}"> <spring:message
					code="application.comment" />
			</a>
		</display:column>
		
		
		
		
	</security:authorize>






	<!-- Attributes -->

	<spring:message code="application.moment"
	var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		sortable="true" format = "{0,date,dd/MM/yyyy HH:mm}"/>



	

	
	<jstl:choose>
	<jstl:when test="${row.status == 'ACCEPTED'}">
	<jstl:set var="background" value="greenCell" /> 
	</jstl:when>
	
	<jstl:when test = "${row.status == 'CANCELLED'}">
	<jstl:set var="background" value="cyanCell" /> 
	</jstl:when>
	
	
	<jstl:when test = "${row.status == 'REJECTED'}">
	<jstl:set var="background" value="greyCell" /> 
	</jstl:when>
	
	<jstl:when test = "${row.status == 'DUE'}">
	<jstl:set var="background" value="yellowCell" /> 
	</jstl:when>
	
	<jstl:when test = "${row.status == 'PENDING' && startingSoonTrips.contains(row.trip)}">
	<jstl:set var="background" value="redCell" /> 
	</jstl:when>
	
	<jstl:otherwise>
	<jstl:set var="background" value="whiteCell" /> 
	</jstl:otherwise>
	
	</jstl:choose>
	


	<spring:message code="application.status" var="statusHeader" />
	
	<display:column class="${background}" title="${statusHeader}" sortable="true"> 
	<jstl:choose>
	<jstl:when test="${empty row.rejectionReason}">
	<span> <jstl:out value="${row.status}" /> </span>
	</jstl:when>
	<jstl:otherwise>
	<span> <jstl:out value="REJECTED"/> </span>
	<div> <jstl:out value="${row.rejectionReason}"/> </div>
	</jstl:otherwise>
	</jstl:choose>
	
	</display:column>

	
	
	<security:authorize access="hasRole('EXPLORER')">
	<spring:message code="application.comments"
		var="commentsHeader" />
	<display:column title="${commentsHeader}" >
	
	
	<ul>
	<jstl:forEach items="${row.comments}" var="comment">
	 
	<li> <jstl:out value="${comment}"> </jstl:out>   </li>
	</jstl:forEach>
	
	</ul>
	
	</display:column>
	</security:authorize>
	
	
	<security:authorize access="hasRole('MANAGER')">
	<spring:message code="application.applicant"
		var="applicantHeader" />
		
	<display:column title="${applicantHeader}" sortable="false" >
	<div><a href="actor/display.do?actorId=${row.applicant.id}"> <jstl:out value="${row.applicant.userAccount.username}"> </jstl:out>  </a>  </div>
	</display:column>
	</security:authorize>
	
	
	

	<spring:message code="application.trip.title" var="tripTitleHeader" />
	<spring:message code="application.trip.show"
		var="showTrip" />
	<display:column  title="${tripTitleHeader}"
		sortable="true" >
	<div>  <jstl:out value = "${row.trip.title}"/> &nbsp; (<a href="trip/display.do?tripId=${row.trip.id}"> ${showTrip} </a>)</div>
		
		</display:column>

</display:table>





