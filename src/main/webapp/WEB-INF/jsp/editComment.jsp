<!DOCTYPE html>
<html>
<head>
  <title>Edit Comment</title>
</head>
<body>
<h1>Edit Comment</h1>
<form action="<c:url value='/course380F/lecture/${pageId}/editComment/${comment.id}'/>" method="post">
  <div>Username: ${comment.username}</div>
  <textarea name="content" rows="5" cols="50" required>${comment.content}</textarea>
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <input type="submit" value="Save"/>
</form>
</body>
</html>