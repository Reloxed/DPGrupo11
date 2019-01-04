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


<display:table name="emergencyContacts" id="row"
	requestURI="emergencyContact/explorer/list.do" pagesize="5"
	class="displaytag">
	

	<!-- Action links -->
	
			<display:column>
			
				
					<a  href="emergencyContact/explorer/edit.do?emergencyContactId=${row.id}" >
						<spring:message code="emergencyContact.edit" />
					</a>					
		</display:column>

			

	<!-- Attributes -->

	<spring:message code="emergencyContact.name"
	var="nameHeader" />
	<display:column property="name" title="${nameHeader}"
		sortable="true" />
			
		
		<spring:message code="emergencyContact.email"
	var="emailHeader" />
	<display:column  title="${emailHeader}"
		sortable="true" > 
		<jstl:choose>
		<jstl:when test="${not empty row.email}">
		<jstl:out value="${row.email}" />
		</jstl:when>
		<jstl:otherwise> - </jstl:otherwise>
		</jstl:choose>
		</display:column>
		
		
			<spring:message code="emergencyContact.phone"
	var="phoneHeader" />
	<display:column title="${phoneHeader}"
		sortable="true" >
		
			<jstl:choose>
		<jstl:when test="${not empty row.phone}">
		<jstl:out value="${row.phone}" />
		</jstl:when>
		<jstl:otherwise> - </jstl:otherwise>
		</jstl:choose>
		 </display:column>
		
		
	</display:table>


	<div>
		<a href="emergencyContact/explorer/create.do"> <spring:message
				code="emergencyContact.create" />
		</a>
	</div>


	

