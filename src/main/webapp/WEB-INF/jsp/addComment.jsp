<!DOCTYPE html>
<html>
<head>
    <title>Add Comment</title>
</head>
<body>
<h1>Add Comment</h1>
<form action="<c:url value='/course380F/lecture/${pageId}/addComment'/>" method="post">
    <textarea name="content" rows="5" cols="50" required></textarea>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>