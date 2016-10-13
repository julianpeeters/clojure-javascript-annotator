package controllers
package generator

import models.{JsDocTag, JsonString, LangString}
import play.api.libs.json.{JsArray, JsObject, JsValue}

object DocTag {

  def asJsDocTag(fieldName: String, fieldType: JsValue): JsDocTag = {
    def asJsonType(fieldName: String, fieldType: JsValue): String = {
      fieldType match {
        case jsArray: JsArray =>
          val jsTypeParam = jsArray.value.headOption.getOrElse(sys.error("0"))
          val typeParamString = asJsonType(fieldName, jsTypeParam)
          s"""Array<$typeParamString>"""
        case jsObject: JsObject =>
          s"""${fieldName.capitalize}"""
        case jsValue: JsValue =>
          s"""${jsValue.toString.replaceAll("\"", "")}"""
      }
    }
    val jsType = "!" + asJsonType(fieldName, fieldType)
    JsDocTag(s"/** @type {$jsType} */")
  }

}
