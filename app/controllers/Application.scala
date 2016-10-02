package controllers

import annotated.JSONAnnotator
import javax.inject.Inject
import models.{AnnotatedJSON, JSONSchema}

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

  def resultsForm = Form (
    mapping(
      "example" -> optional(text)
    )(AnnotatedJSON.apply)(AnnotatedJSON.unapply)
  )

  val defaultView = Ok(views.html.index(taskForm, List(resultsForm), ""))

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
    // val boundTaskForm = taskForm
    //   .bind(queryJSON)// bindFromRequest uncontrollably splits at ',' so bind manually
    //   .copy(value = Some(JSONSchema(Some(queryString))))
    // boundTaskForm.fold(
    //   errors => defaultView,
    //   jsonSchema => {
    //     jsonSchema match {
    //       case JSONSchema(Some(schemaStr)) => {
    //         val annotated = JSONAnnotator.generateAnnotatedExample(queryJSON)
    //         // update the result form(s)
    //         val resultsForms = annotated.map(obj => {
    //           resultsForm.fill(AnnotatedJSON(Some(Json.prettyPrint(obj))))
    //         })
    //         // keep the input form as it looked upon submission
    //         val filledTaskForm = taskForm.fill(JSONSchema(Some(schemaStr)))
    //         Ok(views.html.index(filledTaskForm, resultsForms, "example"))
    //       }
    //       case _ => defaultView
    //     }
    //   }
    // )
    tryJsonParse match {
      case Success(jsValue) => {
        val annotated = JSONAnnotator.generateAnnotatedExample(jsValue)
        // update the result form(s)
        val resultsForms = annotated.map(obj => {
          resultsForm.fill(AnnotatedJSON(Some(Json.prettyPrint(obj))))
        })
        // keep the input form as it looked upon submission
        val filledTaskForm = taskForm.fill(JSONSchema(Some(queryString)))
        Ok(views.html.index(filledTaskForm, resultsForms, "example"))
      }
      case Failure(e) => defaultView
    }
  }

}
