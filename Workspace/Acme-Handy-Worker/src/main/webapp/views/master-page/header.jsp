<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="images/FixItFelix icon.jpeg" alt="Acme Handy Worker Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator.creations" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/create-admin.do"><spring:message code="master.page.administrator.create.admin" /></a></li>
					<li><a href="administrator/create-referee.do"><spring:message code="master.page.administrator.create.referee" /></a></li>					
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.administrator.warranties" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/warranties.do"><spring:message code="master.page.administrator.warranties.show" /></a></li>
					<li><a href="administrator/warranty-create.do"><spring:message code="master.page.administrator.warranty.new" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.administrator.categories" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/categories.do"><spring:message code="master.page.administrator.categories.show" /></a></li>
					<li><a href="administrator/category-create.do"><spring:message code="master.page.administrator.category.new" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.administrator.statistics" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/statistics-1.do"><spring:message code="master.page.administrator.statistics.1" /></a></li>
					<li><a href="administrator/statistics-2.do"><spring:message code="master.page.administrator.statistics.2" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.administrator.manage" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/suspicious-show.do"><spring:message code="master.page.administrator.suspicious.show" /></a></li>
					<li><a href="administrator/system-configuration.do"><spring:message code="master.page.administrator.system.configuration" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer.fixuptasks" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/fixuptasks-customer.do"><spring:message code="master.page.customer.fixuptasks.show" /></a></li>
					<li><a href="customer/fixuptask-create.do"><spring:message code="master.page.customer.fixuptasks.create" /></a></li>					
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.customer.complaints" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/complaints.do"><spring:message code="master.page.customer.complaints.show" /></a></li>
					<li><a href="customer/complaint-create.do"><spring:message code="master.page.customer.complaints.create" /></a></li>					
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.customer.endorsements" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/endorsements.do"><spring:message code="master.page.customer.endorsements.show" /></a></li>
					<li><a href="customer/endorsement-create.do"><spring:message code="master.page.customer.endorsement.create" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('REFEREE')">
			<li><a class="fNiv"><spring:message	code="master.page.referee.complaints" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="referee/complaints.do"><spring:message code="master.page.referee.not.assigned.complaints" /></a></li>
					<li><a href="referee/my-complaints.do"><spring:message code="master.page.referee.my.complaints" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('HANDYWORKER')">
			<li><a class="fNiv"><spring:message	code="master.page.handyworker.fixuptasks" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="handyworker/fixuptasks.do"><spring:message code="master.page.handyworker.fixuptasks.show" /></a></li>
					<li><a href="handyworker/applications.do"><spring:message code="master.page.handyworker.applications.create"/></a></li>
					<li><a href="handyworker/finder.do"><spring:message code="master.page.handyworker.finder"/></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.handyworker.complaints" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="handyworker/complaints.do"><spring:message code="master.page.handyworker.complaints.show" /></a></li>					
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.handyworker.tutorials" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="handyworker/tutorials.do"><spring:message code="master.page.handyworker.tutorials.show" /></a></li>					
					<li><a href="handyworker/tutorial-create.do"><spring:message code="master.page.handyworker.tutorial.create" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/action-1.do"><spring:message code="master.page.profile.action.1" /></a></li>
					<li><a href="profile/action-2.do"><spring:message code="master.page.profile.action.2" /></a></li>
					<li><a href="profile/action-3.do"><spring:message code="master.page.profile.action.3" /></a></li>					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

