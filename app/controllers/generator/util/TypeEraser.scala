package controllers
package generator
package util

import play.api.libs.json.{JsArray, JsObject, JsValue}

object TypeEraser {

  def eraseTypeParam(fieldType: JsValue): JsValue = {
    fieldType match {
      case jsArray: JsArray => jsArray.copy(value = List.empty)
      case jsObject: JsObject => jsObject.copy(underlying = Map.empty)
      case jsValue: JsValue => jsValue
    }
  }

}
