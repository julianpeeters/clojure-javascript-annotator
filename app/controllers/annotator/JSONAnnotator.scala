package controllers
package annotated

import play.api.libs.json.JsValue

object JSONAnnotator {
  def generateAnnotatedExample(jsonSchema: JsValue): List[JsValue] = {
    val annotatedResult = jsonSchema
    List(annotatedResult)
  }
}
