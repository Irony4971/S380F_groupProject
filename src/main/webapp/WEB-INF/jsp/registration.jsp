<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>
</head>
<body>
<h2>User Registration</h2>

<c:if test="${not empty param.error}">
    <p style="color:red">Error: Username already exists!</p>
</c:if>

<form action="<c:url value='/register'/>" method="post">
    <div>
        <label>Username:</label>
        <input type="text" name="username" required/>
    </div>
    <div>
        <label>Password:</label>
        <input type="password" name="password" required/>
    </div>
    <div>
        <label>Full Name:</label>
        <input type="text" name="fullName" required/>
    </div>
    <div>
        <label>Email:</label>
        <input type="email" name="email" required/>
    </div>
    <div>
        <label>Phone:</label>
        <input type="tel" name="phone" required/>
    </div>

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="submit">Register</button>
</form>

<a href="/login">Back to Login</a>
</body>
</html>