import scala.io.StdIn._
import model.Task
import logic.TaskManager._
import storage.Storage._

object Main extends App {
  val file = "tasks.json"

  def mainMenu(): Unit = {
    val tasks = load(file)
    run(tasks)
  }

  def run(tasks: List[Task]): Unit = {
    println(
      """
        |== ToDo App ==
        |1. Aufgaben anzeigen
        |2. Aufgabe hinzufügen
        |3. Aufgabe löschen
        |4. Aufgabe als erledigt markieren
        |5. Aufgaben filtern
        |6. Beenden
        |7. Suchen
        |""".stripMargin)

    val input = readLine("Wähle eine Option: ")

    val updatedTasks = input match {
      case "1" =>
        showTasks(tasks)
        tasks

      case "2" =>
        val newTasks = addNewTask(tasks)
        save(newTasks, file)
        println("✅ Aufgabe wurde hinzugefügt.")
        newTasks

      case "3" =>
        val newTasks = deleteTaskById(tasks)
        save(newTasks, file)
        newTasks

      case "4" =>
        val newTasks = markTaskDone(tasks)
        save(newTasks, file)
        newTasks

      case "5" =>
        filterTasksAndShow(tasks)
        tasks

      case "6" =>
        println("Programm beendet.")
        return

      case "7" =>
        searchTasks(tasks)
        tasks

      case _ =>
        println("❌ Ungültige Eingabe.")
        tasks
    }

    println()
    run(updatedTasks) // rekursiver aufruf
  }

  def showTasks(tasks: List[Task]): Unit = {
    println("== Aufgabenliste ==")
    tasks.zipWithIndex.foreach { case (task, index) =>
      println(s"[${index + 1}] ${task.title} (${task.category}) - Bis: ${task.deadline} - Erledigt: ${task.done}")
    }
  }

  def addNewTask(tasks: List[Task]): List[Task] = {
    val title = readLine("Titel der Aufgabe: ")
    val category = readLine("Kategorie: ")
    val deadline = readLine("Deadline (z.B. 2025-04-10): ")
    addTask(tasks, title, category, deadline)
  }

  def deleteTaskById(tasks: List[Task]): List[Task] = {
    val id = readLine("Gib die ID der Aufgabe ein, die du löschen möchtest: ")
    tasks.find(_.id == id) match {
      case Some(_) =>
        println("🗑️ Aufgabe gelöscht.")
        deleteTask(tasks, id)
      case None =>
        println("❌ Keine Aufgabe mit dieser ID gefunden.")
        tasks
    }
  }

  def markTaskDone(tasks: List[Task]): List[Task] = {
    val id = readLine("Gib die ID der Aufgabe ein, die du als erledigt markieren willst: ")
    tasks.find(_.id == id) match {
      case Some(_) =>
        println("✅ Aufgabe als erledigt markiert.")
        markDone(tasks, id)
      case None =>
        println("❌ Keine Aufgabe mit dieser ID gefunden.")
        tasks
    }
  }

  def filterTasksAndShow(tasks: List[Task]): Unit = {
    val category = readLine("Nach Kategorie filtern (leer = kein Filter): ")
    val deadline = readLine("Nach Deadline filtern (leer = kein Filter): ")

    val filtered = tasks
      .filter(task => category.isEmpty || task.category.toLowerCase.contains(category.toLowerCase))
      .filter(task => deadline.isEmpty || task.deadline.contains(deadline))

    println("== Gefilterte Aufgaben ==")
    filtered.foreach { task =>
      println(s"${task.title} (${task.category}) - Bis: ${task.deadline} - Erledigt: ${task.done}")
    }
  }

  def searchTasks(tasks: List[Task]): Unit = {
    val keyword = readLine("Nach was suchst du? (Titel, Kategorie oder Deadline): ").toLowerCase
    val results = tasks.filter(task =>
      task.title.toLowerCase.contains(keyword) ||
        task.category.toLowerCase.contains(keyword) ||
        task.deadline.toLowerCase.contains(keyword)
    )

    if (results.nonEmpty) {
      println("== Gefundene Aufgaben ==")
      results.foreach { task =>
        println(s"${task.title} (${task.category}) - Bis: ${task.deadline} - Erledigt: ${task.done}")
      }
    } else {
      println("Keine passenden Aufgaben gefunden.")
    }
  }

  // Startprogramm
  mainMenu()
}
