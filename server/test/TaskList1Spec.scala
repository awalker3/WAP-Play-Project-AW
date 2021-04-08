import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import org.scalatestplus.play.OneBrowserPerSuite
import org.scalatestplus.play.HtmlUnitFactory
import controllers.TaskList1


class TaskList1Spec extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
    "Task list 1" must {
    "show login page" in {
      go to s"http://localhost:$port/login1"
      pageTitle mustBe "Login"
      find(cssSelector("h1")).isEmpty mustBe false
      find(cssSelector("h1")).foreach(e => e.text mustBe "Login")
      click on "username-login"
      textField("username-login").value = "mark"
      click on "password-login"
      textField(id("password-login")).value = "pass"
      submit()
    }
    "go to task list page" in {
      pageTitle mustBe "Task List"
      find(cssSelector("h1")).isEmpty mustBe false
      find(cssSelector("h1")).foreach(e => e.text mustBe "Task List")
      findAll(cssSelector("li")).toList.map(_.text) mustBe List("eat", "sleep", "code")
    }
    }
}