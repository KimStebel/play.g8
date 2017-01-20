package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import com.ovoenergy.build.BuildInfo

final case class HealthcheckResponse(name: String, version: String, isHealthy: Boolean, dependencies: Map[String, Boolean])

object Application extends Controller {

  def healthcheck = Action {
    val hcr = HealthcheckResponse("$name$", BuildInfo.version, true, Map())
    implicit val hcrWrites = Json.writes[HealthcheckResponse]
    val json = Json.toJson(hcr)
    Ok(json)
  }

  def ping = Action {
    Ok("pong")
  }

}
