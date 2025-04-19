<!DOCTYPE html>
<html>
<head>
    <title>Edit Lecture Note</title>
    <style>
        .file-upload { margin: 15px 0; }
        .existing-files { margin: 10px 0; }
        .existing-files li { color: #666; }
    </style>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" class="logout-form">
    <input type="submit" value="Log out" class="btn-logout"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<div class="container">
    <h2>Edit Lecture Note</h2>
    <form:form method="POST" enctype="multipart/form-data" modelAttribute="lectureNoteForm" class="note-form">
        <div class="form-group">
            <form:label path="subject">Subject</form:label>
            <form:input path="subject" class="form-control" required="true"/>
        </div>

        <div class="form-group">
            <form:label path="body">Content</form:label>
            <form:textarea path="body" rows="5" class="form-control" required="true"/>
        </div>

        <div class="form-group">
            <label>Existing Materials</label>
            <div class="existing-files">
                <c:forEach items="${lectureNote.courseMaterials}" var="material">
                    <li>
                            ${material.name}
                        <a href="<c:url value="/course380F/lecture/${lecturePage.id}/material/${material.id}"/>">Download</a>
                    </li>
                </c:forEach>
            </div>
        </div>

        <div class="form-group">
            <label>Add More Materials</label>
            <div class="file-upload">
                <input type="file" name="courseMaterials" multiple
                       class="file-input"
                       accept=".pdf,.doc,.docx,.jpg,.png,.jpeg"
                       onchange="showFiles(this)"/>
                <div class="file-list" id="fileListEdit"></div>
            </div>
        </div>

        <div class="form-actions">
            <input type="submit" value="Save Changes" class="btn-submit"/>
            <a href="<c:url value="/course380F/lecture/${lecturePage.id}"/>" class="btn-cancel">Cancel</a>
        </div>
    </form:form>
</div>

<script>
    function showFiles(input) {
        let fileList = input.files;
        let output = document.getElementById('fileList' + (input.form.classList.contains('note-form') ? 'Add' : 'Edit'));
        output.innerHTML = 'Selected ' + fileList.length + ' files:<ul>';

        for(let file of fileList) {
            output.innerHTML += '<li>' + file.name + ' (' + (file.size/1024).toFixed(2) + 'KB)</li>';
        }
        output.innerHTML += '</ul>';
    }
</script>
</body>
</html>