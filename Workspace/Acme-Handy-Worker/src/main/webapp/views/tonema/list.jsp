<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<style>
<!--
.tableColorDarkOliveGreen {
	background-color: darkolivegreen;
}

.tableColorMediumPurple {
	background-color: mediumpurple;
}

.tableColorGrey {
	background-color: grey;
}
-->
</style>


<security:authorize
	access="hasRole('CUSTOMER')">
	<!-- Listing grid -->

	<display:table pagesize="5" class="displaytag" name="tonemas"
		requestURI="tonema/list.do" id="row">

		<jstl:set var="publicationMoment" value="${row.publicationMoment}" />
		<jstl:choose>
			<jstl:when test="${publicationMoment > onePreviousMonth}">
				<jstl:set var="bgcolor" value="tableColorDarkOliveGreen" />
			</jstl:when>

			<jstl:when test="${publicationMoment > twoPreviousMonth}">
				<jstl:set var="bgcolor" value="tableColorMediumPurple" />
			</jstl:when>
			
			<jstl:otherwise>
				<jstl:set var="bgcolor" value="tableColorGrey" />
			</jstl:otherwise>
			
		</jstl:choose>

		<spring:message code="tonema.edit" var="editHeader" />
		<display:column title="${editHeader}">
			<jstl:if test="${!row.isFinal}">
				<a href="tonema/edit.do?tonemaId=${row.id}"> <spring:message
						code="tonema.edit" />	</a>
			</jstl:if>

		</display:column>
		
		<spring:message code="tonema" var="tonemaHeader" />
		<display:column title="${tonemaHeader}">
				<a href="tonema/display.do?tonemaId=${row.id}"> <spring:message
						code="tonema.display" />	</a>
		</display:column>

		<!-- Attributes-->

		<spring:message code="tonema.ticker" var="tickerHeader" />
		<display:column property="ticker" title="${tickerHeader}"
			sortable="true" class="${bgcolor}"/>

		<spring:message code="tonema.publicationMoment" var="momentHeader" />
		<jstl:choose>
			<jstl:when test="${language=='es'}">
				<display:column property="publicationMoment" title="${momentHeader}"
					sortable="true" format="{0,date,dd-MM-yy HH:mm}" />
			</jstl:when>
			<jstl:otherwise>
				<display:column property="publicationMoment" title="${momentHeader}"
					sortable="true" format="{0,date,yy/MM/dd HH:mm}" />
			</jstl:otherwise>
		</jstl:choose>
			
		<spring:message code="tonema.body" var="bodyHeader" />
		<display:column property="body" title="${bodyHeader}"
			sortable="true" />
		
		<spring:message code="tonema.isFinal" var="finalHeader" />
		<display:column property="isFinal" title="${finalHeader}"
			sortable="true"/>		
			
		<spring:message code="tonema.fixUpTask" var="fixUpTaskHeader" />
		<display:column title="${fixUpTaskHeader}">
			<a href="fixUpTask/customer/display.do?fixUpTaskId=${row.fixUpTask.id}"> <spring:message
					code="tonema.fixUpTask.display" /></a>
		</display:column>

	</display:table>
</security:authorize>

<security:authorize
	access="hasRole('HANDYWORKER')">
	<!-- Listing grid -->

	<display:table pagesize="5" class="displaytag" name="tonemas"
		requestURI="tonema/list.do" id="row">

		<jstl:set var="publicationMoment" value="${row.publicationMoment}" />
		<jstl:choose>
			<jstl:when test="${publicationMoment > onePreviousMonth}">
				<jstl:set var="bgcolor" value="tableColorDarkOliveGreen" />
			</jstl:when>

			<jstl:when test="${publicationMoment > twoPreviousMonth}">
				<jstl:set var="bgcolor" value="tableColorMediumPurple" />
			</jstl:when>
			
			<jstl:otherwise>
				<jstl:set var="bgcolor" value="tableColorGrey" />
			</jstl:otherwise>
			
		</jstl:choose>
		
		<spring:message code="tonema" var="tonemaHeader" />
		<display:column title="${tonemaHeader}">
				<a href="tonema/display.do?tonemaId=${row.id}"> <spring:message
						code="tonema.display" />	</a>
		</display:column>

		<!-- Attributes-->

		<spring:message code="tonema.ticker" var="tickerHeader" />
		<display:column property="ticker" title="${tickerHeader}"
			sortable="true" class="${bgcolor}"/>

		<spring:message code="tonema.publicationMoment" var="momentHeader" />
		<jstl:choose>
			<jstl:when test="${language=='es'}">
				<display:column property="publicationMoment" title="${momentHeader}"
					sortable="true" format="{0,date,dd-MM-yy HH:mm}" />
			</jstl:when>
			<jstl:otherwise>
				<display:column property="publicationMoment" title="${momentHeader}"
					sortable="true" format="{0,date,yy/MM/dd HH:mm}" />
			</jstl:otherwise>
		</jstl:choose>
			
		<spring:message code="tonema.body" var="bodyHeader" />
		<display:column property="body" title="${bodyHeader}"
			sortable="true" />
		
		<spring:message code="tonema.isFinal" var="finalHeader" />
		<display:column property="isFinal" title="${finalHeader}"
			sortable="true"/>		
			
		<spring:message code="tonema.fixUpTask" var="fixUpTaskHeader" />
		<display:column title="${fixUpTaskHeader}">
			<a href="fixUpTask/handyWorker/display.do?taskId=${row.fixUpTask.id}"> <spring:message
					code="tonema.fixUpTask.display" /></a>
		</display:column>

	</display:table>
</security:authorize>