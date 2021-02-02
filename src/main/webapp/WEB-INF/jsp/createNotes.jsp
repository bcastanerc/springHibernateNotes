<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
      rel="icon"
      href="https://w7.pngwing.com/pngs/263/431/png-transparent-javaserver-pages-jar-java-servlet-computer-software-jar-text-logo-computer-programming-thumbnail.png"
    />
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
      integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
      crossorigin="anonymous"
    />
    
    <title>Create notes</title>
  </head>
  <body>
  <jsp:directive.include file="./nav.jsp" />
    <h1 class="display-3 d-flex justify-content-center">Create Notes</h1>
    <main class="container">
        <form method="POST" action="/createNotes">
            <div class="form-group">
                <label>Title of the note</label>
                <input type="text" class="form-control" name="title" aria-describedby="basic-addon1">
            </div>

            <div class="form-group">
                <label>Text of the note</label>
                <textarea class="form-control rounded-0" name="text" rows="20"></textarea>
                <style>textarea {resize: none; overflow: auto;}</style>
            </div>

            <button type="submit" class="btn btn-primary">Create note</button>
            <input type="hidden" name="_csrftoken" value="${csrfToken}">
        </form>
        <c:if test="${error eq true}">
          <div style="margin-top: 20px;" class="alert alert-warning fade show">
            <strong>Warning!</strong> You can't Create a note with a title of more than 150 charactes.
            <button type="button" class="close" data-dismiss="alert"></button>
          </div>
        </c:if>
    </main>
    <!-- Boostrap script-->
    <script
      src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
      integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
