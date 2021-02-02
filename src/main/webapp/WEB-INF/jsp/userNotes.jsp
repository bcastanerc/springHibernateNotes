<!DOCTYPE html>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="sanitize" class="com.liceu.notes.utils.sanitize"/>
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
    <title>Your notes</title>
  </head>
  <body>
  <style>
        .card{
          width: 40%;
          height: 250px;
          margin: 20px;
        }
        .cdinner{
          position: absolute;
          bottom: 10px;
        }
        .cdinner .card-text{
          margin: 5px 20px;
        }
        .intd{
          margin-left: 10px;
        }
        .bmodal{
          margin-left: 20px;
        }
  </style>
  <header class="mb-1">
    <nav class="navbar stiky navbar-expand-lg navbar-dark bg-dark scrolling-navbar">
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
            <a class="nav-link text-danger"  href="?logout">Log-Out</a>
          </li>
        </ul>
        <div class="d-flex flex-row">
          <form method="POST" action="/userNotes" class="d-flex flex-row">
            <div class="form-inline mr-auto">
              <input class="form-control" type="text" placeholder="Search" name="searchInput" aria-label="Search">
            </div>
            <div class="form-inline mr-auto my-0 ml-sm-2 ">
                <select name="inputType" class="form-control">
                  <option value="1" selected="selected">By title</option>
                  <option value="2">By text</option>
                  <option value="3">By expresion</option>
                  <option value="4">By creation date</option>
                  <option value="5">By modification date</option>
                </select>
            </div>
            <button class="btn btn-outline-blue btn-md my-0 ml-sm-2 btn-light" type="submit">Search</button>
            <input type="hidden" name="_csrftoken" value="${csrfToken}">
          </form>
          <button type="button" class="bmodal btn btn-danger" data-toggle="modal" data-target="#exampleModal">
            Delete Selected
        </button>
        </div>
      </div>
    </nav>
  </header>
    <h1 class="display-2 d-flex justify-content-center">Your Notes</h1>
    <main class="container">
      <form method="POST" action="/userNotes">
        <!-- Modal -->
        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Delete Selected Notes</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
                All the selected notes will be deleted permanently. Are you sure?
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                <button type="submit" class="btn btn-danger" data-toggle="modal">Yes</button>
              </div>
            </div>
          </div>
        </div>
        <div id="notesContainer" class="d-flex justify-content-between flex-wrap">
        </div>
        <input type="hidden" name="_csrftoken" value="${csrfToken}">
      </form>
      <div id="buttonsPags" class="d-flex justify-content-center">
      </div>
    </main>
    
    <script>
      let notes = [];
      <c:forEach var="n" items="${notes}">
      notes.push(`
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">${sanitize.sanitizeNote(n.title)}</h5>
                <p class="card-text">${sanitize.sanitizeNote(n.text)}</p>
                <div class="cdinner d-flex justify-content-around">
                  <a href="/viewNote?id=${n.id}" class="btn btn-primary btn-sm" value="${n.id}">View</a>
                  <p class="card-text"><small class="text-muted">Last edit ${n.last_modification}</small></p>
                  <p class="card-text">
                  <label class="form-check-label"><small class="text-muted">Delete </small></label>
                  <input type="checkbox" class="intd form-check-input" name="notesToDelete[]" value="${n.id}"></small>
                  </p>
                </div>
              </div>
            </div>
            `);
      </c:forEach>
      let pointer = 0;
      let notesPerPag = 10;
      let box = document.querySelector("#notesContainer");

      function displayNotes(){
        let displayNotes = [];
        box.innerHTML = "";
        let input = "";
        for(let i = 0; i < notesPerPag; i++){
            try {
                let insert = notes[pointer+i];
                if(insert){
                  input += insert;
                  box.innerHTML = input;
                }
            } catch (error) {}
        }
      }

      function nextPage(){
        if(notes.length > parseInt(pointer) + parseInt(notesPerPag)){
          pointer = pointer+notesPerPag;
        }
        displayNotes();
      }

      function previousPage(){
         if(parseInt(pointer) > 0){
          pointer = pointer-notesPerPag;
        }
        displayNotes();
      }

      window.addEventListener('load', (event) => {
          if(notes.length > notesPerPag){
            document.querySelector("#buttonsPags").innerHTML = 
            `<button id="previous" type="button" class="btn btn-primary m-3" onClick="previousPage()">Previous</button>
              <button id="next" type="button" class="btn btn-primary m-3" onClick="nextPage()">Next</button>`;
          }
          displayNotes();
      });
    </script>
    <!-- Boostrap script-->
    <script
      src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
      integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
      crossorigin="anonymous"
    ></script>
    <script
      src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
      integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
      crossorigin="anonymous"
    ></script>
    <script
      src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
      integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
