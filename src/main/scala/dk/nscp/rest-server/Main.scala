package dk.nscp.rest_server

import akka.util.Timeout
import scala.concurrent.duration._
import akka.pattern.ask
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.io.StdIn

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val healthFormat = jsonFormat2(Health)
}

object Main extends App with JsonSupport {

  val host = "localhost"
  val port = 8080

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(20.seconds)

  val requestHandler = system.actorOf(RequestHandler.props(), "requesthandler")
  
  val route: Route = {
    path("health") {
      get {
        onSuccess(requestHandler ? GetHealthRequest) {
          case response: HealthResponse =>
            complete(response.health)
          case _ =>
            complete(StatusCodes.InternalServerError)
        }
      } ~
      post {
        entity(as[Health]) { statusReport =>
          onSuccess(requestHandler ? SetStatusRequest(statusReport)) {
            case response: HealthResponse =>
              complete(StatusCodes.OK, s"Posted health as ${response.health.status}!")
            case _ =>
              complete(StatusCodes.InternalServerError)
          }
        }
      }
    }
  }

  val bindingFuture = Http().bindAndHandle(route, host, port)
  println(s"\nServer running on $host:$port\nhit RETURN to terminate")
  StdIn.readLine()

  bindingFuture.flatMap(_.unbind())
  system.terminate()
}

