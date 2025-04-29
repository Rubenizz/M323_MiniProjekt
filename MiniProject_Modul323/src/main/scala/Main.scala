import scala.io.StdIn._
import model.Task
import logic.TaskManager._
import storage.Storage._
//test
object Main extends App {
  val file = "tasks.json"
  var tasks: List[Task] = load(file)

  var running = true

  while (running) {
    println(
      """
        |== ToDo App ==
        |1. Aufgabe anzeigen
        |2. Aufgabe hinzufügen
        |3. Aufgabe löschen
        |4. Aufgabe als erledigt markieren
        |5. Beenden
        |""".stripMargin)

    val input = readLine("Wähle eine Option: ")

    input match {
      case "1" =>
        println("== Aufgabenliste ==")
        tasks.zipWithIndex.foreach { case (task, index) =>
          println(s"[${index + 1}] ${task.title} (${task.category}) - Bis: ${task.deadline} - Erledigt: ${task.done}")
        }

      case "2" =>
        val title = readLine("Titel der Aufgabe: ")
        val category = readLine("Kategorie: ")
        val deadline = readLine("Deadline (z.B. 2025-04-10): ")
        tasks = addTask(tasks, title, category, deadline)
        save(tasks, file)
        println("✅ Aufgabe wurde hinzugefügt.")

      case "3" =>
        val id = readLine("Gib die ID der Aufgabe ein, die du löschen möchtest: ")
        tasks.find(_.id == id) match {
          case Some(_) =>
            tasks = deleteTask(tasks, id)
            save(tasks, file)
            println("🗑️ Aufgabe gelöscht.")
          case None => println("❌ Keine Aufgabe mit dieser ID gefunden.")
        }

      case "4" =>
        val id = readLine("Gib die ID der Aufgabe ein, die du als erledigt markieren willst: ")
        tasks.find(_.id == id) match {
          case Some(_) =>
            tasks = markDone(tasks, id)
            save(tasks, file)
            println("✅ Aufgabe als erledigt markiert.")
          case None => println("❌ Keine Aufgabe mit dieser ID gefunden.")
        }

      case "5" =>
        running = false
        println("Programm beendet.")

      case _ =>
        println("Ungültige Eingabe.")
    }

    println()
  }
}
