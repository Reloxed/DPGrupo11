

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
	name="notes" requestURI="${requestURI}" id="row">
	
	
	
	<security:authorize access="hasRole('MANAGER')">
	 <display:column>

		<jstl:if test="${empty row.managerReply}">
					<a href="note/manager/reply.do?noteId=${row.id}" >
						<spring:message code="note.reply" />
					</a>					
		</jstl:if>
	</display:column>

</security:authorize>
	
	
	
	<!-- Attributes -->



	<spring:message code="note.moment"
		var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		sortable="true" format = "{0,date,dd/MM/yyyy HH:mm}" />


	<spring:message code="note.trip"
		var="tripHeader" />
		<spring:message code="note.trip.show"
		var="showTrip" />
		
	 <display:column title="${tripHeader}"> 
	 
	 
	<div>  <jstl:out value = "${row.trip.title}"/> &nbsp; (<a href="trip/display.do?tripId=${row.trip.id}"> ${showTrip} </a>)</div>
	 
	 
	  </display:column>

	<security:authorize access="hasRole('AUDITOR')">
		<display:column>
			<a href="note/auditor/display.do?noteId=${row.id}"> <spring:message
					code="note.display" />
			</a>
		
		</display:column>
</security:authorize> 


	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<a href="note/manager/display.do?noteId=${row.id}"> <spring:message
					code="note.display" />
			</a>
		
		</display:column>
</security:authorize> 

</display:table>
