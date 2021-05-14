/**
 * 
 */

"use strict"

 const loginRoute = document.getElementById("loginRoute").value;
 const validateRoute = document.getElementById("validateRoute").value;
 const csrfToken = document.getElementById("csrfToken").value;

function login() {
    const username = document.getElementById("v2usernameLogin").val();
    const password = document.getElementById("v2passwordLogin").val();
    fetch(validateRoute, {
        method: 'POST',
        headers: {'Content-Type': 'application/json', 'CSRF-token': csrfToken },
        body: JSON.stringify({username, password})
    }).then(res => res.text()).then(data => {
        console.log(data);
    })
}

// function createUser() {
//     const username = $("#v2usernameCreate").val();
//     const password = $("#v2passwordCreate").val();
//     $("#contents").load("/create2?username=" + username + "&password=" + password)
// }

// function deleteTask(index) {
//     $("#contents").load("/deleteTask2?index=" + index)
// }

// function addTask() {
//     const task = $("#newTask").val();
//     $("#contents").load("/addTask2?task=" + encodeURIComponent(task))
// }