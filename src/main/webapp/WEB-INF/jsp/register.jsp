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
    <link>
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
      integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
      crossorigin="anonymous"
    />
    <title>Registration Page</title>
  </head>
  <body>
    <h1 class="display-3 d-flex justify-content-center">Register</h1>
    <main class="container w-50">
      <form method="POST" action="/register">
        <!-- Email -->
        <div class="form-group">
          <label>Email address</label>
          <input
            type="email"
            class="form-control"
            aria-describedby="emailHelp"
            placeholder="Enter email"
            name="email"
            required
          />
          <small class="form-text text-muted"
            >We'll never share your email with anyone else.</small
          >
        </div>
        <!-- Username -->
        <div class="form-group">
          <label>Username</label>
          <input
            type="text"
            class="form-control"
            placeholder="Username"
            name="username"
            required minlength="3" maxlength="20"
          />
          <small class="form-text text-muted"
            >Username can contain any letters or numbers, without spaces, at
            least 3 character.</small
          >
        </div>

        <!-- Password -->
        <div class="form-group">
          <label>Password</label>
          <input
            type="password"
            class="form-control"
            placeholder="P@ssw0rd"
            name="password"
            required pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{6,}$"
          />
          <small class="form-text text-muted"
            >Password must be at least 8 character and contain at least 1 letter, 1 number, 1 special char and 1 cap letter, without spaces.</small
          >
        </div>

        <!-- Confirm Password -->
        <div class="form-group">
          <label>Confirm Password</label>
          <input
            type="password"
            class="form-control"
            placeholder="Confirm password"
            name="confirmPassword"
            required pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{6,}$"
          />
          <small class="form-text text-muted">Confirm Password</small>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
        <input type="hidden" name="_csrftoken" value="${csrfToken}">
      </form>
    
      <c:if test="${error eq true}">
        <div style="margin-top: 20px;" class="alert alert-warning fade show">
          <strong>Warning!</strong> Please enter a valid value in all the required fields before proceeding. You only can register an email once.
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
l
