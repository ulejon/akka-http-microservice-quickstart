package service.users

import javax.inject.{Inject, Named}

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import spray.json.DefaultJsonProtocol

import scala.concurrent.Future

@Named
class UserService @Inject()(userRepository: UserRepository) extends DefaultJsonProtocol with SprayJsonSupport {

  val userRoutes: Route =
    path("users") {
      get {
        complete(userRepository.getUsers)
      } ~
        post {
          entity(as[String]) { rawUser =>
            val saved: Future[User] = userRepository.createUser(rawUser)
            onComplete(saved) { done =>
              complete(done)
            }
          }
        }
    } ~
      path("users" / IntNumber) { id =>
        get {
          complete(userRepository.getUser(id))
        }
      }
}
