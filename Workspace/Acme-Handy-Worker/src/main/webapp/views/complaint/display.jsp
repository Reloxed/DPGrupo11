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

<display:table name="complaint" id="row"
	requestURI="complaint/display.do" class="displaytag">

	<spring:message code="complaint.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}"
		sortable="false" />

	<spring:message code="complaint.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		sortable="false" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="complaint.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="false" />
	
	<jstl:if test="${complaint.attachments}">
	<display:column property="attachments"
				titleKey="complaint.attachments" />
	</jstl:if>
</display:table>

<security:authorize access="hasRole('HANDYWORKER')">
	<input type="button" name="back"
		value="<spring:message code="complaint.back" />"
		onClick="javascript: window.location.replace('complaint/handyWorker/list.do')" />
</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">
	<input type="button" name="back"
		value="<spring:message code="complaint.back" />"
		onClick="javascript: window.location.replace('complaint/customer/list.do')" />
</security:authorize>