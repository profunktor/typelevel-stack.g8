package $package$.http

import cats.effect._
import cats.syntax.applicativeError._ // For `recoverWith`
import cats.syntax.flatMap._ // For `>>=` (alias for `flatMap`)
import $package$.model._
import $package$.service.UserService
import io.circe.Json
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

object UserHttpEndpoint {
  def apply[F[_] : Effect]: UserHttpEndpoint[F] = new UserHttpEndpoint[F](UserService[F])
}

class UserHttpEndpoint[F[_] : Effect](userService: UserService[F]) extends Http4sDsl[F] {

  val service: HttpService[F] = HttpService[F] {
    case GET -> Root / "users" / username =>
      val user = userService.findUser(new UserName(username))
      user.>>=(u => Ok(u.asJson)).recoverWith {
        case UserNotFound(msg) => NotFound(Json.fromString(msg))
      }
  }

}

