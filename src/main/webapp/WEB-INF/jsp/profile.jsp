<!DOCTYPE html>
<html>
<head>
    <title>My Profile</title>
</head>
<body>
<h2>My Profile</h2>

<c:if test="${not empty param.updated}">
    <p style="color:#98d45f;">Profile updated successfully!</p>
</c:if>

<form:form action="${pageContext.request.contextPath}/updateProfile" method="post" modelAttribute="user">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <div>
        <label>Username: </label>
        <span>${user.username}</span>
    </div>

    <div>
        <label for="fullName">Full Name: </label>
        <form:input path="fullName" id="fullName"/>
    </div>

    <div>
        <label for="email">Email: </label>
        <form:input path="email" id="email" type="email"/>
    </div>

    <div>
        <label for="phone">Phone: </label>
        <form:input path="phone"  id="phone"/>
    </div>

    <div>
        <input type="password" id="password" name="newPassword"
               placeholder="Leave blank to keep current password"/>
    </div>

    <button type="submit">Update Profile</button>
    <a href="<c:url value="/course380F" />">Return to Main Page</a>
</form:form>
</body>
</html>