<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jsp:useBean id="now" class="java.util.Date"/>

<style>

.tableColorGreen {
	
	background-color: chartreuse;
}

.tableColorOrange {
	background-color: orange;
}

.tableColorGrey {
	background-color: grey;
}
</style>
<security:authorize access="hasAnyRole('HANDYWORKER','ADMINISTRATOR')">

	<display:table name="finalObservations" id="row"
		requestURI="observation/actor/list.do" pagesize="5" class="displaytag">


		<!-- Attributes -->
		
		<jstl:choose>
			<jstl:when test="${(now.time-row.publishedMoment.time) < 2629800000}">
				<jstl:set var="bgcolor" value="tableColorGreen"/>
			</jstl:when>
			
			<jstl:when test="${(now.time-row.publishedMoment.time) > 2629800000 and (now.time-row.publishedMoment.time<5259600000)}">
				<jstl:set var="bgcolor" value="tableColorOrange"/>
			</jstl:when>
			
			<jstl:otherwise>
				<jstl:set var="bgcolor" value="tableColorGrey"/>
			</jstl:otherwise>
		</jstl:choose>
		
		<jstl:if test="${language == español}">
		
			<spring:message code="observation.publishedMoment"
				var="publishedMoment"/>
			
			<display:column property="publishedMoment" title="${publishedMoment}"
				sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"
				class="${bgcolor}">
			</display:column>
		
		</jstl:if>
		
		<jstl:if test="${language == english}">
		
			<spring:message code="observation.publishedMoment"
				var="publishedMoment"/>
			
			<display:column property="publishedMoment" title="${publishedMoment}"
				sortable="true" format="{0,date,yyyy/MM/dd HH:mm}"
				class="${bgcolor}">
			</display:column>
		
		</jstl:if>
		<spring:message code="observation.ticker" var="tickerHeader" />
		<display:column property="ticker" title="${tickerHeader}" class="${bgcolor}"/>

		<spring:message code="observation.body" var="bodyHeader" />
		<display:column property="body" title="${bodyHeader}" class="${bgcolor}"/>

		<spring:message code="observation.fix" var="fixHeader" />
		<display:column property="fixUpTask.id" title="${fixHeader}" class="${bgcolor}"/>

		<display:column>
			<a href="observation/actor/display.do?observationId=${row.id}"> <spring:message
					code="observation.display" />
			</a>
		</display:column>


	</display:table>



</security:authorize>

<security:authorize access="hasAnyRole('CUSTOMER')">
	
	
		
	<display:table name="observations" id="row"
		requestURI="observation/customer/list.do" pagesize="5"
		class="displaytag">


		<!-- Attributes -->
		
		<jstl:choose>
			<jstl:when test="${(now.time-row.publishedMoment.time) < 2629800000}">
				<jstl:set var="bgcolor" value="tableColorGreen"/>
			</jstl:when>
			
			<jstl:when test="${(now.time-row.publishedMoment.time) > 2629800000 and (now.time-row.publishedMoment.time<5259600000)}">
				<jstl:set var="bgcolor" value="tableColorOrange"/>
			</jstl:when>
			
			<jstl:otherwise>
				<jstl:set var="bgcolor" value="tableColorGrey"/>
			</jstl:otherwise>
		</jstl:choose>
		
		<jstl:if test="${language == español}">
		
			<spring:message code="observation.publishedMoment"
				var="publishedMoment"/>
			
			<display:column property="publishedMoment" title="${publishedMoment}"
				sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"
				class="${bgcolor}">
			</display:column>
		
		</jstl:if>
		
		<jstl:if test="${language == english}">
		
			<spring:message code="observation.publishedMoment"
				var="publishedMoment"/>
			
			<display:column property="publishedMoment" title="${publishedMoment}"
				sortable="true" format="{0,date,yyyy/MM/dd HH:mm}"
				class="${bgcolor}">
			</display:column>
		
		</jstl:if>
		
		<spring:message code="observation.ticker" var="tickerHeader" />
		<display:column property="ticker" title="${tickerHeader}" class="${bgcolor}"/>

		<spring:message code="observation.body" var="bodyHeader" />
		<display:column property="body" title="${bodyHeader}" class="${bgcolor}"/>

		<spring:message code="observation.fix" var="fixHeader" />
		<display:column property="fixUpTask.id" title="${fixHeader}" class="${bgcolor}"/>

		<display:column>
			<a href="observation/actor/display.do?observationId=${row.id}"> <spring:message
					code="observation.display" />
			</a>
		</display:column>

		<display:column>
			<jstl:if test="${row.isFinal == false}">

				<a href="observation/customer/edit.do?observationId=${row.id}">
					<spring:message code="observation.edit" />
				</a>
		
		</jstl:if>
		</display:column>

	</display:table>



</security:authorize>
<!-- Listing grid -->
