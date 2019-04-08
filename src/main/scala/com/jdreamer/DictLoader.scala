package com.jdreamer

import scalikejdbc._

import com.jdreamer.DictResource._

import scala.io.Source


object DictLoader {
  val databasePath = "./database/dict"

  val r = "([^\\s]+)\\s?\\[([^\\]]+)\\]\\s?/\\s?(.+)".r

  def main(args: Array[String]): Unit = {
    Class.forName("org.h2.Driver")
    ConnectionPool.singleton(s"jdbc:h2:$databasePath", "", "")

    implicit val session = AutoSession

    createTables()

    val loadDictionary = true
    if (loadDictionary) {
      val s = Source.fromFile("/home/horizon/IdeaProjects/translate/src/main/resources/edict", "EUC-JP")
      try {
        for (line <- s.getLines) {
          processLine(line)
        }
      } finally {
        s.close
      }
    }

    val result = findByMainWord("誼み")
    result.foreach(println)

  }

  private def processLine(line: String) = {
    line match {
      case r(mainWord, reading, meaning) =>
        create(Entry(mainWord, reading, meaning))

      case _ => // Do nothing
    }
  }


}
