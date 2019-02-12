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

<jsp:useBean id="now" class="java.util.Date" />

<style>
<!--
.tableColorGreen {
	background-color: snow ;
        
}

.tableColorOrange {
	background-color: MintCream;
}

.tableColorGrey {
	background-color: SandyBrown;
}
-->
</style>

<security:authorize access="hasAnyRole('CUSTOMER')">
	<!-- Listing grid -->

	<display:table pagesize="10" class="displaytag" name="nusts"
		requestURI="nust/list.do" id="row">

		<!-- Attributes-->
		
		

		<jstl:choose>
			<jstl:when test="${(now.time-row.publishedMoment.time)<2629800000}">
				<jstl:set var="bgcolor" value="tableColorGreen" />
			</jstl:when>

			<jstl:when
				test="${(now.time-row.publishedMoment.time)>2629800000 and (now.time-row.publishedMoment.time)<5259600000}">
				<jstl:set var="bgcolor" value="tableColorOrange" />
			</jstl:when>

			<jstl:otherwise>
				<jstl:set var="bgcolor" value="tableColorGrey" />
			</jstl:otherwise>
		</jstl:choose>



		<jstl:if test="${language==español}">


			<spring:message code="nust.publishedMoment"
				var="publishedMoment" />
			<display:column property="publishedMoment" title="${publishedMoment}"
				sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"
				class="${bgcolor}" />

		</jstl:if>

		<jstl:if test="${language==english}">


			<spring:message code="nust.publishedMoment"
				var="publishedMoment" />
			<display:column property="publishedMoment" title="${publishedMoment}"
				sortable="true" format="{0,date,yyyy/MM/dd HH:mm}"
				class="${bgcolor}" />

		</jstl:if>


		<spring:message code="nust.body" var="body" />
		<display:column property="body" title="${body}" sortable="true"
			class="${bgcolor}" />

		<jstl:if test="${row.isFinal==false}">

			<spring:message code="nust.isFinal" var="isFinal" />
			<display:column title="${isFinal}" sortable="true" class="${bgcolor}"> Draft </display:column>

			<security:authorize access="hasRole('CUSTOMER')">
				<display:column >
					<a href="nust/customer/edit.do?nustId=${row.id}">
						<spring:message code="nust.edit" />
					</a>
				</display:column>
			</security:authorize>

		</jstl:if>

		<jstl:if test="${row.isFinal==true}">

			<spring:message code="nust.isFinal" var="isFinal" />
			<display:column title="${isFinal}" sortable="true" class="${bgcolor}"> Final</display:column>

		</jstl:if>
		<display:column >
			<a href="nust/display.do?nustId=${row.id}"> <spring:message
					code="nust.display" />
			</a>
		</display:column>


	</display:table>

</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">
	<p>
		<a href="nust/customer/create.do"><spring:message
				code="nust.create" /></a>
	</p>
</security:authorize>

<security:authorize access="hasAnyRole('ADMINISTRATOR','HANDYWORKER')">
	<!-- Listing grid -->

	<display:table pagesize="10" class="displaytag" name="published"
		requestURI="nust/list.do" id="row">

		<!-- Attributes-->

		<jstl:choose>
			<jstl:when test="${(now.time-row.publishedMoment.time)<2629800000}">
				<jstl:set var="bgcolor" value="tableColorGreen" />
			</jstl:when>

			<jstl:when
				test="${(now.time-row.publishedMoment.time)>2629800000 and (now.time-row.publishedMoment.time)<5259600000}">
				<jstl:set var="bgcolor" value="tableColorOrange" />
			</jstl:when>

			<jstl:otherwise>
				<jstl:set var="bgcolor" value="tableColorGrey" />
			</jstl:otherwise>
		</jstl:choose>




		<jstl:if test="${language==español}">


			<spring:message code="nust.publishedMoment"
				var="publishedMoment" />
			<display:column property="publishedMoment" title="${publishedMoment}"
				sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"
				class="${bgcolor}" />

		</jstl:if>

		<jstl:if test="${language==english}">


			<spring:message code="nust.publishedMoment"
				var="publishedMoment" />
			<display:column property="publishedMoment" title="${publishedMoment}"
				sortable="true" format="{0,date,yyyy/MM/dd HH:mm}"
				class="${bgcolor}" />

		</jstl:if>


		<spring:message code="nust.body" var="body" />
		<display:column property="body" title="${body}" sortable="true"
			class="${bgcolor}" />

		<spring:message code="nust.picture" var="picture" />
		<display:column property="picture" title="${picture}" sortable="true"
			class="${bgcolor}" />


		<display:column class="${bgcolor}">
			<a href="nust/display.do?nustId=${row.id}"> <spring:message
					code="nust.display" />
			</a>
		</display:column>

	</display:table>
</security:authorize>