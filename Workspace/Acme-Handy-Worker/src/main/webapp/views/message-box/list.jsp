<%--
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h2 style="font-family: sans-serif;">
	<spring:message code="message.box.list" />
</h2>

<div style="float: right">
	<a href="message/edit.do"><spring:message
			code="message.box.new.message" /></a> <br />
</div>

<div style="float: right">
	<security:authorize access="hasRole('ADMIN')">
		<a href="message/edit.do"><spring:message
				code="message.box.broadcast" /></a>
		<br>
	</security:authorize>
</div>

<a href="message-box/edit.do"><spring:message
		code="message.box.create" /></a>
<br />

<a href="message-box/edit.do"><spring:message
		code="message.box.edit.name" /></a>

<display:table name="messageBox" id="messageBox"
	requestURI="message-box/list.do" pagesize="10" class="displaytag">

	<display:column property="name" titleKey="message.box.name"
		url="message-box/list.do" />
	<jstl:if test="${!messageBox.isPredefined }">
		<display:column property="buttons" titleKey="messages.actions">
			<a href="message/edit.do"><img src="images/edit.png" /></a>
			<a href="message/edit.do"><img src="images/delete.png" /></a>
		</display:column>
	</jstl:if>

</display:table>
<jstl:if test="${empty messages }">
	<spring:message code="messages.empty" />
</jstl:if>
<jstl:otherwise>
	<display:table name="messages" id="messages"
		requestURI="message-box/list.do" pagesize="10" class="displaytag">

		<display:column property="sender" titleKey="messages.sender" />
		<display:column property="subject" titleKey="messages.subject"
			url="message/edit.do" />
		<display:column property="buttons" titleKey="messages.actions">
			<a href="message/edit.do"><img src="images/copy.png" /></a>
			<a href="message/edit.do"><img src="images/move.png" /></a>
			<a href="message/edit.do"><img src="images/delete.png" /></a>
		</display:column>

	</display:table>
</jstl:otherwise>