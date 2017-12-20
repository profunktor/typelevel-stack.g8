package $package$

object model {

  class Email(val value: String) extends AnyVal
  class UserName(val value: String) extends AnyVal

  case class User(username: UserName, email: Email)

  // Business errors
  sealed trait ApiError extends Product with Serializable
  case class UserNotFound(username: UserName) extends ApiError

  // Http model
  case class CreateUser(username: String, email: String)
  case class UpdateUser(email: String)

}
