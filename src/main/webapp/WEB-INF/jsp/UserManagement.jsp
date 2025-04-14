<!DOCTYPE html>
<html>
<head><title>User Management</title></head>
<body>

<a href="<c:url value="/course380F" />">Return to Main Page</a>

<h2>Users</h2>

<a href="<c:url value="/user/create" />">Create a User</a><br /><br />

<c:choose>
    <c:when test="${fn:length(lectureNoteUsers) == 0}">
        <i>There are no users in the system.</i>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>Username</th>
                <th>Password</th>
                <th>Full Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Roles</th>
                <th>Actions</th>
            </tr>
            <c:forEach items="${lectureNoteUsers}" var="user">
                <tr>
                    <td>${user.username}</td>
                    <td>${fn:substringAfter(user.password, '{noop}')}</td>
                    <td>${user.fullName}</td>
                    <td>${user.email}</td>
                    <td>${user.phone}</td>
                    <td>
                        <c:forEach items="${user.roles}" var="role" varStatus="status">
                            <c:if test="${!status.first}">, </c:if>
                            ${role.role}
                        </c:forEach>
                    </td>
                    <td>
                        [<a href="<c:url value="/user/delete/${user.username}" />">Delete</a>]
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
<br /><br />
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</body>
</html>
