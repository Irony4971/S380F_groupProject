
<!DOCTYPE html>
<head>
    <title>Main Page</title>
</head>
<body>
<h2>COMP S380F</h2>

<security:authorize access="hasRole('TEACHER')">
    <a href="<c:url value="/user" />">Manage User Accounts</a><br /><br />
    <a href="<c:url value="/course380F/createPoll" />">Create a Poll Page</a><br/><br/>
    <a href="<c:url value="/course380F/createPage" />">Create a Lecture Page</a><br/><br/>
</security:authorize>

<c:choose>
    <c:when test="${empty lecturePages}">
        <i>There are no lecture pages.</i>
    </c:when>
    <c:otherwise>
        <ul>
            <c:forEach items="${lecturePages}" var="page">
                <li>
                    <security:authorize access="!hasRole('GUEST')">
                        <a href="<c:url value='/course380F/lecture/${page.id}'/>">
                            <c:out value="${page.title}" />
                        </a>
                    </security:authorize>

                    <security:authorize access="hasRole('GUEST')">
            <span class="text-muted">
                <c:out value="${page.title}" /> (Login to access)
            </span>
                    </security:authorize>

                    <security:authorize access="hasRole('TEACHER')">
                        [<a href="<c:url value='/course380F/deletePage/${page.id}'/>">Delete</a>]
                    </security:authorize>
                    <br/>
                    <c:out value="${page.description}" />
                </li>
            </c:forEach>
        </ul>
    </c:otherwise>
</c:choose>

<security:authorize access="!hasRole('GUEST')">
    <a href="<c:url value='/course380F/user/comments'/>">My Comment History</a>
    <c:url var="logoutUrl" value="/logout"/>
    <form action="${logoutUrl}" method="post">
        <input type="submit" value="Log out" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</security:authorize>
<security:authorize access="hasRole('STUDENT')">
    <c:if test="${not empty pageContext.request.userPrincipal}">
        <a href="<c:url value='/updateProfile'/>">My Profile</a>
    </c:if>
</security:authorize>
<security:authorize access="hasRole('GUEST')">
    <a href="/project/login">Login for Full Access</a>
</security:authorize>
</body>
</html>

