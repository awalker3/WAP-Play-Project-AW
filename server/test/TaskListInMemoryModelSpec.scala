import org.scalatestplus.play._
import models.TaskListInMemoryModel
import models._
import org.eclipse.jetty.util.thread.Scheduler.Task

class TaskListInMemoryModelSpec extends PlaySpec {
    "TaskListInMemoryModel" must {
        "do valid login for default user" in {
            TaskListInMemoryModel.validateUser("mark", "pass") mustBe(true)
        }
        "reject login with wrong password" in {
            TaskListInMemoryModel.validateUser("mark", "password") mustBe(false)
        }
        "reject login with wrong username" in {
            TaskListInMemoryModel.validateUser("alex", "pass") mustBe(false)
        }
        "reject login with wrong username and password" in {
            TaskListInMemoryModel.validateUser("alex", "password") mustBe(false)
        }
        "get correct default tasks" in {
            TaskListInMemoryModel.getTasks("mark") mustBe(List("eat", "sleep", "code"))
        }
        "create a user with no tasks" in {
            TaskListInMemoryModel.createUser("alex", "password") mustBe(true)
            TaskListInMemoryModel.getTasks("alex") mustBe(Nil)
        }
        "create a new user with existing name" in {
            TaskListInMemoryModel.createUser("mark", "notpass") mustBe(false)
        }
        "add new task for default user" in {
            TaskListInMemoryModel.addTask("mark", "testing")
            TaskListInMemoryModel.getTasks("mark") must contain ("testing")
        }
        "add new task for new user" in {
            TaskListInMemoryModel.addTask("alex", "testing1")
            TaskListInMemoryModel.getTasks("alex") must contain ("testing1")
        }
        "remove task for default user" in {
            TaskListInMemoryModel.removeTask("mark", TaskListInMemoryModel.getTasks("mark").indexOf("eat"))
            TaskListInMemoryModel.getTasks("alex") must not contain ("eat")
        }
    }
}