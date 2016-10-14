package controllers
package generator
package util

import com.eaio.uuid.UUID
import play.api.libs.json.{
  JsArray,
  JsBoolean,
  JsNull,
  JsNumber,
  JsObject,
  Json,
  JsString,
  JsValue
}
import sys.process._

object ValueGenerator {

  // regexes for trying to derive an example value from a field's type and name.
  val IdPattern = "(^ids?\\z)".r
  val XIdPattern = "(\\w*)Ids?\\z".r
  val NamePattern = "(names?\\z)".r
  val XNamePattern = "(\\w*)Names?\\z".r
  val NicknamePattern = "(^nicknames?\\z)".r
  val RoutingNumberPattern = "(^routingNumbers?\\z)".r

  // when no suitable default value can be derived, provide a warning
  val Warning = "________________PLEASE_DEFINE_A_DEFAULT_VALUE________________"

  //TODO: search github for example values
  // val org = "*****"
  // val username = "*****"
  // val password = "*****"
  // val githubAuthToken = "*****"
  // val typeCandidateName = "*****"
  // val enumQuery = s"https://api.github.com/search/code?l=Scala&q=org%3A$org+object+$typeCandidateName+extends+Enumeration&utf8=%E2%9C%93"
  // val searchResult = Seq(
  //   "curl",
  //   "--user", s"$username:$password",
  //   "-H", s"Authorization: token $githubAuthToken",
  //   enumQuery)!!
  def getExampleValue(fieldName: String, fieldType: JsValue): JsValue = {
    fieldType match {
      case jsArray: JsArray =>
        val jsTypeParam = jsArray.value.headOption.getOrElse(sys.error("0"))
        val exampleValue = Seq(getExampleValue(fieldName, jsTypeParam))
        jsArray.copy(value = exampleValue)
      case jsBoolean: JsBoolean => jsBoolean
      case jsObject: JsObject =>
        val schemaFields = jsObject.fields
        val exampleFields = schemaFields.map { case (fieldName, fieldType) => {
          val exampleValue = getExampleValue(fieldName, fieldType)
          (fieldName, exampleValue)
        }}.toMap
        jsObject.copy(underlying = exampleFields)
      case jsString: JsString =>
        jsString.value match {
          case "boolean" => JsBoolean(false)
          case "string"|"optional string" =>
            fieldName match {
              case IdPattern(id) => JsString(new UUID toString)
              case XIdPattern(xId) => JsString(new UUID toString)
              case NamePattern(name) => JsString("Example Name")
              case XNamePattern(xName) => JsString(s"Example ${xName.capitalize} Name")
              case NicknamePattern(nickname) => JsString("Example Nickname")
              case RoutingNumberPattern(routingNumber) => JsString("123456789")
              case _ => JsString(Warning)
            }
          case _ => JsString(Warning)
        }
      case jsValue: JsValue =>
        JsString(Warning)
    }
  }

}
