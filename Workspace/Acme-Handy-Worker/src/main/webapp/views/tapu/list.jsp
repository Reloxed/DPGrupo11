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
.tableColorLime {
	background-color: Lime;
}

.tableColorGreenYellow {
	background-color: GreenYellow;
}

.tableColorSkyBlue {
	background-color: SkyBlue;
}
-->
</style>

<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:choose>
		<jstl:when test="${not empty publishedtapus}">
			<display:table pagesize="10" class="displaytag" name="publishedtapus"
				requestURI="tapu/list.do" id="tapu">

				<!-- Colors & Dates -->

				<jstl:set var="pm" value="${tapu.publishedMoment}" />

				<jstl:choose>
					<jstl:when test="${(now.time - pm.time) le 2629800000}">
						<jstl:set var="bgcolor" value="tableColorLime" />
					</jstl:when>

					<jstl:when
						test="${(now.time - pm.time) gt 2629800000 and ((now.time - pm.time) le 5259600000)}">
						<jstl:set var="bgcolor" value="tableColorGreenYellow" />
					</jstl:when>

					<jstl:otherwise>
						<jstl:set var="bgcolor" value="tableColorSkyBlue" />
					</jstl:otherwise>
				</jstl:choose>

				<!-- Action links -->

				<display:column class="${bgcolor}">
					<a href="tapu/display.do?tapuID=${tapu.id}"> <spring:message
							code="tapu.display" />
					</a>
				</display:column>

				<!-- Attributes-->

				<display:column property="ticker" titleKey="tapu.ticker"
					sortable="true" class="${bgcolor}" />

				<display:column property="body" titleKey="tapu.body" sortable="true"
					class="${bgcolor}" />

				<jstl:choose>
					<jstl:when test="${language == espanyol}">
						<display:column property="publishedMoment" title="tapu.moment"
							sortable="true" format="{0,date,dd/MM/yy HH:mm}"
							class="${bgcolor}" />
					</jstl:when>
					<jstl:otherwise>
						<display:column property="publishedMoment" titleKey="tapu.moment"
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
		<jstl:when test="${not empty tapus}">
			<display:table pagesize="10" class="displaytag" name="tapus"
				requestURI="tapu/list.do" id="tapu">

				<!-- Colors & Dates -->

				<jstl:set var="pm" value="${tapu.publishedMoment}" />

				<jstl:choose>
					<jstl:when test="${(now.time - pm.time) le 2629800000}">
						<jstl:set var="bgcolor" value="tableColorLime" />
					</jstl:when>

					<jstl:when
						test="${(now.time - pm.time) gt 2629800000 and ((now.time - pm.time) le 5259600000)}">
						<jstl:set var="bgcolor" value="tableColorGreenYellow" />
					</jstl:when>

					<jstl:otherwise>
						<jstl:set var="bgcolor" value="tableColorSkyBlue" />
					</jstl:otherwise>
				</jstl:choose>

				<!-- Action links -->

				<display:column class="${bgcolor}">
					<a href="tapu/display.do?tapuID=${tapu.id}"> <spring:message
							code="tapu.display" />
					</a>
				</display:column>

				<!-- Attributes-->

				<display:column property="ticker" titleKey="tapu.ticker"
					sortable="true" class="${bgcolor}" />

				<display:column property="body" titleKey="tapu.body" sortable="true"
					class="${bgcolor}" />

				<jstl:choose>
					<jstl:when test="${language == espanyol}">
						<display:column property="publishedMoment" title="tapu.moment"
							sortable="true" format="{0,date,dd-MM-yy HH:mm}"
							class="${bgcolor}" />
					</jstl:when>
					<jstl:otherwise>
						<display:column property="publishedMoment" titleKey="tapu.moment"
							sortable="true" format="{0,date,yy/MM/dd HH:mm}"
							class="${bgcolor}" />
					</jstl:otherwise>
				</jstl:choose>

				<display:column titleKey="tapu.is.final" sortable="true"
					class="${bgcolor}">
					<jstl:choose>
						<jstl:when test="${tapu.isFinal}">
							<spring:message code="tapu.final" />
						</jstl:when>
						<jstl:otherwise>
							<spring:message code="tapu.no.final" />
						</jstl:otherwise>
					</jstl:choose>
				</display:column>
			</display:table>

			<jstl:if test="${user == owner.userAccount.username}">

				<input type="button" name="create"
					value='<spring:message code="tapu.create"/>'
					onclick="redirect: location.href = 'tapu/create.do?fixuptaskID=${tapu.fixUpTask.id}';" />

			</jstl:if>
		</jstl:when>

		<jstl:otherwise>
			<spring:message code="none.published" />
		</jstl:otherwise>

	</jstl:choose>
</security:authorize>