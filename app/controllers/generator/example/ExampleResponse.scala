package controllers
package generator
package example

import play.api.libs.json.{Json, JsArray, JsObject, JsValue}

object ExampleResponse {

  def generate(jsonSchema: JsValue): String = {
    "Example"
  }

}
