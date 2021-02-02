<header>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <nav class="navbar sticky navbar-expand-lg navbar-dark bg-dark scrolling-navbar">
    <div class="collapse navbar-collapse" >
      <ul class="navbar-nav mr-auto">
      <li class="nav-item">
          <a class="nav-link" href="/createNotes">Create Notes</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/userNotes">Your Notes</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/userInfo">Profile</a>
        </li>
        <li class="nav-item">
          <a class="nav-link text-danger" href="?logout">Log-Out</a>
        </li>
      </ul>
    </div>
  </nav>
</header>