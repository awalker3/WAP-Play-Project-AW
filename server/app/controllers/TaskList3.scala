
package controllers

import javax.inject._
import play.api.mvc._
import models._
import play.api.libs.json.Json
import com.typesafe.sslconfig.ssl.FakeChainedKeyStore
import play.api.libs.json.JsSuccess
import play.api.libs.json.JsError


@Singleton
class TaskList3 @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  
  def load = Action { implicit request =>
    Ok(views.html.v3main())
  }

  implicit val userDataRead = Json.reads[UserData]

  def validate = Action { implicit request => 
    request.body.asJson.map { body =>
        Json.fromJson[UserData](body) match {
            case JsSuccess(ud, path) => 
                if(TaskListInMemoryModel.validateUser(ud.username, ud.password))
                    Ok(Json.toJson(true)).withSession("username" -> ud.username)
                else Ok(Json.toJson(false))
            case e @ JsError(_) => Redirect(routes.TaskList3.load())
        }
    }.getOrElse(Redirect(routes.TaskList3.load()))
  }

  def data = Action {
      Ok(Json.toJson(Seq("a", "b", "c")))
  }


}
