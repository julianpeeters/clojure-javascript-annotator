package models

case class AnnotatedJSON(jsons: String)
// case class ExampleResponse(example: String)
// case class ReturnedData(typeSummary: String)

case class DocResult(
  returnedData: String,
  exampleResponse: String,
  types: String)

case class JsDocTag(value: String)

case class JsonString(value: String) extends AnyVal

case class LangString(value: String) extends AnyVal
