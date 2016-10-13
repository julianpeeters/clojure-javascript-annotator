package controllers
package generator
package returned

import controllers.generator.util.{JSONRebuilder, Markdown, TypeEraser}
import models.{JsDocTag, JsonString}
import play.api.libs.json.{Json, JsArray, JsObject, JsValue}

object ReturnedData {

  def generate(jsonSchema: JsValue): String = {
    val returnedData: JsonString = jsonSchema match {
      case json: JsObject =>
        val annotatedFields = json.fields.map { case (fieldName, fieldType) => {
          val erasedType = TypeEraser.eraseTypeParam(fieldType)
          val jsDocTag = DocTag.asJsDocTag(fieldName, fieldType).value
          s"""  $jsDocTag
            |  "$fieldName": $erasedType""".stripMargin
        }}
        val jsonString = JSONRebuilder.asJsonString(annotatedFields)
        jsonString
      case _ =>
        val prettyJson = Json.prettyPrint(jsonSchema)
        JsonString(prettyJson)
    }
    val jsonMarkdown = Markdown.asJsCodeBlock(returnedData)
    jsonMarkdown
  }

}
