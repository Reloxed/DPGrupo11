<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="isAuthenticated()" var="authenticated" />

<jstl:if test="${authenticated}">
	<security:authentication property="principal.username" var="user" />
</jstl:if>

<jsp:useBean id="now" class="java.util.Date" />

<style>
<!--
.tableColorGreen {
	background-color: chartreuse;
}

.tableColorOrange {
	background-color: orange;
}

.tableColorGrey {
	background-color: grey;
}

.tableColorDefault {
	background-color: white;
}
-->
</style>

<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:choose>
		<jstl:when test="${not empty publishedxxxxs}">
			<display:table pagesize="5" class="displaytag" name="publishedxxxxs"
				requestURI="xxxx/handy-worker/list.do" id="xxxx">

				<!-- Colors & Dates -->

				<jstl:set var="pm" value="${xxxx.publishedMoment}" />

				<jstl:choose>
					<jstl:when test="${(now.time - pm.time) le 2629800000}">
						<jstl:set var="bgcolor" value="tableColorGreen" />
					</jstl:when>

					<jstl:when
						test="${(now.time - pm.time) gt 2629800000 and ((now.time - pm.time) le 5259600000)}">
						<jstl:set var="bgcolor" value="tableColorOrange" />
					</jstl:when>

					<jstl:otherwise>
						<jstl:set var="bgcolor" value="tableColorGrey" />
					</jstl:otherwise>
				</jstl:choose>

				<!-- Action links -->

				<display:column class="${bgcolor}">
					<a href="xxxx/handy-worker/display.do?xxxxID=${xxxx.id}"> <spring:message
							code="xxxx.display" />
					</a>
				</display:column>

				<!-- Attributes-->

				<display:column property="ticker" titleKey="xxxx.ticker"
					sortable="true" class="${bgcolor}" />

				<display:column property="body" titleKey="xxxx.body" sortable="true"
					class="${bgcolor}" />

				<jstl:choose>
					<jstl:when test="${language == espanyol}">
						<display:column property="publishedMoment" title="xxxx.moment"
							sortable="true" format="{0,date,dd/MM/yy HH:mm}"
							class="${bgcolor}" />
					</jstl:when>
					<jstl:otherwise>
						<display:column property="publishedMoment" titleKey="xxxx.moment"
							sortable="true" format="{0,date,yy-MM-dd HH:mm}"
							class="${bgcolor}" />
					</jstl:otherwise>
				</jstl:choose>

			</display:table>
		</jstl:when>

		<jstl:otherwise>
			<spring:message code="none.published" />
		</jstl:otherwise>

	</jstl:choose>
</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">
	<jstl:choose>
		<jstl:when test="${not empty xxxxs}">
			<display:table pagesize="5" class="displaytag" name="xxxxs"
				requestURI="xxxx/handy-worker/list.do" id="xxxx">

				<!-- Colors & Dates -->

				<jstl:set var="pm" value="${xxxx.publishedMoment}" />

				<jstl:choose>
					<jstl:when test="${(now.time - pm.time) le 2629800000}">
						<jstl:set var="bgcolor" value="tableColorGreen" />
					</jstl:when>

					<jstl:when
						test="${(now.time - pm.time) gt 2629800000 and ((now.time - pm.time) le 5259600000)}">
						<jstl:set var="bgcolor" value="tableColorOrange" />
					</jstl:when>

					<jstl:otherwise>
						<jstl:set var="bgcolor" value="tableColorGrey" />
					</jstl:otherwise>
				</jstl:choose>

				<!-- Action links -->

				<display:column class="${bgcolor}">
					<a href="xxxx/handy-worker/display.do?xxxxID=${xxxx.id}"> <spring:message
							code="xxxx.display" />
					</a>
				</display:column>

				<!-- Attributes-->

				<display:column property="body" titleKey="xxxx.body" sortable="true"
					class="${bgcolor}" />

				<jstl:choose>
					<jstl:when test="${language == espanyol}">
						<display:column property="publishedMoment" title="xxxx.moment"
							sortable="true" format="{0,date,dd-MM-yy HH:mm}"
							class="${bgcolor}" />
					</jstl:when>
					<jstl:otherwise>
						<display:column property="publishedMoment" titleKey="xxxx.moment"
							sortable="true" format="{0,date,yy/MM/dd HH:mm}"
							class="${bgcolor}" />
					</jstl:otherwise>
				</jstl:choose>

				<display:column titleKey="xxxx.is.final" sortable="true"
					class="${bgcolor}">
					<jstl:choose>
						<jstl:when test="${xxxx.isFinal}">
							<spring:message code="xxxx.final" />
						</jstl:when>
						<jstl:otherwise>
							<spring:message code="xxxx.no.final" />
						</jstl:otherwise>
					</jstl:choose>
				</display:column>
			</display:table>

			<input type="button" name="create"
				value='<spring:message code="xxxx.create"/>'
				onclick="redirect: location.href = 'xxxx/handy-worker/create.do?fixuptaskID=${xxxx.fixUpTask.id}';" />

		</jstl:when>

		<jstl:otherwise>
			<spring:message code="none.published" />
		</jstl:otherwise>

	</jstl:choose>
</security:authorize>