<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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

<display:table	pagesize="5" class="displaytag"
	name="sponsorships" requestURI="sponsorship/sponsor/list.do" id="row"	>


	
<display:column>
	<a href="sponsorship/sponsor/edit.do?sponsorshipId=${row.id}">
		<spring:message code="ss.edit"/>
	</a>

</display:column>

<spring:message code="ss.banner" var="bannerHeader" />
<display:column	property="banner" title="${bannerHeader}"
	sortable="true"	/>

<spring:message code="ss.targetPage" var="targetPageHeader"/>
<display:column	property="targetPage"	title="${targetPageHeader}"
	sortable="true"/>

<spring:message code="ss.creditCard" var="creditCardHeader"/>
<display:column	property="creditCard.number"	title="${creditCardHeader}"
	sortable="true"/>

<spring:message code="ss.sponsor" var="sponsorHeader"/>
<display:column	property="sponsor.userAccount.username"	title="${sponsorHeader}"
	sortable="true"	/>

</display:table>


	