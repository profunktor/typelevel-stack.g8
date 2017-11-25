package $package$

import cats.data.ValidatedNel
import cats.syntax.apply._
import cats.syntax.validated._
import $package$.model._

object validation {

  object UserValidation {
    type Result[A] = ValidatedNel[String, A]

    private def validateUsername(username: String): Result[UserName] = {
      if (username.matches("^[a-zA-Z0-9]+\$")) new UserName(username).validNel[String]
      else "Invalid username".invalidNel
    }

    private def validateEmail(email: String): Result[Email] = {
      if ("""(\w+)@([\w\.]+)""".r.unapplySeq(email).isDefined) new Email(email).validNel[String]
      else "Invalid email".invalidNel
    }

    def validateCreateUser(createUser: CreateUser): Result[User] = {
      val validatedUsername = validateUsername(createUser.username)
      val validatedEmail    = validateEmail(createUser.email)
      (validatedUsername, validatedEmail).mapN(User.apply)
    }

    def validateUpdateUser(updateUser: UpdateUser): Result[Email] = {
      validateEmail(updateUser.email)
    }
  }

}

