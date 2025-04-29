package storage

import model.Task
import upickle.default._
import java.nio.file.{Files, Paths}
import java.nio.charset.StandardCharsets

object Storage {
  def save(tasks: List[Task], file: String): Unit = {
    val json = write(tasks)
    Files.write(Paths.get(file), json.getBytes(StandardCharsets.UTF_8))
  }

  def load(file: String): List[Task] = {
    val path = Paths.get(file)
    if (Files.exists(path)) {
      val json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8)
      read[List[Task]](json)
    } else {
      List.empty
    }
  }
}
