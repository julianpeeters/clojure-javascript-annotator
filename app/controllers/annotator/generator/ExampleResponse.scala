package controllers
package annotator
package generator

import play.api.libs.json.{Json, JsArray, JsObject, JsValue}

object ExampleResponse {

  def generate(jsonSchema: JsValue): String = {
    "Example"
  }

}
