<%--
 * action-1.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jstl:if test="${permission }">

	<form:form action="survivalClass/manager/edit.do"
		modelAttribute="survivalClass">

		<form:hidden path="id" />

		<form:hidden path="version" />

		<form:hidden path="explorers" />


		<form:hidden path="trip" />


		<form:label path="title">
			<spring:message code="survivalClass.title" />:
	</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br />
		<br />


		<form:label path="description">
			<spring:message code="survivalClass.description" />:
	</form:label>
		<form:input path="description" />
		<form:errors cssClass="error" path="description" />
		<br />
		<br />


		<form:label path="moment">
			<spring:message code="survivalClass.moment" />:
	</form:label>
		<form:input path="moment" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="moment" />
		<br />
		<span> <strong> <spring:message
					code="survivalClass.moment.text.1" /> <fmt:formatDate
					value="${survivalClass.trip.startDate}" pattern="dd/MM/yyyy" /> <spring:message
					code="survivalClass.moment.text.2" /> <fmt:formatDate
					value="${survivalClass.trip.endDate}" pattern="dd/MM/yyyy" />
		</strong>
		</span>
		<br />
		<br />










		<fieldset>

			<legend>
				<form:label path="place">
					<spring:message code="survivalClass.place" /> : </form:label>
			</legend>

			<form:label path="place.name">
				<spring:message code="survivalClass.place.name" />:
	</form:label>
			<form:input path="place.name" />
			<form:errors cssClass="error" path="place.name" />
			<br />

			<form:label path="place.latitude">
				<spring:message code="survivalClass.place.latitude" />:
	</form:label>
			<form:input path="place.latitude" />
			<form:errors cssClass="error" path="place.latitude" />
			<br />

			<form:label path="place.longitude">
				<spring:message code="survivalClass.place.longitude" />:
	</form:label>
			<form:input path="place.longitude" />
			<form:errors cssClass="error" path="place.longitude" />
			<br />

		</fieldset>



		<br />
		<br />



		<spring:message code="survivalClass.save" var="saveSurvivalClass" />
		<spring:message code="survivalClass.delete" var="deleteSurvivalClass" />
		<spring:message code="survivalClass.confirm.delete"
			var="confirmDeleteSurvivalClass" />
		<spring:message code="survivalClass.cancel" var="cancelSurvivalClass" />

		<input type="submit" name="save" value="${saveSurvivalClass}" />&nbsp; 
	<jstl:if test="${survivalClass.id != 0}">
			<input type="submit" name="delete" value="${deleteSurvivalClass}"
				onclick="return confirm('${confirmDeleteSurvivalClass}')" />&nbsp;
	</jstl:if>
		<input type="button" name="cancel" value="${cancelSurvivalClass}"
			onclick="javascript: relativeRedir('survivalClass/manager/list.do');" />
		<br />


	</form:form>

</jstl:if>

<jstl:if test="${!permission }">
	<h3>
		<spring:message code="survivalClass.nopermission" />
	</h3>
</jstl:if>