<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%-- <jstl:choose> --%>
<jstl:if test="${m.id==0}">

	<form:form action="message/create.do" modelAttribute="m" id="form">
		<form:hidden path="id" />
		<form:hidden path="version" />

		<form:hidden path="sendMoment" value="01/01/2001 00:00"/>

		<form:label path="subject">
			<spring:message code="message.subject" />
		</form:label>
		<spring:message code="message.subject.placeholder" var="placeholder" />
		<form:input placeholder="${placeholder}" path="subject"/>
		<form:errors cssClass="error" path="subject" />
		<br />

		<form:label path="body">
			<spring:message code="message.body" />
		</form:label>
		<spring:message code="message.body.placeholder" var="placeholder" />
		<form:textarea placeholder="${placeholder}" path="body" />
		<form:errors cssClass="error" path="body" />
		<br />

		<form:label path="priority">
			<spring:message code="message.priority" />
		</form:label>
		<form:radiobutton path="priority" value="LOW" />
		<spring:message code="message.priority.low" />
		<form:radiobutton path="priority" value="NEUTRAL" checked="checked" />
		<spring:message code="message.priority.neutral" />
		<form:radiobutton path="priority" value="HIGH" />
		<spring:message code="message.priority.high" />
		<form:errors cssClass="error" path="priority" />
		<br />

		<form:label path="tags">
			<spring:message code="message.tags" />
		</form:label>
		<spring:message code="message.tags.placeholder" var="placeholder" />
		<form:input placeholder="${placeholder}" path="tags" />
		<form:errors cssClass="error" path="tags" />
		<br />

		<form:hidden path="sender" />
		<form:errors cssClass="error" path="sender" />

		<jstl:choose>
			<jstl:when test="${broadcast}">
				<!-- Aqui van los recipients si es broadcast -->
				<form:hidden path="recipients" />
			</jstl:when>
			<jstl:otherwise>
				<form:label path="recipients">
					<spring:message code="message.recipients" />
				</form:label>
				<form:select path="recipients" multiple="true" itemValue="id">
					<form:options items="${actors }" itemLabel="userAccount.username"/>
				</form:select>
				<form:errors cssClass="error" path="recipients" />
				<br />
			</jstl:otherwise>
		</jstl:choose>

		<form:hidden path="isSpam" />
		<form:errors cssClass="error" path="isSpam" />
		<form:hidden path="messageBoxes" />
		<form:errors cssClass="error" path="messageBoxes" />

		<input type="submit" name="save" id="save" onclick="checkPhone()"
			value="<spring:message code="message.save" />" />
		<input type="button" name="cancel" id="cancel"
			onclick="javascript: relativeRedir('message/list.do');"
			value="<spring:message code="message.cancel" />" />
	</form:form>

</jstl:if>

 	<jstl:if test="${m.id!=0}">
		<form:hidden path="sendMoment" />
		<form:hidden path="subject" />
		<form:hidden path="body" />
		<form:hidden path="priority" />
		<form:hidden path="tags" />
		<form:hidden path="sender" />
		<form:hidden path="recipients" />
		<form:hidden path="isSpam" />

		<jstl:if test="${move}">

			<form:label path="messageBoxes">
				<spring:message code="message.boxes.move" />
			</form:label>
			<form:select path="messageBoxes">
				<jstl:forEach var="x" items="${listMessageBoxes}">
					<form:option value="${x.name}" />
					<jstl:out value="${x.name }" />
				</jstl:forEach>
			</form:select>
			<form:errors cssClass="error" path="messageBoxes" />
			<br />
		</jstl:if>

		<jstl:if test="${copy}">
			<form:label path="messageBoxes">
				<spring:message code="message.boxes.copy" />
			</form:label>
			<form:select path="messageBoxes">
				<jstl:forEach var="x" items="${listMessageBoxes}">
					<form:option value="${x.name}" />
					<jstl:out value="${x.name }" />
				</jstl:forEach>
			</form:select>
			<form:errors cssClass="error" path="messageBoxes" />
			<br />
		</jstl:if>

	</jstl:if>

	<jstl:if test="${(m.id !=0) && (display)}">

		<form:form action="message/edit.do" modelAttribute="message" id="form"
			readonly="true">
			<form:hidden path="id" />
			<form:hidden path="version" />

			<spring:message code="message.display.sendMoment" />
			<form:input path="sendMoment" value="" />
			<br />

			<spring:message code="message.display.subject" />
			<form:input path="subject" value="" />
			<br />

			<spring:message code="message.display.body" />
			<form:textarea path="body" value="" />
			<br />

			<spring:message code="message.display.priority" />
			<form:input path="priority" value="" />
			<br />

			<spring:message code="message.display.tags" />
			<form:input path="tags" value="" />
			<br />

			<form:hidden path="sender" />

			<spring:message code="message.display.recipients" />
			<form:input path="recipients" value="" />
			<br />

			<form:hidden path="isSpam" />
			<form:hidden path="messageBoxes" />

			<input type="submit" name="save" id="save" />

			<input type="button" name="cancel" id="cancel"
				onclick="window.history.back()"
				value="<spring:message code="message.display.back" />" />
			<br />
		</form:form>


	</jstl:if>
