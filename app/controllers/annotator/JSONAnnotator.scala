package controllers
package annotator

import controllers.annotator.generator.{ExampleResponse, ReturnedData, Types}
import models.DocResult
import play.api.libs.json.{Json, JsArray, JsObject, JsValue}

object JSONAnnotator {

  def generateDocsFromSchema(jsonSchema: JsValue): DocResult = {
    val returnedData = ReturnedData.generate(jsonSchema)
    val exampleResponse = ExampleResponse.generate(jsonSchema)
    val typeAnnotatedJson = Types.generate(jsonSchema)
    DocResult(returnedData, exampleResponse, typeAnnotatedJson)
  }

}
