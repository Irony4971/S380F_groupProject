<!DOCTYPE html>
<html>
<head>
    <title>My Comment History</title>
    <style>
        .comment-container {
            margin-bottom: 20px;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .comment-header {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            font-size: 0.9em;
            color: #666;
        }
        .comment-content {
            margin-bottom: 10px;
        }
        .comment-page {
            font-style: italic;
        }
        .no-comments {
            font-size: 1.2em;
            color: #666;
            text-align: center;
            margin-top: 50px;
        }
    </style>
</head>
<body>
<h1>My Comment History</h1>

<c:choose>
    <c:when test="${not empty comments}">
        <c:forEach var="comment" items="${comments}">
            <div class="comment-container">
                <div class="comment-header">
                    <span>Posted on: <fmt:formatDate value="${comment.date}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
                </div>
                <div class="comment-content">
                        ${comment.content}
                </div>
                <div class="comment-page">
                    On page: ${comment.lecturePage.title}
                    <a href="<c:url value='/course380F/lecture/${comment.lecturePage.id}'/>">View Page</a>
                </div>
                <div>
                    <security:authorize access="hasRole('TEACHER')">
                    <a href="<c:url value='/course380F/lecture/${comment.lecturePage.id}/editComment/${comment.id}'/>">Edit</a> |
                        <form action="<c:url value='/course380F/lecture/${comment.lecturePage.id}/deleteComment/${comment.id}'/>"
                              method="post"
                              style="display: inline;">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" onclick="return confirm('Are you sure you want to delete this comment?')">
                                Delete
                            </button>
                        </form>
                    </security:authorize>
                </div>
            </div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <div class="no-comments">
            You haven't posted any comments yet.
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>