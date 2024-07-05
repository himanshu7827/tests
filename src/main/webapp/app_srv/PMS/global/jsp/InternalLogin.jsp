<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">


</head>
<body>

	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
<sec:authorize access="hasAnyAuthority('ROLE_ADMIN','ROLE_PREVIOUS_ADMINISTRATOR')">			
<form:form method="GET" action="/PMS/admin/impersonate" >
    <label for="usernameField">User name:</label>
    <input type="text" name="username" id="usernameField" />
    <input type="submit" value="Switch User" />
				</form:form>
			
</sec:authorize>
		
		</div>
	</section>



</body>
</html>