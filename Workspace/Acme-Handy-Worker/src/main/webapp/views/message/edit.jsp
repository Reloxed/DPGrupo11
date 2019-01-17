<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${(mensaje.id == 0) && (!broadcast)}">
	<form:form action="message/actor/edit.do" modelAttribute="mensaje">
		<!-- Se ponen estos atributos en hidden ya que en el servicio de mensaje al crearlo obtienen unos valores -->
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="isSpam" />
		<form:hidden path="sender" />
		<form:hidden path="sendMoment" />
		<form:hidden path="messageBoxes" />


		<form:label path="recipient">
			<spring:message code="message.recipient.userAccount" />:
	</form:label>
		<form:select path="recipient">
			<form:option label="-----" value="0" />
			<form:options items="${recipients}" itemLabel="userAccount.username"
				itemValue="id" />
		</form:select>
		<form:errors cssClass="error" path="recipient" />
		<br />
		<br />


		<form:label path="subject">
			<spring:message code="message.subject" />
		</form:label>
		<form:input path="subject" />
		<form:errors cssClass="error" path="subject" />
		<br />
		<br />

		<form:label path="body">
			<spring:message code="message.body" />
		</form:label>
		<form:textarea path="body" />
		<form:errors cssClass="error" path="body" />
		<br />
		<br />

		<form:label path="priority">
			<spring:message code="message.priority" />
		</form:label>
		<form:radiobutton path="priority" value="HIGH" />
		<spring:message code="message.priority.high" />
		<form:radiobutton path="priority" value="NEUTRAL" checked="checked" />
		<spring:message code="message.priority.neutral" />
		<form:radiobutton path="priority" value="LOW" />
		<spring:message code="message.priority.low" />
		<br />
		<br />

		<jstl:if test="${mensaje.id == 0}">
			<input type="submit" name="save"
				value="<spring:message code="message.send"/>" />&nbsp;
  	</jstl:if>

		<jstl:if test="${(mensaje.id == 0) && (broadcast)}">
			<input type="submit" name="save"
				value="<spring:message code="message.broadcast"/>" />

		</jstl:if>

		<input type="button" name="cancel"
			value="<spring:message code="message.cancel"/>"
			onclick="javascript: relativeRedir('/box/actor/list.do');" />
		<br />


	</form:form>



</jstl:if>

<jstl:if test="${possible}">

	<jstl:if test="${mensaje.id != 0 && !(broadcast)}">
		<form:form action="message/actor/edit.do" modelAttribute="mensaje">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="isSpam" />
			<form:hidden path="sender" />
			<form:hidden path="sendMoment" />
			<form:hidden path="recipient" />
			<form:hidden path="priority" />
			<form:hidden path="subject" />
			<form:hidden path="body" />

			<fieldset>
				<legend>
					<spring:message code="message.legend" />
				</legend>

				<form:label path="sender">
					<spring:message code="message.sender.userAccount" />:
	</form:label>
				<input type="text" value="${mensaje.sender.userAccount.username}"
					disabled /> <br /> <br />


				<form:label path="recipient">
					<spring:message code="message.recipient.userAccount" />:
	
	</form:label>
				<input type="text" value="${mensaje.recipient.userAccount.username}"
					disabled />
			</fieldset>
			<br />
			<br />


			<form:label path="sendMoment">
				<spring:message code="message.sendMoment" />
			</form:label>
			<input type="text" value="${mensaje.sendMoment}" disabled />
			<br />
			<br />

			<form:label path="subject">
				<spring:message code="message.subject" />
			</form:label>
			<input type="text" value="${mensaje.subject}" disabled />
			<br />
			<br />

			<form:label path="body">
				<spring:message code="message.body" />
			</form:label>
			<input type="text" value="${mensaje.body}" disabled />
			<br />
			<br />

			<form:label path="messageBoxes">
				<spring:message code="message.box" />:
		
			</form:label>
			<form:select path="messageBoxes">
				<form:option label="----" value="0" />
				<form:options items="${boxes}" itemLabel="name" itemValue="id" />
			</form:select>
			<form:errors cssClass="error" path="messageBoxes" />
			<br />
			<br />

			<input type="submit" name="move"
				value="<spring:message code="message.move"/>" />&nbsp;
		
	<input type="submit" name="delete"
				value="<spring:message code="message.delete"/>"
				onclick="return confirm('<spring:message code="message.confirm.delete"/>') " />&nbsp;
		
	<input type="button" name="cancel"
				value="<spring:message code="message.cancel" />"
				onclick="javascript: relativeRedir('/box/actor/list.do');" />

			<br />
		</form:form>

	</jstl:if>


</jstl:if>



<jstl:if test="${!possible}">
	<h3>
		<spring:message code="message.nopermission" />
	</h3>
</jstl:if>