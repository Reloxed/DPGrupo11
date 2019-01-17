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

<a href="message/actor/create.do"> <spring:message
		code="box.writeMessage" />
</a>
<br />

<security:authorize access="hasRole('ADMINISTRATOR')">
	<a href="message/administrator/broadcast.do"> <spring:message
			code="box.broadcast" />
	</a>
	<br>
</security:authorize>

<jstl:if test="${empty actualMessageBox.name}">
	<a href="box/actor/create.do"> <spring:message
			code="messageBox.createBox" />
	</a>
</jstl:if>


<h3>
	<spring:message code="messageBox.listBoxes" />
</h3>

<jstl:if test="${empty actualMessageBox.name}">
	<display:table pagesize="10" class="displaytag" name="boxes"
		requestURI="box/actor/list.do" id="box">

		<display:column>
			<a href="box/actor/list.do?messageBoxId=${box.id}"> <spring:message
					code="messageBox.show" />
			</a>
		</display:column>

		<spring:message code="messageBox.name" var="name" />
		<display:column property="name" title="${name}" sortable="true" />

		<display:column>
			<jstl:if test="${!box.isPredefined }">
				<a href="box/actor/edit.do?messageBoxId=${box.id}"> <spring:message
						code="messageBox.editBox" />
				</a>
			</jstl:if>
		</display:column>




	</display:table>

</jstl:if>

<jstl:if test="${not empty actualMessageBox.name}">

	<h3>
		<spring:message code="messageBox.actual" />
		: ${actualMessageBox.name }
	</h3>

	<br>

	<h3>
		<spring:message code="messageBox.listMessages" />
		${actualMessageBox.name}
	</h3>

	<jstl:if test="${empty messages }">
		<spring:message code="messageBox.messages.empty" var="messagesEmpty" />
		<jstl:out value="${messagesEmpty }"></jstl:out>
	</jstl:if>

	<jstl:if test="${not empty messages }">
		<display:table pagesize="10" class="displayag" name="messages"
			requestURI="box/actor/list.do" id="message">
			<jstl:if test="${actualMessageBox.name != 'Out box'}">
				<spring:message code="messageBox.message.sender" var="sender" />
				<display:column title="${sender}" sortable="true">

					<jstl:if test="${message.sender != message.recipient}">
						<jstl:out value="${message.sender.userAccount.username}"></jstl:out>
					</jstl:if>

				</display:column>


				<jstl:if
					test="${(actualMessageBox.name == 'Out box') ||
					(!actualMessageBox.isPredefined) }">

					<spring:message code="messageBox.message.recipient" var="recipient" />
					<display:column title="${recipient}" sortable="true">

						<jstl:if test="${message.sender != message.recipient}">
							<jstl:out value="${message.recipient.userAccount.username}"></jstl:out>
						</jstl:if>

						<jstl:if
							test="${(message.sender == message.recipient) && 
						(message.sender.userAccount.authorities[0]).authority == 'ADMIN' }">
							<spring:message code="messageBox.message.broadcast"
								var="broadcast" />
							<jstl:out value="${broadcast }"></jstl:out>
						</jstl:if>
					</display:column>
				</jstl:if>

			</jstl:if>

			<spring:message code="messageBox.message.sendMoment" var="sendMoment" />
			<display:column property="sendMoment" title="${sendMoment}"
				sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

			<spring:message code="messageBox.message.subject" var="subject" />
			<display:column property="subject" title="${subject}"
				sortable="false" />

			<spring:message code="messageBox.message.body" var="body" />
			<display:column property="body" title="${body}" sortable="false" />

			<spring:message code="messageBox.message.priority" var="priority" />
			<display:column property="priority" title="${priority}"
				sortable="true" />

			<display:column>
				<a href="message/actor/edit.do?messageId=${message.id}"> <spring:message
						code="messageBox.message.edit" />
				</a>
			</display:column>
		</display:table>
	</jstl:if>
	<br>
	<br>
	<jstl:if test="${!actualMessageBox.isPredefined}">
		<input type="button" name="editBox"
			value="<spring:message code="messageBox.editMessageBox" /> "
			onclick="redirect: location.href = 'box/actor/edit.do?messageBoxId=${actualMessageBox.id}';" />

	</jstl:if>
</jstl:if>

<br>
<br>



