
package controllers

import javax.inject._
import play.api.mvc._
import models.TaskListInMemoryModel


@Singleton
class TaskList2 @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  
  def load = Action { implicit request =>
    Ok(views.html.v2main())
  }

  def login = Action {
      Ok(views.html.login2())
  }
  def logout = Action {
      Redirect(routes.TaskList2.load()).withNewSession
    }

  def validate = Action { implicit  request =>
    val postVals = request.body.asFormUrlEncoded
      postVals.map {args => 
        val username = args("username").head
        val password = args("password").head
        if(TaskListInMemoryModel.validateUser(username, password))
          Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username))).withSession("username" -> username)
        else Redirect(routes.TaskList2.login())
      }.getOrElse(Ok(views.html.login2()))
  }

  def createUser (username: String, password: String) = Action {
    if(TaskListInMemoryModel.createUser(username, password))
          Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username))).withSession("username" -> username)
    else Redirect(routes.TaskList2.login())
  }

  def deleteTask(index: Int) = Action { implicit request =>
    val usernameOption = request.session.get("username")
      usernameOption.map { username =>
          TaskListInMemoryModel.removeTask(username, index)
          Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username)))
      }.getOrElse(Ok(views.html.login2()))
  }

  def addTask (task: String) = Action { implicit request => 
    val usernameOption = request.session.get("username")
      usernameOption.map { username =>
          TaskListInMemoryModel.addTask(username, task)
          Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username)))
      }.getOrElse(Ok(views.html.login2()))
}

def generatedJS = Action {
    Ok(views.js.generatedjs())
}

 

}
