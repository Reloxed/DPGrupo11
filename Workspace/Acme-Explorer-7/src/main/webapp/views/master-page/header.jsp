<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>


<div>
	<img src="${bannerWelcome}" alt="Acme, Inc." height="206" width="377" />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->

		<security:authorize access="isAnonymous()">

			<li><a class="fNiv"><spring:message code="master.page.trips" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="trip/list.do"><spring:message
								code="master.page.trips.list" /></a></li>
					<li><a href="category/list.do"><spring:message
								code="master.page.categories" /></a></li>
				</ul></li>


			<li><a class="fNiv"><spring:message code="master.page.actions" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="security/login.do"><spring:message
								code="master.page.login" /></a></li>
					<li><a href="explorer/create.do"><spring:message
								code="master.page.register.explorer" /></a></li>
								<li><a href="ranger/create.do"><spring:message
								code="master.page.register.ranger" /></a></li>
				</ul></li>

		</security:authorize>





		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customisation/administrator/display.do"><spring:message
								code="master.page.administrator.customisation" /></a></li>
					<li><a href="legalText/administrator/list.do"><spring:message
								code="master.page.administrator.legalTexts" /></a></li>
					<li><a href="tag/administrator/list.do"><spring:message
								code="master.page.administrator.tags" /></a></li>
					<li><a href="category/administrator/list.do"><spring:message
								code="master.page.administrator.categories" /></a></li>

				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.userAccounts" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/administrator/list.do"><spring:message
								code="master.page.userAccounts.actors" /></a></li>
					<li><a href="ranger/administrator/create.do"><spring:message
								code="master.page.userAccounts.ranger" /></a></li>
					<li><a href="manager/administrator/create.do"><spring:message
								code="master.page.userAccounts.manager" /></a></li>
					<li><a href="sponsor/administrator/create.do"><spring:message
								code="master.page.userAccounts.sponsor" /></a></li>
					<li><a href="auditor/administrator/create.do"><spring:message
								code="master.page.userAccounts.auditor" /></a></li>
					<li><a href="administrator/administrator/create.do"><spring:message
								code="master.page.userAccounts.administrator" /></a></li>	
					<li><a href="actor/administrator/listSuspicious.do"><spring:message
								code="master.page.userAccounts.listSuspicious" /></a></li>
					<li><a href="dashboard/administrator/display.do"><spring:message
								code="master.page.administrator.dashboard" /></a></li>
				</ul></li>


		</security:authorize>




		<security:authorize access="hasRole('EXPLORER')">
			<li><a class="fNiv"><spring:message
						code="master.page.explorer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="trip/explorer/list.do"><spring:message
								code="master.page.trips" /></a></li>
					<li><a href="application/explorer/list.do"><spring:message
								code="master.page.applications" /></a></li>
					<li><a href="emergencyContact/explorer/list.do"><spring:message
							code="master.page.emergencyContacts" /></a></li>
					<li><a href="story/explorer/list.do"><spring:message
								code="master.page.stories" /></a></li>
					<li><a href="finder/explorer/list.do"><spring:message
								code="master.page.finders" /></a></li>
				</ul></li>
		</security:authorize>


		<security:authorize access="hasRole('MANAGER')">
			<li><a class="fNiv"><spring:message
						code="master.page.manager" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="trip/manager/list.do"><spring:message
								code="master.page.managedTrips" /></a></li>
					<li><a href="application/manager/list.do"><spring:message
								code="master.page.applications" /></a></li>
					<li><a href="survivalClass/manager/list.do"><spring:message
								code="master.page.survivalClasses" /></a></li>
					<li><a href="note/manager/list.do"><spring:message
								code="master.page.notes" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('RANGER')">
			<li><a class="fNiv"><spring:message
						code="master.page.ranger" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="curriculum/ranger/search.do"><spring:message
								code="master.page.ranger.curriculum" /></a></li>

				</ul></li>
		</security:authorize>


		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.sponsor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsorship/sponsor/list.do"><spring:message
								code="master.page.sponsorships" /></a></li>
				</ul></li>
		</security:authorize>



		<security:authorize access="hasRole('AUDITOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.auditor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="audit/auditor/list.do"><spring:message
								code="master.page.audits" /></a></li>
					<li><a href="note/auditor/list.do"><spring:message
								code="master.page.notes" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">

			<li><a class="fNiv"><spring:message code="master.page.trips" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="trip/list.do"><spring:message
								code="master.page.trips.list" /></a></li>
					<li><a href="category/list.do"><spring:message
								code="master.page.categories" /></a></li>
				</ul></li>


			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="folder/actor/list.do"><spring:message
								code="master.page.mail" /></a></li>
					<li><a href="actor/display.do"><spring:message
								code="master.page.personalData" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>





	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

