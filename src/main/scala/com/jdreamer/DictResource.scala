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
  def create(e: Entry)(implicit s: DBSession = AutoSession): Long = {
    sql"MERGE INTO dict(entry, reading, meaning) KEY (entry) VALUES (${e.mainWord}, ${e.reading}, ${e.meaning})"
      .executeUpdate().apply()
  }

  def findByMainWord(mainWord: String)(implicit s: DBSession = AutoSession): Option[Entry] = {
    sql"select entry, reading, meaning from dict where entry = ${mainWord}"
      .map { rs => Entry(rs) }.single.apply()
  }

  def createTables()(implicit s: DBSession = AutoSession): Unit = {
    sql"""
      create table if not exists dict(
        entry varchar(256) primary key,
        reading varchar(256),
        meaning varchar(2048)
      )
      """.execute().apply()
  }
}
