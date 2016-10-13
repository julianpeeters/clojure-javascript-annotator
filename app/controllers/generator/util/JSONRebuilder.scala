package controllers
package generator
package util

import models.JsonString

object JSONRebuilder {

  def asJsonString(annotatedFields: Seq[String]): JsonString = {
    val fieldsAsString = annotatedFields.mkString(",\n\n")
    def rebracketAsJson(fields: String): String = s"{\n$fields\n}"
    val annotatedJson = rebracketAsJson(fieldsAsString)
    JsonString(annotatedJson)
  }

}
