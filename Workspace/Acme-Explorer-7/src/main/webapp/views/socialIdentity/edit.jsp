

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jstl:if test="${permission }">

<form:form action="socialIdentity/actor/edit.do" modelAttribute="socialIdentity"  >


	<form:hidden path="id"/>
	<form:hidden path="version"/>
	

	<form:label path="nick">
		<spring:message code="socialIdentity.nick" />:
	</form:label>
	<form:input path="nick" />
	<form:errors cssClass="error" path="nick" />
	<br />
	<br />
	
	<form:label path="socialNetwork">
		<spring:message code="socialIdentity.socialNetwork" />:
	</form:label>
	<form:input path="socialNetwork" />
	<form:errors cssClass="error" path="socialNetwork" />
	<br />
	<br />
	
		<form:label path="linkProfile">
		<spring:message code="socialIdentity.linkProfile" />:
	</form:label>
	<form:input path="linkProfile" />
	<form:errors cssClass="error" path="linkProfile" />
	<br />
	<br />
	
	
		<form:label path="linkPhoto">
		<spring:message code="socialIdentity.linkPhoto" />:
	</form:label>
	<form:input path="linkPhoto" />
	<form:errors cssClass="error" path="linkPhoto" />
	<br />
	<br />



	<spring:message code="socialIdentity.save" var="saveSocialIdentity" />
	<spring:message code="socialIdentity.delete" var="deleteSocialIdentity" />
	<spring:message code="socialIdentity.confirm.delete" var="confirmSocialIdentity" />
	<spring:message code="socialIdentity.cancel" var="cancelSocialIdentity" />

	<input type="submit" name="save" value="${saveSocialIdentity}" />&nbsp; 
	<jstl:if test="${socialIdentity.id != 0}">
		<input type="submit" name="delete" value="${deleteSocialIdentity}"
			onclick="return confirm('${confirmSocialIdentity}')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel" value="${cancelSocialIdentity}"
		onclick="javascript: relativeRedir('actor/display.do');" />
	<br />

</form:form>

</jstl:if>

<jstl:if test="${!permission }">
<h3><spring:message code="socialIdentity.nopermission"  /></h3>
</jstl:if>



