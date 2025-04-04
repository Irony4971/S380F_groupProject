<!DOCTYPE html>
<html>
<head>
  <title>add lecture note</title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
  <input type="submit" value="Log out" />
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<h2>Create a LectureNote</h2>
<form:form method="POST" enctype="multipart/form-data" modelAttribute="lectureNoteForm">
  <form:label path="subject">Subject</form:label><br/>
  <form:input type="text" path="subject"/><br/><br/>
  <form:label path="body">Body</form:label><br/>
  <form:textarea path="body" rows="5" cols="30"/><br/><br/>
  <b>CourseMaterials</b><br/>
  <input type="file" name="courseMaterials" multiple="multiple"/><br/><br/>
  <input type="submit" value="Submit"/>
</form:form>
</body>
</html>