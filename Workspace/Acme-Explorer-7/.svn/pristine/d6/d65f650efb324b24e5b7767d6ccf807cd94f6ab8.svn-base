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


<display:table name="tags" id="row"
	requestURI="tag/administrator/list.do" pagesize="5"
	class="displaytag">
	

	<!-- Action links -->
	
			<display:column>
			
				<jstl:if test="${!usedTags.contains(row)}">
					<a  href="tag/administrator/edit.do?tagId=${row.id}">
						<spring:message code="tag.edit" />
					</a>					
				</jstl:if>

		</display:column>
		
		<spring:message code="tag.confirm.delete" var="confirmDeleteTag"  />
		
		<display:column>
			<a href="tag/administrator/delete.do?tagId=${row.id}" onclick="return confirm('${confirmDeleteTag}')"> <spring:message
					code="tag.delete" />
			</a>
			
		</display:column>
		
		
		

	<!-- Attributes -->

	<spring:message code="tag.name"
	var="nameHeader" />
	<display:column property="name" title="${nameHeader}"
		sortable="true" />
			
		
	</display:table>


	<div>
		<a href="tag/administrator/create.do"> <spring:message
				code="tag.create" />
		</a>
	</div>


	

