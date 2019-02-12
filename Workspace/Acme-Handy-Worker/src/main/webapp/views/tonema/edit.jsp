<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CUSTOMER')">
<jstl:choose>
	<jstl:when test="${tonema.id == 0 || tonema.isFinal == false}">

		<form:form action="tonema/edit.do" modelAttribute="tonema">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="ticker" />
			<form:errors cssClass="error" path="ticker" />
			<form:hidden path="publicationMoment" />
			<form:errors cssClass="error" path="publicationMoment" />
			<form:hidden path="fixUpTask" />
			<form:label path="body">
				<spring:message code="tonema.body" />:
			</form:label>
			<br />
			<form:textarea path="body" />
			<form:errors cssClass="error" path="body" />
			<br />
			<br />
			
			<form:label path="picture">
				<spring:message code="tonema.picture" />: 
			</form:label>
			<form:input path="picture" />
			<form:errors cssClass="error" path="picture" />
			<br/><br/>
			
			<spring:message code="tonema.save" var="saveTonema" />			
			<spring:message code="tonema.save.final" var="saveTonemaFinal" />
			<jstl:if test="${tonema.isFinal == false}">
				<input type="submit" id="sumbit" name="saveFinal"
					value="${saveTonemaFinal}" />&nbsp;
			</jstl:if>
			
			<jstl:if test="${tonema.isFinal == false}">
				<input type="submit" id="submit" name="save" value="${saveTonema}" />
			</jstl:if>
			
			<spring:message code="tonema.delete" var="deleteTonema" />
			<spring:message code="tonema.confirm.delete"
				var="confirmDeleteTonema" />
			<jstl:if test="${tonema.id != 0}">
				<input type="submit" name="delete" value="${deleteTonema}"
					onclick="return confirm('${confirmDeleteTonema}')" />&nbsp;
			</jstl:if>
			
			<spring:message code="tonema.cancel" var="cancelTonema" />
			<input type="button" name="cancel" value="${cancelTonema}"
				onclick="window.history.back()" />
			<br />



		</form:form>

	</jstl:when>
	<jstl:otherwise>
		<h3>
			<spring:message code="tonema.nopermission" />
		</h3>
	</jstl:otherwise>
</jstl:choose>
</security:authorize>

<security:authorize access="hasRole('HANDYWORKER')">
		<h3>
			<spring:message code="tonema.nocustomer" />
		</h3>
</security:authorize>