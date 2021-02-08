<!DOCTYPE html>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>Login Page</title>
  </head>
  <body>
    <h1 class="display-3 d-flex justify-content-center">Login</h1>
    <main class="container w-50">
      <form method="POST" action="/login">
        <div class="form-group">
          <label>Email address</label>
          <input
            type="email"
            class="form-control"
            aria-describedby="emailHelp"
            placeholder="Enter email"
            name="email"
          />
          <small class="form-text text-muted"
            >We'll never share your email with anyone else.</small
          >
        </div>
        <div class="form-group">
          <label>Password</label>
          <input
            type="password"
            class="form-control"
            placeholder="password"
            name="password"
          />
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
        <a class="btn btn-info" href="/logingoogle"><img class="mr-2" src="https://images.theconversation.com/files/93616/original/image-20150902-6700-t2axrz.jpg?ixlib=rb-1.1.0&q=45&auto=format&w=1000&fit=clip" width="20" height="20">Loging With Google</a>
        <a href="/register" class="my-0 ml-sm-2">If you don't have an account register first...</a><br>
        <input type="hidden" name="_csrftoken" value="${csrfToken}">
      </form>
       <c:if test="${error eq true}">
        <div style="margin-top: 20px;" class="alert alert-warning fade show">
          <strong>Warning!</strong> Please enter a valid email and password to log in. If you are registered via <a href="/logingoogle">Google Auth</a> you must use that method to log. If you don't have an account <strong><a class="alert-link" href="/register">register</a></strong> first.
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
