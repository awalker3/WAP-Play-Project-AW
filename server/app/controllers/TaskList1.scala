package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class TaskList1 @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def taskList = Action {
      val tasks = List("task1", "task2", "task3", "task4")
      Ok(views.html.taskList1(Nil))
  }
}
