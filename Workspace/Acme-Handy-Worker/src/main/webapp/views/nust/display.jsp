<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<security:authorize
	access="hasAnyRole('ADMINISTRATOR','CUSTOMER','HANDYWORKER')">
	
	
	<table class="displayStyle">

		<tr>
			<td><strong> <spring:message
						code="nust.ticker" /> :
			</strong></td>
			<td><jstl:out value="${nust.ticker}">
				</jstl:out></td>
		</tr>
		 <jstl:if test="${language==english}">
		<tr>
			<td><strong> <spring:message
						code="nust.publishedMoment" /> :
			</strong></td>
			
			<td>
			<fmt:formatDate value="${nust.publishedMoment}" pattern="yyyy/MM/dd HH:mm" />
			
				</td>
		</tr>
		</jstl:if>
		
		<jstl:if test="${language==español}">
		<tr>
			<td><strong> <spring:message
						code="nust.publishedMoment" /> :
			</strong></td>
			
			
			<td>
			<fmt:formatDate value="${nust.publishedMoment}" pattern="dd/MM/yyyy HH:mm" />
			
			</td>
		</tr>
		</jstl:if>
		
		<tr>
			<td><strong> <spring:message
						code="nust.body" /> :
			</strong></td>
			<td><jstl:out value="${nust.body}">
				</jstl:out></td>
		</tr>
		
		<tr>
			<td><strong> <spring:message
						code="nust.picture" /> :
			</strong></td>
			<td><jstl:out value="${nust.picture}">
				</jstl:out></td>
		</tr>
		<security:authorize
	access="hasAnyRole('CUSTOMER')">
		<jstl:if test="${nust.isFinal==false}">
				<tr>
			<td><strong> <spring:message
						code="nust.isFinal" /> :
			</strong></td>
			<td>Draft
			</td>
		</tr>
		</jstl:if>
		<jstl:if test="${nust.isFinal==true}">
				<tr>
			<td><strong> <spring:message
						code="nust.isFinal" /> :
			</strong></td>
			<td>Final
			</td>
		</tr>
		</jstl:if>
	
		
		</security:authorize>
		<tr>
			<td><strong> <spring:message
						code="nust.fixUpTask" /> :
			</strong></td>
			<td><jstl:out value="${nust.fixUpTask.description}">
				</jstl:out></td>
		</tr>
	
	
	
		</table>
		
		<input type="button" name="back"
		value="<spring:message code="fixuptask.back" />"
		onclick="window.history.back()" />


	<br />
	
		
	</security:authorize>