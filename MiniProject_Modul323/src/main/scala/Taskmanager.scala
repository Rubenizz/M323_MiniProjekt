package logic

import model.Task
import java.util.UUID

object TaskManager {
  def addTask(tasks: List[Task], title: String, category: String, deadline: String): List[Task] = {
    tasks :+ Task(UUID.randomUUID().toString, title, category, deadline)
  }

  def deleteTask(tasks: List[Task], id: String): List[Task] = {
    tasks.filterNot(_.id == id)
  }

  def updateTask(tasks: List[Task], id: String, updated: Task => Task): List[Task] = {
    tasks.map(task => if (task.id == id) updated(task) else task)
  }

  def filterTasks(tasks: List[Task], category: Option[String], deadline: Option[String]): List[Task] = {
    tasks.filter { task =>
      category.forall(_ == task.category) &&
        deadline.forall(_ == task.deadline)
    }
  }

  def markDone(tasks: List[Task], id: String): List[Task] = {
    updateTask(tasks, id, task => task.copy(done = true))
  }
}
