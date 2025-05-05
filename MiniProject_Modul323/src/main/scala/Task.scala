package model

import upickle.default.{ReadWriter, macroRW}

//immutable daten gehalten
case class Task(
                 id: String,
                 title: String,
                 category: String,
                 deadline: String,
                 done: Boolean = false
               )

object Task {
  implicit val rw: ReadWriter[Task] = macroRW
}
