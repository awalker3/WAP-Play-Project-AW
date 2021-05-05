package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action { implicit request =>
    Ok(views.html.index(SharedMessages.itWorks))
  }

  def product(prodType: String, prodNum: Int) = Action {
    Ok(s"prodType is $prodType and prodNum is $prodNum")
  }

  def randomNumber = Action {
    Ok(util.Random.nextInt(100).toString())
  }

  def randomString (x: Int)= Action {
    Ok(util.Random.nextString(x))
  }

}
