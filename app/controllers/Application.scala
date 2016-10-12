package controllers

import annotator.JSONAnnotator
import javax.inject.Inject
import models._//{AnnotatedJSON, DocResult, JSONSchema}

import play.api._
import play.api.i18n.{MessagesApi, I18nSupport}
import play.api.libs.json.{Json, JsValue}
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import scala.util.{Failure, Success, Try}

class Application @Inject() (implicit val webJarAssets: WebJarAssets, val messagesApi: MessagesApi)
  extends Controller with I18nSupport {

  val schemaKey = "schema"

  def taskForm = Form (
    mapping(
      schemaKey -> optional(text)
    )(JSONSchema.apply)(JSONSchema.unapply)
  )

  // def resultsForm = Form (
  //   mapping(
  //     "jsons" -> text
  //   )(AnnotatedJSON.apply)(AnnotatedJSON.unapply)
  // )

  def resultsForm = Form(
    mapping(
      "returnedData" -> text,
      "exampleResponse" -> text,
      "types" -> text
    )(DocResult.apply)(DocResult.unapply)
  )

  val defaultView = Ok(views.html.index(taskForm, List(resultsForm)))

  def index = Action {
    defaultView
  }

  def generate = Action { implicit request =>
    val queryString: String =
      request
        .queryString
        .get(schemaKey)
        .map(_.mkString(","))
        .getOrElse("no input found")
    val tryJsonParse = Try(Json.parse(queryString))
    tryJsonParse match {
      case Success(jsValue) => {
        // generate result
        val docResult = JSONAnnotator.generateDocsFromSchema(jsValue)
        // update the result form(s)
        val resultsForms = List(resultsForm.fill(docResult))
        // keep the input form as it looked upon submission
        val filledTaskForm = taskForm.fill(JSONSchema(Some(queryString)))
        Ok(views.html.index(filledTaskForm, resultsForms))
      }
      case Failure(e) => defaultView
    }
  }

}
