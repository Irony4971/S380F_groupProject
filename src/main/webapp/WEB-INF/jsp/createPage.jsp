<!DOCTYPE html>
<html>
<head>
  <title>Create Lecture Page</title>
</head>
<body>

<h2>Create a New Lecture Page</h2>
<form method="POST" action="<c:url value='/course380F/createPage' />">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <label for="title">Title:</label><br/>
  <input type="text" id="title" name="title" required/><br/><br/>

  <label for="description">Description:</label><br/>
  <textarea id="description" name="description" rows="4" cols="50"></textarea><br/><br/>

  <input type="submit" value="Create"/>
</form>
<br/>
<a href="<c:url value='/course380F' />">Return to Main Page</a>
</body>
</html>
