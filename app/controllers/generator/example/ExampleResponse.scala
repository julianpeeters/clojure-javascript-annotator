package controllers
package generator
package example

import controllers.generator.util.{JSONRebuilder, Markdown, ValueGenerator}
import models.{JsDocTag, JsonString}
import play.api.libs.json.{Json, JsArray, JsObject, JsValue}

object ExampleResponse {

  def generate(jsonSchema: JsValue): String = {
    val exampleResponse: JsonString = jsonSchema match {
      case jsObject: JsObject =>
        val schemaFields = jsObject.fields
        val exampleFields = schemaFields.map { case (fieldName, fieldType) => {
          val exampleValue = ValueGenerator.getExampleValue(fieldName, fieldType)
          (fieldName, exampleValue)
        }}.toMap
        val jsonExample = jsObject.copy(underlying = exampleFields)
        val prettyJson = Json.prettyPrint(jsonExample)
        JsonString(prettyJson)
      case _ =>
        val prettyJson = Json.prettyPrint(jsonSchema)
        JsonString(prettyJson)
    }
    val jsonMarkdown = Markdown.asJsCodeBlock(exampleResponse)
    jsonMarkdown
  }

}
