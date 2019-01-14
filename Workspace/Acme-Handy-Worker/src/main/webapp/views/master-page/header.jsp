<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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


<div style="float: right">
	<a href="?language=es" id="es"><img src="images/sp.png"
		style="width: 50px; height: 25px" /></a> <a href="?language=en" id="en"><img
		src="images/uk.png" style="width: 50px; height: 25px" /></a>
</div>

			
<div>	
	<a href="#"><img
		style="height: 200px; width: 500px; padding-bottom: 12px"
		src="${banner}" alt="Acme Handy Worker Co., Inc." /></a>
	
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.administrator.creations" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/administrator/create.do"><spring:message
								code="master.page.administrator.create.admin" /></a></li>
					<li><a href="referee/referee/create.do"><spring:message
								code="master.page.administrator.create.referee" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.administrator.warranties" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="warranty/administrator/list.do"><spring:message
								code="master.page.administrator.warranties.show" /></a></li>

				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.administrator.categories" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="category/administrator/list.do"><spring:message
								code="master.page.administrator.categories.show" /></a></li>
					<li><a href="category/administrator/create.do"><spring:message
								code="master.page.administrator.category.new" /></a></li>
				</ul></li>

			<li><a class="fNiv" href="statistics/administrator/display.do"><spring:message
						code="master.page.administrator.dashboard" /></a></li>

			<li><a class="fNiv"><spring:message
						code="master.page.administrator.manage" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/administrator/list-suspicious-actors.do"><spring:message
								code="master.page.list.suspicious.actors" /></a></li>
					<li><a href="system-configuration/administrator/display.do"><spring:message
								code="master.page.administrator.system.configuration" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message
						code="master.page.customer.fixuptasks" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="fixUpTask/customer/list.do"><spring:message
								code="master.page.customer.fixuptasks.show" /></a></li>
					<li><a href="fixUpTask/customer/create.do"><spring:message
								code="master.page.customer.fixuptasks.create" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.customer.complaints" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/list.do"><spring:message
								code="master.page.customer.complaints.show" /></a></li>
					<li><a href="complaint/edit.do"><spring:message
								code="master.page.customer.complaints.create" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.customer.endorsements" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="endorsement/customer/list.do"><spring:message
								code="master.page.customer.endorsements.show" /></a></li>
					<li><a href="endorsement/customer/create.do"><spring:message
								code="master.page.customer.endorsements.create" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('REFEREE')">
			<li><a class="fNiv"><spring:message
						code="master.page.referee.complaints" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/referee/listNotAssigned.do"><spring:message
								code="master.page.referee.not.assigned.complaints" /></a></li>
					<li><a href="complaint/referee/listAssigned.do"><spring:message
								code="master.page.referee.my.complaints" /></a></li>
				</ul></li>
			<li><a class="fNiv"></a>

			<li class="arrow"></li>
			<li><a href="report/referee/list.do"><spring:message
						code="master.page.referee.reports" /></a></li>
			<li><a href="note/referee/list.do"><spring:message
						code="master.page.referee.notes" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('HANDYWORKER')">
			<li><a class="fNiv"><spring:message
						code="master.page.handyworker.fixuptasks" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="fixUpTask/handyWorker/list.do"><spring:message
								code="master.page.handyworker.fixuptasks.show" /></a></li>
					<li><a href="application/customer,handy-worker/list.do"><spring:message
								code="master.page.handyworker.applications" /></a></li>
					<li><a href="finder/handyWorker/search.do"><spring:message
								code="master.page.handyworker.finder" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.handyworker.complaints" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/handyWorker/list.do"><spring:message
								code="master.page.handyworker.complaints.show" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.handyworker.tutorials" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="tutorial/list.do"><spring:message
								code="master.page.handyworker.tutorials.show" /></a></li>
					<li><a href="tutorial/edit.do"><spring:message
								code="master.page.handyworker.tutorial.create" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.handyworker.endorsements" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="endorsement/handyWorker/list.do"><spring:message
								code="master.page.handyworker.endorsements.show" /></a></li>
					<li><a href="endorsement/handyWorker/create.do"><spring:message
								code="master.page.handyworker.endorsement.create" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.sponsor.sponsorship" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsor/sponsorship-create.do"><spring:message
								code="master.page.sponsor.sponsorship.create" /></a></li>
					<li><a href="sponsor/sponsorship-show.do"><spring:message
								code="master.page.sponsor.sponsorship.show" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.signup" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/customer/create.do"><spring:message
								code="master.page.register.customer" /></a></li>
					<li><a href="handyworker/handyworker/create.do"><spring:message
								code="master.page.register.handy.worker" /></a></li>
					<li><a href="sponsor/sponsor/create.do"><spring:message
								code="master.page.register.sponsor" /></a></li>
				</ul></li>

			<li><a class="fNiv" href="tutorial/list.do"><spring:message
						code="master.page.see.all.tutorials" /></a></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/display.do"><spring:message
								code="master.page.profile.view" /></a></li>
					<li><a href="message-box/list.do"><spring:message
								code="master.page.profile.message.boxes" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>
