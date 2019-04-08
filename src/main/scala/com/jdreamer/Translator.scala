package com.jdreamer

import java.io.File

import com.atilika.kuromoji.TokenizerBase
import com.atilika.kuromoji.ipadic.Tokenizer
import com.jdreamer.DictLoader.databasePath
import com.jdreamer.DictResource._
import org.apache.commons.io.FileUtils
import org.jsoup.Jsoup
import scalikejdbc._

import scala.collection.JavaConverters._

object Translator {
  //val url = "https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Array"
  //val url = "http://www.ne.jp/asahi/hishidama/home/tech/scala/source.html"
  val url = "http://www.ne.jp/asahi/hishidama/home/tech/scala/any.html"

  def main(args: Array[String]): Unit = {
    val encoding = "UTF-8"

    val file = new java.io.File(s"${url.hashCode}.tmp")
    if (!file.exists()) {
      val doc = Jsoup.connect(url).get

      val text = doc.body().text()

      FileUtils.writeStringToFile(file, text, encoding)
    }


    val lines = FileUtils.readLines(file, encoding).asScala.toList

    Class.forName("org.h2.Driver")
    ConnectionPool.singleton(s"jdbc:h2:$databasePath", "", "")

    implicit val session = AutoSession

    val words = searchWords(lines)//.toSet
    val translations = words.map(e => s"${e.mainWord}, ${e.reading}, ${e.meaning}")

    FileUtils.writeLines(new File(s"${url.hashCode}.tr"), encoding, translations.asJavaCollection)
  }

  def searchWords(lines: List[String])(implicit session: AutoSession): List[Entry] = {
    val builder = new Tokenizer.Builder
    builder.mode(TokenizerBase.Mode.SEARCH)

    val tokenizer: Tokenizer = builder.build

    lines.flatMap { line =>
      val tokens = tokenizer.tokenize(line).asScala

      tokens.flatMap { token =>
        val baseForm = token.getBaseForm
        if (!(baseForm == "*") && token.getPartOfSpeechLevel1 == "名詞") {
          findByMainWord(baseForm).map(e => e.copy(reading = s"${e.reading} (${token.getReading})"))
        } else {
          None
        }
      }
    }
  }
}
