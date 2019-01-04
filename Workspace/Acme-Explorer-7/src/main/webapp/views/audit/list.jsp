

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->


<display:table pagesize="5" class="displaytag" 
	name="audits" requestURI="audit/auditor/list.do" id="row">
	
	
	
<!-- Action links -->
	
		<display:column>
			
				<jstl:if test="${row.isDraft}">
					<a href="audit/auditor/edit.do?auditId=${row.id}" >
						<spring:message code="audit.edit" />
					</a>					
				</jstl:if>
				
			
		</display:column>



	<!-- Attributes -->


	<spring:message code="audit.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="true" />


	<spring:message code="audit.moment"
		var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		sortable="true" format = "{0,date,dd/MM/yyyy HH:mm}" />


	<spring:message code="audit.trip"
		var="tripHeader" />
		<spring:message code="audit.trip.show"
		var="showTrip" />
		
	
	 <display:column title="${tripHeader}"> 
	 
	 
	<div>  <jstl:out value = "${row.trip.title}"/> &nbsp; (<a href="trip/display.do?tripId=${row.trip.id}"> ${showTrip} </a>)</div>
	 
	 
	  </display:column>


		<display:column>
			<a href="audit/display.do?auditId=${row.id}"> <spring:message
					code="audit.display" />
			</a>
		</display:column>


</display:table>
