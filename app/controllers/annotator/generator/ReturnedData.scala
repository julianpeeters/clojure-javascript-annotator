package controllers
package annotator
package generator

import models.{JsDocTag, JsonString, LangString}

import play.api.libs.json.{Json, JsArray, JsObject, JsValue}

object ReturnedData {

  def generate(jsonSchema: JsValue): String = {
    "Returned"
  }

}
