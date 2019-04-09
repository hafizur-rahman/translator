package com.jdreamer

import scalikejdbc._
import scalikejdbc.{AutoSession, DBSession}

case class Entry(mainWord: String, reading: String, meaning: String)

object Entry extends SQLSyntaxSupport[Entry] {
  override val tableName = "dict"

  def apply(rs: WrappedResultSet): Entry = new Entry(
    rs.string("entry"),
    rs.string("reading"),
    rs.string("meaning"))
}

object DictResource {
  def bulkLoad(csvFilePath: String)(implicit s: DBSession = AutoSession): Unit = {
    val sql =
      s"""
        | CREATE TABLE IF NOT EXISTS dict(
        | entry varchar(256),
        | reading varchar(256),
        | meaning varchar(2048)
        |)
        | AS SELECT * FROM CSVREAD ('${csvFilePath}')
      """.stripMargin

    SQL(sql).update().apply()
  }

  def findByMainWord(mainWord: String)(implicit s: DBSession = AutoSession): List[Entry] = {
    sql"select entry, reading, meaning from dict where entry = ${mainWord}"
      .map { rs => Entry(rs) }.list().apply()
  }

  def createTables()(implicit s: DBSession = AutoSession): Unit = {
    sql"""
      create table if not exists dict(
        entry varchar(256),
        reading varchar(256),
        meaning varchar(2048)
      )
      """.execute().apply()
  }
}
