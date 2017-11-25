package $package$.http

import cats.effect._
import cats.syntax.applicativeError._ // For `recoverWith`
import cats.syntax.flatMap._
import $package$.model._
import $package$.service.UserService
import $package$.validation.UserValidation
import io.circe.{Decoder, Json}
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

object UserHttpEndpoint {
  def apply[F[_] : Effect]: UserHttpEndpoint[F] = new UserHttpEndpoint[F](UserService[F])
}

class UserHttpEndpoint[F[_] : Effect](userService: UserService[F]) extends Http4sDsl[F] {

  implicit def createUserDecoder[A : Decoder]: EntityDecoder[F, A] = jsonOf[F, A]

  val service: HttpService[F] = HttpService[F] {

    // Find all users
    case GET -> Root =>
      userService.findAll.flatMap(users => Ok(users.asJson))

    // Find user by username
    case GET -> Root / username =>
      val user = userService.findUser(new UserName(username))
      user.flatMap(u => Ok(u.asJson)).recoverWith {
        case UserNotFound(msg) => NotFound(Json.fromString(msg))
      }

    // Create a user
    case req @ POST -> Root =>
      req.decode[CreateUser] { createUser =>
        UserValidation.validateCreateUser(createUser).fold(
          errors  => BadRequest(errors.toList.asJson),
          user    => userService.addUser(user).flatMap(_ => Created())
        )
      }

    // Update a user
    case req @ PUT -> Root / username =>
      req.decode[UpdateUser] { updateUser =>
        UserValidation.validateUpdateUser(updateUser).fold(
          errors  => BadRequest(errors.toList.asJson),
          email   => userService.updateUser(User(new UserName(username), email)).flatMap(_ => Ok())
        )
      }

    // Delete a user
    case DELETE -> Root / username =>
      userService.deleteUser(new UserName(username)).flatMap(_ => NoContent())

  }

}

