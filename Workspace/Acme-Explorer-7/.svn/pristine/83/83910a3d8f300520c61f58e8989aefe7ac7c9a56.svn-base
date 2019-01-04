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


<display:table name="finders" id="row"
	requestURI="finder/explorer/list.do" pagesize="5"
	class="displaytag">


	<!-- Attributes -->

	<spring:message code="finder.moment"
	var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		sortable="true" format = "{0,date,dd/MM/yyyy HH:mm}" />
		
			
	<spring:message code="finder.keyword"
	var="keywordHeader" />
	<display:column property="keyword" title="${keywordHeader}"
		sortable="true"  />
		
	<spring:message code="finder.dateMin"
	var="dateMinHeader" />
	<display:column property="dateMin" title="${dateMinHeader}"
		sortable="true" format = "{0,date,dd/MM/yyyy}" />
	
	<spring:message code="finder.dateMax"
	var="dateMaxHeader" />
	<display:column property="dateMax" title="${dateMaxHeader}"
		sortable="true" format = "{0,date,dd/MM/yyyy}" />
	
	<spring:message code="finder.priceMin"
	var="priceMinHeader" />
	<display:column property="priceMin" title="${priceMinHeader}"
		sortable="true"  />


	<spring:message code="finder.priceMax"
	var="priceMaxHeader" />
	<display:column property="priceMax" title="${priceMaxHeader}"
		sortable="true"  />
		
		
		
		
	<!-- Action links -->
	
		<display:column>
		
			<a href="trip/explorer/search.do?finderId=${row.id}" >
						<spring:message code="finder.search.show" />
			</a>
			
		</display:column>
			
	</display:table>



	<div>
		<a href="trip/explorer/list.do"> <spring:message
				code="finder.search" />
		</a>
	</div>


	

