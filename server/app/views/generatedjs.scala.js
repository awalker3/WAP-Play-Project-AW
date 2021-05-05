
@()

 $("#contents").load("@routes.TaskList2.login()");

 function login() {
     const username = $("#v2usernameLogin").val();
     const password = $("#v2passwordLogin").val();
     $("#contents").load("/validate2?username=" + username + "&password=" + password);
 }
 
 function createUser() {
     const username = $("#v2usernameCreate").val();
     const password = $("#v2passwordCreate").val();
     $("#contents").load("/create2?username=" + username + "&password=" + password);
 }
 
 function deleteTask(index) {
     $("#contents").load("/deleteTask2?index=" + index);
 }
 
 function addTask() {
     const task = $("#newTask").val();
     $("#contents").load("/addTask2?task=" + encodeURIComponent(task));
 }
