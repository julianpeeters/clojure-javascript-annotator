package controllers
package annotator

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
