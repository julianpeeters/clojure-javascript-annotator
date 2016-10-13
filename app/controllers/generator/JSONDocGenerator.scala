package controllers
package generator

import controllers.generator.example.ExampleResponse
import controllers.generator.returned.ReturnedData
import controllers.generator.types.Types
import models.DocResult
import play.api.libs.json.{Json, JsArray, JsObject, JsValue}

object JSONDocGenerator {

  def generateDocsFromSchema(jsonSchema: JsValue): DocResult = {
    val returnedData = ReturnedData.generate(jsonSchema)
    val exampleResponse = ExampleResponse.generate(jsonSchema)
    val typeAnnotatedJson = Types.generate(jsonSchema)
    DocResult(returnedData, exampleResponse, typeAnnotatedJson)
  }

}
