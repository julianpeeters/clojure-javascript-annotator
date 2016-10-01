package controllers
package annotated

object JSONAnnotator {
  def generateAnnotatedExample(schemaString: String): List[String] = {
    val annotatedResult = schemaString
    println("PPPP " + schemaString)
    List(annotatedResult)
  }
}
