package controllers
package generator
package util

import models.{JsonString, LangString}

object Markdown {

  /** Example:
    * ```js
    * {...}
    * ```
    */
  def asJsCodeBlock(jsonString: JsonString): String = {
    val syntax = "js"
    val json = jsonString.value
    s"```$syntax\n$json\n```\n"
  }

}
