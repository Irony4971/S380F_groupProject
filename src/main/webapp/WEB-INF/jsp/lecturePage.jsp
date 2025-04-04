
<!DOCTYPE html>
<html>
<head>
    <title><c:out value="${lecturePage.title}" /></title>
</head>
<body>
<div class="lecture-container">
    <h1><c:out value="${lecturePage.title}" /></h1>
    <p><c:out value="${lecturePage.description}" /></p>

    <security:authorize access="hasRole('TEACHER')">
        <a href="<c:url value="/course380F/lecture/${lecturePage.id}/create" />" class="create-btn">Create New Note</a>
    </security:authorize>

    <h2>Lecture Notes</h2>

    <c:choose>
        <c:when test="${empty lecturePage.lectureNotes}">
            <p>No notes available</p>
        </c:when>
        <c:otherwise>
            <c:forEach items="${lecturePage.lectureNotes}" var="note">
                <div class="note-container">
                        <c:if test="${not empty note.courseMaterials}">
                            <div class="materials-section">
                                <c:forEach items="${note.courseMaterials}" var="material">
                                    <div class="material-item">
                                        <a href="<c:url value="/course380F/lecture/${lecturePage.id}/material/${material.id}"/>">
                                            <c:out value="${material.name}"/>
                                        </a>
                                        <span>by <c:out value="${note.userName}"/></span>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>

                    <div class="note-actions">
                        <security:authorize access="hasRole('TEACHER') or principal.username=='${note.userName}'">
                            <a href="<c:url value="/course380F/lecture/${lecturePage.id}/edit/${note.id}"/>"
                               class="btn btn-edit">Edit</a>
                            <form:form action="/project/course380F/lecture/${lecturePage.id}/delete/${note.id}"
                                       method="post"
                                       style="display:inline;">
                                <input type="submit" value="Delete"
                                       class="btn btn-delete"/>
                                <input type="hidden" name="${_csrf.parameterName}"
                                       value="${_csrf.token}"/>
                            </form:form>
                        </security:authorize>
                    </div>
                </div>
            </c:forEach>

        </c:otherwise>
    </c:choose>

    <div style="margin-top: 30px;">
        <a href="<c:url value="/course380F" />">Return to Main Page</a>
    </div>

    <c:url var="logoutUrl" value="/logout"/>
    <form action="${logoutUrl}" method="post" style="margin-top:20px;">
        <input type="submit" value="Log out" class="action-btn delete-btn"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
</body>
</html>