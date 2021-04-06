package controllers

import javax.inject._
import play.api.mvc._
import models.TaskListInMemoryModel
import play.api.data._
import play.api.data.Forms._

case class LoginData(username: String, password: String)

@Singleton
class TaskList1 @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  val loginForm = Form(mapping(
    "Username" -> text(3, 10),
    "Password" -> text(8)
    )(LoginData.apply)(LoginData.unapply))
  
    def login = Action { implicit request =>
        Ok(views.html.login1(loginForm))
    }

    def validateLoginGet (username: String, password: String) = Action {
      Ok(s"You have logged in with $username and $password")
    }

    def validateLoginPost = Action { implicit request => 
      val postVals = request.body.asFormUrlEncoded
      postVals.map {args => 
        val username = args("username").head
        val password = args("password").head
        if(TaskListInMemoryModel.validateUser(username, password))
          Redirect(routes.TaskList1.taskList()).withSession("username" -> username)
        else Redirect(routes.TaskList1.login()).flashing("error" -> "Incorrect username or password")
      }.getOrElse(Redirect(routes.TaskList1.login()))
    }

     def createUserForm = Action { implicit request =>
    loginForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.login1(formWithErrors)),
      ld =>
        if (TaskListInMemoryModel.createUser(ld.username, ld.password)) {
          Redirect(routes.TaskList1.taskList()).withSession("username" -> ld.username)
        } else {
          Redirect(routes.TaskList1.login()).flashing("error" -> "User creation failed.")
        })
  }
  
    def taskList = Action { implicit request =>
      val usernameOption = request.session.get("username")
      usernameOption.map { username =>
        val tasks = TaskListInMemoryModel.getTasks(username)
        Ok(views.html.taskList1(tasks))
      }.getOrElse(Redirect(routes.TaskList1.login()))
      
    }

    def createUser = Action { request => 
      val postVals = request.body.asFormUrlEncoded
      postVals.map {args => 
        val username = args("username").head
        val password = args("password").head
        if(TaskListInMemoryModel.createUser(username, password))
          Redirect(routes.TaskList1.taskList()).withSession("username" -> username)
        else Redirect(routes.TaskList1.login()).flashing("error" -> "User creation failed")
      }.getOrElse(Redirect(routes.TaskList1.login()))
    }

    def logout = Action {
      Redirect(routes.TaskList1.login()).withNewSession
    }

    def addTask = Action {implicit request =>
      val usernameOption = request.session.get("username")
      usernameOption.map { username =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map {args => 
          val task = args("newTask").head
          TaskListInMemoryModel.addTask(username, task)
          Redirect(routes.TaskList1.taskList())
        }.getOrElse(Redirect(routes.TaskList1.taskList()))
      }.getOrElse(Redirect(routes.TaskList1.login()))
    }

    def deleteTask = Action {implicit request =>
      val usernameOption = request.session.get("username")
      usernameOption.map { username =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map {args => 
          val task = args("index").head.toInt
          TaskListInMemoryModel.removeTask(username, task)
          Redirect(routes.TaskList1.taskList())
        }.getOrElse(Redirect(routes.TaskList1.taskList()))
      }.getOrElse(Redirect(routes.TaskList1.login()))
    }

}
