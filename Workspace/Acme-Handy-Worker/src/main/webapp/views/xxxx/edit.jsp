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
		<jstl:when test="${xxxx.id == 0 || xxxx.isFinal == false}">

			<form:form action="xxxx/edit.do" modelAttribute="xxxx">

				<form:hidden path="id" />
				<form:hidden path="version" />
				<form:hidden path="ticker" />
				<form:hidden path="publishedMoment" />
				<form:hidden path="isFinal" />
				<form:hidden path="fixUpTask" />

				<form:label path="body">
					<spring:message code="xxxx.body" />
				</form:label>
				<form:textarea path="body" />
				<form:errors cssClass="error" path="body" />
				<br />
				<br />

				<form:label path="photoLink">
					<spring:message code="xxxx.photo.link" />
				</form:label>
				<form:textarea path="photoLink" />
				<form:errors cssClass="error" path="photoLink" />
				<br />
				<br />


				<spring:message code="xxxx.save.final" var="savexxxxFinal" />
				<spring:message code="xxxx.delete" var="deletexxxx" />
				<spring:message code="xxxx.confirm.delete" var="confirmDeletexxxx" />
				<spring:message code="xxxx.cancel" var="cancelxxxx" />
				<spring:message code="xxxx.save" var="savexxxx" />

				<jstl:if test="${xxxx.isFinal == false}">
					<input type="submit" id="sumbit" name="saveFinal"
						value="${savexxxxFinal}" />&nbsp;
	</jstl:if>
				<jstl:if test="${xxxx.isFinal == false}">
					<input type="submit" id="submit" name="saveDraft"
						value="${savexxxx}" />
				</jstl:if>
				<jstl:if test="${xxxx.id != 0}">

					<input type="submit" name="delete" value="${deletexxxx}"
						onclick="return confirm('${confirmDeletexxxx}')" />&nbsp;
	</jstl:if>
				<input type="button" name="cancel" value="${cancelxxxx}"
					onclick="window.history.back()" />
				<br />



			</form:form>

			<script>
				$(document).ready(function() {
					$("#list").dynamiclist();
				});
			</script>

		</jstl:when>
		<jstl:otherwise>
			<h3>
				<spring:message code="xxxx.nopermission" />
			</h3>
		</jstl:otherwise>
	</jstl:choose>
</security:authorize>