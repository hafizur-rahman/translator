package com.jdreamer

import java.io.{File, FileWriter}

import scalikejdbc._
import com.jdreamer.DictResource._
import com.opencsv.CSVWriter
import org.apache.commons.io.FileUtils

import scala.collection.JavaConverters._

object DictLoader {
  val databasePath = "./database/dict"

  val r = "([^\\s]+)\\s?\\[([^\\]]+)\\]\\s?/\\s?(.+)".r

  def main(args: Array[String]): Unit = {
    Class.forName("org.h2.Driver")
    ConnectionPool.singleton(s"jdbc:h2:$databasePath", "", "")

    implicit val session = AutoSession

    //createTables()

    val loadDictionary = false
    if (loadDictionary) {
      val csvFile = new File("dict.csv")
      val lines = FileUtils.readLines(
        new File("/home/horizon/IdeaProjects/translate/src/main/resources/edict"), "EUC-JP").asScala


      val writer = new CSVWriter(new FileWriter(csvFile))

      lines.foreach{ line =>
        processLine(line).map(data => writer.writeNext(data, true))
      }

      writer.close()

      bulkLoad(csvFile.getAbsolutePath)
    }

    val result = findByMainWords(Set("誼み"))
    result.foreach(println)
  }

  private def processLine(line: String): Option[Array[String]] = {
    line match {
      case r(mainWord, reading, meaning) =>
        Some(Array(mainWord,reading, meaning))

      case _ =>
        println(line)
        None
    }
  }


}
