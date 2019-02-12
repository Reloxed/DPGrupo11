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
		<jstl:when test="${tapu.id == 0 || tapu.isFinal == false}">

			<form:form action="tapu/edit.do" modelAttribute="tapu">

				<form:hidden path="id" />
				<form:hidden path="version" />
				<form:hidden path="ticker" />
				<form:hidden path="publishedMoment" />
				<form:hidden path="isFinal" />
				<form:hidden path="fixUpTask" />

				<form:label path="body">
					<spring:message code="tapu.body" />
				</form:label>
				<form:textarea path="body" />
				<form:errors cssClass="error" path="body" />
				<br />
				<br />

				<form:label path="photoLink">
					<spring:message code="tapu.photo.link" />
				</form:label>
				<form:textarea path="photoLink" />
				<form:errors cssClass="error" path="photoLink" />
				<br />
				<br />


				<spring:message code="tapu.save.final" var="savetapuFinal" />
				<spring:message code="tapu.delete" var="deletetapu" />
				<spring:message code="tapu.confirm.delete" var="confirmDeletetapu" />
				<spring:message code="tapu.cancel" var="canceltapu" />
				<spring:message code="tapu.save" var="savetapu" />

				<jstl:if test="${tapu.isFinal == false}">
					<input type="submit" id="sumbit" name="saveFinal"
						value="${savetapuFinal}" />&nbsp;
	</jstl:if>
				<jstl:if test="${tapu.isFinal == false}">
					<input type="submit" id="submit" name="saveDraft"
						value="${savetapu}" />
				</jstl:if>
				<jstl:if test="${tapu.id != 0}">

					<input type="submit" name="delete" value="${deletetapu}"
						onclick="return confirm('${confirmDeletetapu}')" />&nbsp;
	</jstl:if>
				<input type="button" name="cancel" value="${canceltapu}"
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
				<spring:message code="tapu.nopermission" />
			</h3>
		</jstl:otherwise>
	</jstl:choose>
</security:authorize>