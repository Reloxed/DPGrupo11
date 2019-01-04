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


<display:table name="legalTexts" id="row"
	requestURI="legalText/administrator/list.do" pagesize="5"
	class="displaytag">
	

	<!-- Action links -->
	
		<display:column>
			<jstl:if test="${row.isDraft}">
				<a href="legalText/administrator/edit.do?legalTextId=${row.id}"> <spring:message
					code="legalText.edit" />
				</a>
			</jstl:if>
		</display:column>

	



	<!-- Attributes -->


	<spring:message code="legalText.title"
	var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="true" />


	<spring:message code="legalText.registerDate"
	var="registerDateHeader" />
	<display:column property="registerDate" title="${registerDateHeader}"
		sortable="true" format = "{0,date,dd/MM/yyyy}"/>


	<display:column>
			<a href="legalText/administrator/display.do?legalTextId=${row.id}"> <spring:message
					code="legalText.display" />
			</a>
		</display:column>

	</display:table>


	<div>
		<a href="legalText/administrator/create.do"> <spring:message
				code="legalText.create" />
		</a>
	</div>


	

