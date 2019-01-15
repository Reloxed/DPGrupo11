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


<security:authorize access="hasRole('HANDYWORKER')">
	<!-- Action links -->
	<form:form action="finder/handyWorker/search.do"
		modelAttribute="finder">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="searchMoment" value="01/01/2001 00:00"/>

		<form:label path="keyWord">
			<spring:message code="finder.keyWord" />:
		</form:label>
		<form:input path="keyWord" />
		<form:errors cssClass="error" path="keyWord" />
		<br />
		<br />

		<form:label path="category">
				<spring:message code="finder.category" />:
		</form:label>
		<form:select path="category">
			<form:option value="0" label="  --  "/>
			<form:options items="${categories}" itemLabel="name" />
		</form:select>
		<br><br>

		<form:label path="warranty">
				<spring:message code="finder.warranty" />:
		</form:label>
		<form:select path="warranty">
			<form:option value="0" label="  --  "/>
			<form:options items="${warranties}" itemLabel="title" />
		</form:select>
		<br><br>

		<form:label path="startMoment">
			<spring:message code="finder.startMoment" />:
		</form:label>
		<form:input path="startMoment" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="startMoment" />
		<br />
		<br />


		<form:label path="endMoment">
			<spring:message code="finder.endMoment" />:
		</form:label>
		<form:input path="endMoment" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="endMoment" />
		<br />
		<br />


		<form:label path="priceLow">
			<spring:message code="finder.priceLow" />:
		</form:label>
		<form:input path="priceLow" placeholder="200.00" />
		<form:errors cssClass="error" path="priceLow" />
		<br />
		<br />

		<form:label path="priceHigh">
			<spring:message code="finder.priceHigh" />:
		</form:label>
		<form:input path="priceHigh" placeholder="200.00" />
		<form:errors cssClass="error" path="priceHigh" />
		<br />
		<br />

		<input type="submit" name="save"
			value="<spring:message code="finder.showResults" />"/>
		
		<input type="button" name="cancel"
			value="<spring:message code="finder.cancel" />"
			onclick="javascript: relativeRedir('fixUpTask/handyWorker/list.do');" />
		<br />

	</form:form>




</security:authorize>












