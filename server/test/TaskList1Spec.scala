import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import org.scalatestplus.play.OneBrowserPerSuite
import org.scalatestplus.play.HtmlUnitFactory
import controllers.TaskList1


class TaskList1Spec extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
    "Task list 1" must {
    "login and see task list" in {
      go to s"localhost:$port/login1"
      click on "username-login"
      textField("username-login").value = "mark"
      click on "password-login"
      pwdField(id("password-login".stripMargin)).value = "pass"
      submit()
    }
    }
}