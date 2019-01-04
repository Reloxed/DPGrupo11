

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

<form:form action="${actionURI}" modelAttribute="ranger" id="form" >


	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<form:hidden path="isSuspicious"/>
	<form:hidden path="isBanned"/>
		
		
	
	<form:hidden path="folders" />
	
	
	<form:hidden path="sentMessages"/>
	<form:hidden path="receivedMessages"/>
	<form:hidden path="CV"/>
	<form:hidden path="trips"/>
	
	<form:hidden path="socialIdentities"/>

	<form:label path="name">
		<spring:message code="ranger.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	<br />
	
	
	<form:label path="surname">
		<spring:message code="ranger.surname" />:
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />
	<br />
	
	
	<form:label path="email">
		<spring:message code="ranger.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	<br />
	
	<form:label path="phone">
		<spring:message code="ranger.phone" />:
	</form:label>
	<spring:message code="ranger.phonePlaceholder" var="placeholder" />
	<form:input path="phone" id="phone" placeholder="${placeholder }" size="30"/>
	<form:errors cssClass="error" path="phone" />
	<br />
	<br />
	
	<form:label path="address">
		<spring:message code="ranger.address" />:
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />
	<br />
	
	<jstl:choose>
	<jstl:when test="${ranger.id == 0}">
	
	<form:hidden path="userAccount.authorities[0].authority" value="RANGER" />
	
	
	<form:label path="userAccount.username">
		<spring:message code="ranger.username" />:
	</form:label>
	<spring:message code="ranger.username.placeholder" var="usernamePlaceholder"/> 
	<form:input path="userAccount.username" placeholder="${usernamePlaceholder}" size="25"/>
	<form:errors cssClass="error" path="userAccount.username" />
	<br />
	<br />
	
	
	
	<form:label path="userAccount.password">
	<spring:message code="ranger.password" />:
	</form:label>
	<spring:message code="ranger.password.placeholder" var="passwordPlaceholder"/> 
	<form:password path="userAccount.password" placeholder="${passwordPlaceholder}" size="25"/>
	<form:errors cssClass="error" path="userAccount.password" />
	<br />
	<br />
	

	
	
	<form:hidden path="userAccount.isBanned" value="false" />
	
	
	</jstl:when>
	<jstl:otherwise>
	
	
	<form:hidden path="userAccount"/>
	

	</jstl:otherwise>
	</jstl:choose>
	

	<input type="button" name="save" id="save"
		value="<spring:message code="ranger.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="ranger.cancel" />"
		onclick="javascript: relativeRedir('${redirectURI}');" />
	<br />

</form:form>



<script type="text/javascript">
$("#save").on("click",function(){validatePhone("<spring:message code='ranger.confirmationPhone'/>","${countryCode}");});


	 
</script>

</jstl:if>

<jstl:if test="${!permission }">
<h3><spring:message code="ranger.nopermission" /></h3>
</jstl:if>




