

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<security:authorize access="hasRole('ADMIN')">
	
	
	<%-- Tabla para SuspiciousRangers --%>
	<h3><spring:message code="actor.listRangers" /></h3>
	
	<table class="displayStyle">
	<tr>
	<th><spring:message code="actor.listRangers" /></th>
	<tr>
	
	<tr>
	<td>
		<display:table pagesize="5" class="displaytag" 
			name="suspiciousRangers" requestURI="actor/administrator/listSuspicious.do" id="suspiciousRanger">
		
		<spring:message code="actor.userAccount.username" var="username" />
		<display:column property="userAccount.username" title="${username}" sortable="true"/>
		
		<spring:message code="actor.name" var="name" />
		<display:column property="name" title="${name}" sortable="true"/>
		
		<spring:message code="actor.surname" var="surname" />
		<display:column property="surname" title="${surname}" sortable="true"/>
		
		<display:column>
			<a href="actor/display.do?actorId=${suspiciousRanger.id }"><spring:message code="actor.displayRanger" /></a>
		</display:column>
		
		<display:column>
			<jstl:if test="${!suspiciousRanger.isBanned}">
				
				<input type="button" name="ban"
			value="<spring:message code="actor.banRanger" />"
			onclick="redirect: location.href = 'actor/administrator/ban.do?rangerId=${suspiciousRanger.id}';" />
			
			
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${suspiciousRanger.isBanned}">
				
				<input type="button" name="unban"
			value="<spring:message code="actor.unbanRanger" />"
			onclick="redirect: location.href = 'actor/administrator/unban.do?rangerId=${suspiciousRanger.id}';" />
			
			
			</jstl:if>
		</display:column>
		</display:table>
	</td>
	</tr>
	</table>
	
	
	<%-- Tabla para SuspiciousManagers --%>
	<h3><spring:message code="actor.listManagers" /></h3>
	
	<table class="displayStyle">
	<tr>
	<th><spring:message code="actor.listManagers"  /></th>
	<tr>
	
	<tr>
	<td>
		<display:table pagesize="5" class="displaytag" 
			name="suspiciousManagers" requestURI="actor/administrator/listSuspicious.do" id="suspiciousManager">
	
		
		<spring:message code="actor.userAccount.username" var="username" />
		<display:column property="userAccount.username" title="${username}" sortable="true"/>
		
		<spring:message code="actor.name" var="name" />
		<display:column property="name" title="${name}" sortable="true"/>
		
		<spring:message code="actor.surname" var="surname" />
		<display:column property="surname" title="${surname}" sortable="true"/>
		
		<display:column>
			<a href="actor/display.do?actorId=${suspiciousManager.id }"><spring:message code="actor.displayManager" /></a>
		</display:column>
		
		<display:column>
			<jstl:if test="${!suspiciousManager.isBanned}">
				
				<input type="button" name="ban"
			value="<spring:message code="actor.banManager" />"
			onclick="redirect: location.href = 'actor/administrator/ban.do?managerId=${suspiciousManager.id}';" />
			
			
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${suspiciousManager.isBanned}">
				
				<input type="button" name="unban"
			value="<spring:message code="actor.unbanManager" />"
			onclick="redirect: location.href = 'actor/administrator/unban.do?managerId=${suspiciousManager.id}';" />
			
			
			</jstl:if>
		</display:column>
		</display:table>
	</td>
	</tr>
	</table>
	
		<%-- Tabla para SuspiciousExplorers --%>
	<h3><spring:message code="actor.listExplorers" /></h3>
	
	<table class="displayStyle">
	<tr>
	<th><spring:message code="actor.listExplorers"  /></th>
	<tr>
	
	<tr>
	<td>
	
		<display:table pagesize="5" class="displaytag" 
			name="suspiciousExplorers" requestURI="actor/administrator/listSuspicious.do" id="suspiciousExplorer">
			
		
		<spring:message code="actor.userAccount.username" var="username" />
		<display:column property="userAccount.username" title="${username}" sortable="true"/>
		
		<spring:message code="actor.name" var="name" />
		<display:column property="name" title="${name}" sortable="true"/>
		
		<spring:message code="actor.surname" var="surname" />
		<display:column property="surname" title="${surname}" sortable="true"/>
		
		<display:column>
			<a href="actor/display.do?actorId=${suspiciousExplorer.id }"><spring:message code="actor.displayExplorer" /></a>
		</display:column>
		
		<display:column>
			<jstl:if test="${!suspiciousExplorer.isBanned}">
				
				<input type="button" name="ban"
			value="<spring:message code="actor.banExplorer" />"
			onclick="redirect: location.href = 'actor/administrator/ban.do?explorerId=${suspiciousExplorer.id}';" />
			
			
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${suspiciousExplorer.isBanned}">
				
				<input type="button" name="unban"
			value="<spring:message code="actor.unbanExplorer" />"
			onclick="redirect: location.href = 'actor/administrator/unban.do?explorerId=${suspiciousExplorer.id}';" />
			
			
			</jstl:if>
		</display:column>
		</display:table>
	</td>
	</tr>
	</table>
	
	
	<%-- Tabla para SuspiciousAuditors --%>
	<h3><spring:message code="actor.listAuditors" /></h3>
	
	<table class="displayStyle">
	<tr>
	<th><spring:message code="actor.listAuditors"  /></th>
	<tr>
	
	<tr>
	<td>
	
		<display:table pagesize="5" class="displaytag" 
			name="suspiciousAuditors" requestURI="actor/administrator/listSuspicious.do" id="suspiciousAuditor">
		
		
		<spring:message code="actor.userAccount.username" var="username" />
		<display:column property="userAccount.username" title="${username}" sortable="true"/>
		
		<spring:message code="actor.name" var="name" />
		<display:column property="name" title="${name}" sortable="true"/>
		
		<spring:message code="actor.surname" var="surname" />
		<display:column property="surname" title="${surname}" sortable="true"/>
		
		<display:column>
			<a href="actor/display.do?actorId=${suspiciousAuditor.id }"><spring:message code="actor.displayAuditor" /></a>
		</display:column>
		
		<display:column>
			<jstl:if test="${!suspiciousAuditor.isBanned}">
				
				<input type="button" name="ban"
			value="<spring:message code="actor.banAuditor" />"
			onclick="redirect: location.href = 'actor/administrator/ban.do?auditorId=${suspiciousAuditor.id}';" />
			
			
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${suspiciousAuditor.isBanned}">
				
				<input type="button" name="unban"
			value="<spring:message code="actor.unbanAuditor" />"
			onclick="redirect: location.href = 'actor/administrator/unban.do?auditorId=${suspiciousAuditor.id}';" />
			
			
			</jstl:if>
		</display:column>
		</display:table>
	</td>
	</tr>
	</table>
	
		<%-- Tabla para SuspiciousSponsors --%>
	<h3><spring:message code="actor.listSponsors" /></h3>
	
	<table class="displayStyle">
	<tr>
	<th><spring:message code="actor.listSponsors"  /></th>
	<tr>
	
	<tr>
	<td>
	
		<display:table pagesize="5" class="displaytag" 
			name="suspiciousSponsors" requestURI="actor/administrator/listSuspicious.do" id="suspiciousSponsor">
		
		
		<spring:message code="actor.userAccount.username" var="username" />
		<display:column property="userAccount.username" title="${username}" sortable="true"/>
		
		<spring:message code="actor.name" var="name" />
		<display:column property="name" title="${name}" sortable="true"/>
		
		<spring:message code="actor.surname" var="surname" />
		<display:column property="surname" title="${surname}" sortable="true"/>
		
		<display:column>
			<a href="actor/display.do?actorId=${suspiciousSponsor.id }"><spring:message code="actor.displaySponsor" /></a>
		</display:column>
		
		<display:column>
			<jstl:if test="${!suspiciousSponsor.isBanned}">
				
				<input type="button" name="ban"
			value="<spring:message code="actor.banSponsor" />"
			onclick="redirect: location.href = 'actor/administrator/ban.do?sponsorId=${suspiciousSponsor.id}';" />
			
			
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${suspiciousSponsor.isBanned}">
				
				<input type="button" name="unban"
			value="<spring:message code="actor.unbanSponsor" />"
			onclick="redirect: location.href = 'actor/administrator/unban.do?sponsorId=${suspiciousSponsor.id}';" />
			
			
			</jstl:if>
		</display:column>
		</display:table>
	
	</td>
	</tr>
	</table>
	
</security:authorize>
