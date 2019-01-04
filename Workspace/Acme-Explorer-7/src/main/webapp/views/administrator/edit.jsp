

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

<form:form action="${actionURI}" modelAttribute="administrator" id="form" >


	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<form:hidden path="isSuspicious"/>
	<form:hidden path="isBanned"/>
		
		
	
	<form:hidden path="folders" />
	
	
	<form:hidden path="sentMessages"/>
	<form:hidden path="receivedMessages"/>
	
	
	<form:hidden path="socialIdentities"/>

	<form:label path="name">
		<spring:message code="administrator.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	<br />
	
	
	<form:label path="surname">
		<spring:message code="administrator.surname" />:
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />
	<br />
	
	
	<form:label path="email">
		<spring:message code="administrator.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	<br />
	
	<form:label path="phone">
		<spring:message code="administrator.phone" />:
	</form:label>
	<spring:message code="administrator.phonePlaceholder" var="placeholder" />
	<form:input path="phone" id="phone" placeholder="${placeholder }" size="30"/>
	<form:errors cssClass="error" path="phone" />
	<br />
	<br />
	
	<form:label path="address">
		<spring:message code="administrator.address" />:
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />
	<br />
	
	<jstl:choose>
	<jstl:when test="${administrator.id == 0}">
	
	<form:hidden path="userAccount.authorities[0].authority" value="ADMIN" />
	
	
	<form:label path="userAccount.username">
		<spring:message code="administrator.username" />:
	</form:label>
	<spring:message code="administrator.username.placeholder" var="usernamePlaceholder"/> 
	<form:input path="userAccount.username" placeholder="${usernamePlaceholder}" size="25"/>
	<form:errors cssClass="error" path="userAccount.username" />
	<br />
	<br />
	
	
	
	<form:label path="userAccount.password">
	<spring:message code="administrator.password" />:
	</form:label>
	<spring:message code="administrator.password.placeholder" var="passwordPlaceholder"/> 
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
		value="<spring:message code="administrator.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="administrator.cancel" />"
		onclick="javascript: relativeRedir('${redirectURI}');" />
	<br />

</form:form>


<script type="text/javascript">
$("#save").on("click",function(){validatePhone("<spring:message code='administrator.confirmationPhone'/>","${countryCode}");});


	 
</script>


</jstl:if>

<jstl:if test="${!permission }">
<h3><spring:message code="administrator.nopermission" /></h3>
</jstl:if>




