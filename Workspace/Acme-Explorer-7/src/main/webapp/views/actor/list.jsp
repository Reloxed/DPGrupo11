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

<!-- Listing grid -->



<display:table name="actors" id="row"
	requestURI="actor/administrator/list.do" pagesize="5"
	class="displaytag">
	


	<!-- Action links -->
	

		
		
		
		
		<jstl:choose>
		<jstl:when test="${row.userAccount.authorities[0].authority == 'EXPLORER'}">
		<jstl:set var="link" value="explorer" />
		<spring:message code="actor.rol.explorer" var="rol"/>
		</jstl:when>
		<jstl:when test="${row.userAccount.authorities[0].authority == 'MANAGER'}">
		<jstl:set var="link" value="manager" />
		<spring:message code="actor.rol.manager" var="rol"/>
		</jstl:when>
		<jstl:when test="${row.userAccount.authorities[0].authority == 'RANGER'}">
		<jstl:set var="link" value="ranger" />
		<spring:message code="actor.rol.ranger" var="rol"/>
		</jstl:when>
		<jstl:when test="${row.userAccount.authorities[0].authority == 'AUDITOR'}">
		<jstl:set var="link" value="auditor" />
		<spring:message code="actor.rol.auditor" var="rol"/>
		</jstl:when>
		<jstl:when test="${row.userAccount.authorities[0].authority == 'SPONSOR'}">
		<jstl:set var="link" value="sponsor" />
		<spring:message code="actor.rol.sponsor" var="rol"/>
		</jstl:when>
		</jstl:choose>
		
	


	<!-- Attributes -->
	
	<spring:message code="actor.rol" var="rolHeader" />
	<display:column title="${rolHeader}" sortable="true">
	<jstl:out value="${rol}" />
	
	</display:column>
	
	<spring:message code="actor.username" var="usernameHeader" />
	<display:column title="${usernameHeader}" > 
	<jstl:out value="${row.userAccount.username}" />
	<jstl:if test="${row.isSuspicious}">
	<div>
	( <spring:message code="actor.isSuspicious"/> )
	</div>
	</jstl:if>
	<jstl:if test="${row.isBanned}">
	<div>
	( <spring:message code="actor.isBanned"/> )
	</div>
	</jstl:if>
	</display:column>

	<spring:message code="actor.name" var="nameHeader" />
	<display:column title="${nameHeader}" > 
	<jstl:out value="${row.name}" /> <jstl:out value="${row.surname}" /> 
	</display:column>
	
	
	<spring:message code="actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}"
		sortable="true" />

		
		<display:column>
		

			<a href="actor/display.do?actorId=${row.id}"  >
				<spring:message code="actor.show"  />
			</a>					
		</display:column>
	
	
	
</display:table>



