package controllers

import annotated.JSONAnnotator
import models.{AnnotatedJSON, JSONSchema}

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {

  def taskForm = Form (
    mapping(
      "schema" -> optional(text)
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
    taskForm.bindFromRequest.fold(
      errors => defaultView,
      jsonSchema => jsonSchema match {
        case JSONSchema(Some(schemaStr)) => {
          val jsonExamples = JSONAnnotator.generateAnnotatedExample(schemaStr)
          // keep the input form as it looked upon submission
          val filledTaskForm = taskForm.fill(jsonSchema)
          // update the result form(s)
          val resultsForms = jsonExamples.map(cd => {
            resultsForm.fill(AnnotatedJSON(Some(cd)))
          })
          Ok(views.html.index(filledTaskForm, resultsForms, "example"))
        }
        case _ => defaultView
      }
    )
  }

}
