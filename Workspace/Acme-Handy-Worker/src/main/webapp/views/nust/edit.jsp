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

<security:authorize access="hasAnyRole('CUSTOMER')">

	<form:form action="nust/customer/edit.do"
		modelAttribute="nust" id="form">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="ticker" />
		

		<jstl:if test="${nust.id == 0 }">
			<form:hidden path="publishedMoment" value="01/01/2001 00:00" />
		</jstl:if>
		<jstl:if test="${nust.id != 0 }">
			<form:hidden path="publishedMoment" />
			<form:hidden path="isFinal" />
			<form:hidden path="fixUpTask"/>
		</jstl:if>


		<br />
		<br />
		<jstl:if test="${nust.id==0}">
			<div>
				<p>
					<spring:message code="nust.fixUpTask" />
					:
				</p>
				<form:select path="fixUpTask" style="width:400px;">

					<form:option label="-----" value="0" />
					<form:options items="${fixUpTasks}" itemLabel="description"
						itemValue="id" />

				</form:select>
				<br />
			</div>
		</jstl:if>


		<div>
			<p>
				<form:label path="body">
					<spring:message code="nust.body" />: </form:label>
			</p>
			<form:textarea path="body" />
		</div>
		<br />
		<br />


		<form:label path="picture">
			<spring:message code="nust.picture" />
		</form:label>
		<spring:message code="nust.picture" var="picture" />
		<form:input placeholder="${picture}" path="picture" />
		<form:errors cssClass="error" path="picture" />
		<br />
		<br />

		
			<input type="submit" name="saveDraft" id="save"
				value='<spring:message code="nust.saveDraft"/>' />
		
		<jstl:if test="${observation.id != 0 }">
		
			<input type="submit" name="saveFinal" id="save"
				value='<spring:message code="nust.saveFinal"/>' />
				
				<input type="submit" id="delete" name="delete"
			value="<spring:message code="nust.delete" />" />
			
		</jstl:if>
		<input type="button" name="cancel"
			value="<spring:message code="nust.cancel" />"
			onclick="window.history.back()" />

		<br />

	</form:form>
</security:authorize>