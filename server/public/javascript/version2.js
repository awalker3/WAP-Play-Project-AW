/**
 * 
 */
 const loginRoute = $("#loginRoute").val();
 const validateRoute = $("#validateRoute").val();
 const csrfToken = $("#csrfToken").val();
$("#contents").load(loginRoute);

function login() {
    const username = $("#v2usernameLogin").val();
    const password = $("#v2passwordLogin").val();
    $.post(validateRoute,
        {username, password, csrfToken},
        data => {
            $("#contents").html(data);
        });
}

function createUser() {
    const username = $("#v2usernameCreate").val();
    const password = $("#v2passwordCreate").val();
    $("#contents").load("/create2?username=" + username + "&password=" + password)
}

function deleteTask(index) {
    $("#contents").load("/deleteTask2?index=" + index)
}

function addTask() {
    const task = $("#newTask").val();
    $("#contents").load("/addTask2?task=" + encodeURIComponent(task))
}