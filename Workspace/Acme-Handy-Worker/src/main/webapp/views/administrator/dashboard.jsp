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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMINISTRATOR')">

	<jstl:choose>
	<jstl:when test="${not empty applicationsStatistics or not empty pricesStatistics or not empty complaintStatistics
		 or not empty ratioFixWithComplaints or not empty statusStatistics or not empty notesStatistics 
		 	or not empty customerStatistics or not empty customerStatistics2 or not empty handyWorkerStatistics
		 		or not empty handyWorkerStatistics2}">
	
	
	<table class="displayStyle" style="width: 50%">
		<tr>
			<th colspan="2"><spring:message
					code="administrator.observation.statistics" /></th>
		</tr>
		<tr>
			<td><spring:message code="administrator.observations.final" /></td>
			<td style="text-align: right">${ratioObservationsFinalMode}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.observations.draft" /></td>
			<td style="text-align: right">${ratioObservationsDraftMode}</td>
		</tr>
		
	</table>
	
		
	<jstl:if test="${not empty applicationsStatistics}"></jstl:if>
	<table class="displayStyle" style="width: 50%">
		<tr>
			<th colspan="2"><spring:message
					code="administrator.application.statistics" /></th>
		</tr>
		<tr>
			<td><spring:message code="administrator.maxapplperfut" /></td>
			<td style="text-align: right">${applicationsStatistics[0]}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.minapplperfut" /></td>
			<td style="text-align: right">${applicationsStatistics[1]}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.avgapplperfut" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="4" value="${applicationsStatistics[2]}" /></td>
		</tr>
		<tr>
			<td><spring:message code="administrator.devapplperfut" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="4" value="${applicationsStatistics[3]}" /></td>
		</tr>
	</table>

	<jstl:if test="${not empty pricesStatistics}"></jstl:if>
	<table class="displayStyle" style="width: 50%">
		<tr>
			<th colspan="2"><spring:message
					code="administrator.price.statistics" /></th>
		</tr>

		<tr>
			<td><spring:message code="administrator.maxpriceperfut" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="2" value="${pricesStatistics[0]}" /></td>
		</tr>
		<tr>
			<td><spring:message code="administrator.minpriceperfut" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="2" value="${pricesStatistics[1]}" /></td>
		</tr>
		<tr>
			<td><spring:message code="administrator.avgpriceperfut" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="2" value="${pricesStatistics[2]}" /></td>
		</tr>
		<tr>
			<td><spring:message code="administrator.devpriceperfut" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="4" value="${pricesStatistics[3]}" /></td>
		</tr>
	</table>

	<jstl:if test="${not empty complaintStatistics}"></jstl:if>
	<table class="displayStyle" style="width: 50%">
		<tr>
			<th colspan="2"><spring:message
					code="administrator.complaints.statistics" /></th>
		</tr>

		<tr>
			<td><spring:message code="administrator.maxcompperfut" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="4" value="${complaintStatistics[0]}" /></td>
		</tr>
		<tr>
			<td><spring:message code="administrator.mincompperfut" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="4" value="${complaintStatistics[1]}" /></td>
		</tr>
		<tr>
			<td><spring:message code="administrator.avgcompperfut" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="4" value="${complaintStatistics[2]}" /></td>
		</tr>
		<tr>
			<td><spring:message code="administrator.devcompperfut" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="4" value="${complaintStatistics[3]}" /></td>
		</tr>
		<tr>
			<td><spring:message code="administrator.ratiofutwithcomplaints" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="4" value="${ratioFixWithComplaints}" /></td>
		</tr>
	</table>

	<jstl:if test="${not empty statusStatistics}"></jstl:if>
	<table class="displayStyle" style="width: 50%">
		<tr>
			<th colspan="2"><spring:message
					code="administrator.application.status.statistics" /></th>
		</tr>

		<tr>
			<td><spring:message code="administrator.ratiopending" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="4" value="${statusStatistics[0]}" /></td>
		</tr>
		<tr>
			<td><spring:message code="administrator.ratioaccepted" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="4" value="${statusStatistics[1]}" /></td>
		</tr>
		<tr>
			<td><spring:message code="administrator.ratiorejected" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="4" value="${statusStatistics[2]}" /></td>
		</tr>
		<tr>
			<td><spring:message code="administrator.ratiopendingexpired" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="4" value="${pendingExpired}" /></td>
		</tr>
	</table>

	<jstl:if test="${not empty notesStatistics}"></jstl:if>
	<table class="displayStyle" style="width: 50%">
		<tr>
			<th colspan="2"><spring:message
					code="administrator.notes.statistics" /></th>
		</tr>

		<tr>
			<td><spring:message code="administrator.maxnotesperrep" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="4" value="${notesStatistics[0]}" /></td>
		</tr>
		<tr>
			<td><spring:message code="administrator.minnotesperrep" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="4" value="${notesStatistics[1]}" /></td>
		</tr>
		<tr>
			<td><spring:message code="administrator.avgnotesperrep" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="4" value="${notesStatistics[2]}" /></td>
		</tr>
		<tr>
			<td><spring:message code="administrator.devnotesperrep" /></td>
			<td style="text-align: right"><fmt:formatNumber
					maxFractionDigits="4" value="${notesStatistics[3]}" /></td>
		</tr>
	</table>

	<div>
	
	<jstl:if test="${not empty customerStatistics}"></jstl:if>
		<table class="displayStyle" style="width: 50%">
			<tr>
				<td><display:table pagesize="5" class="displaytag"
						name="customerStatistics"
						requestURI="administrator/dashboard/display.do"
						id="customerStatistics">

						<display:column titleKey="administrator.topthreecust"
							value="${customerStatistics.surname}, ${customerStatistics.name}"
							sortable="true" />

						<display:column titleKey="actor.profile">

							<a href="actor/display.do?actorID=${customerStatistics.id}"><spring:message
									code="actor.profile" /></a>

						</display:column>
					</display:table></td>
			</tr>
		</table>
		
		<jstl:if test="${not empty customerStatistics2}"></jstl:if>
		<table class="displayStyle" style="width: 50%">
			<tr>
				<td><display:table pagesize="5" class="displaytag"
						name="customerStatistics2"
						requestURI="administrator/dashboard/display.do"
						id="customerStatistics2">

						<display:column titleKey="administrator.topthreecustcomp"
							value="${customerStatistics2.surname}, ${customerStatistics2.name}"
							sortable="true" />

						<display:column titleKey="actor.profile">

							<a href="actor/display.do?actorID=${customerStatistics2.id}"><spring:message
									code="actor.profile" /></a>

						</display:column>
					</display:table></td>
			</tr>
		</table>
	</div>
	
	<jstl:if test="${not empty handyWorkerStatistics }">
	<table class="displayStyle" style="width: 50%">
		<tr>
			<td><display:table pagesize="5" class="displaytag"
					name="handyWorkerStatistics"
					requestURI="administrator/dashboard/display.do"
					id="handyWorkerStatistics">

					<display:column titleKey="administrator.topthreehw"
						value="${handyWorkerStatistics.surname}, ${handyWorkerStatistics.name}"
						sortable="true" />

					<display:column titleKey="actor.profile">
						<a href="actor/display.do?actorID=${handyWorkerStatistics.id}"><spring:message
								code="actor.profile" /></a>
					</display:column>
				</display:table></td>
		</tr>
	</table>
	</jstl:if>
	<jstl:if test="${not empty handyWorkerStatistics2}">
	<table class="displayStyle" style="width: 50%">
		<tr>
			<td><display:table pagesize="5" class="displaytag"
					name="handyWorkerStatistics2"
					requestURI="administrator/dashboard/display.do"
					id="handyWorkerStatistics2">

					<display:column titleKey="administrator.topthreehwcomp"
						value="${handyWorkerStatistics2.surname}, ${handyWorkerStatistics2.name}"
						sortable="true" />

					<display:column titleKey="actor.profile">

						<a href="actor/display.do?actorID=${handyWorkerStatistics2.id}"><spring:message
								code="actor.profile" /></a>

					</display:column>
				</display:table></td>
		</tr>
	</table>
	</jstl:if>
	</jstl:when>
	<jstl:otherwise>
		<p> 
			<spring:message code="dashboard.empty"/>
		</p>
	</jstl:otherwise>
	</jstl:choose>
</security:authorize>
