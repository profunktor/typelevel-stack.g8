package $package$

object model {

  class Email(val value: String) extends AnyVal
  class UserName(val value: String) extends AnyVal

  case class User(username: UserName, email: Email)

  case class UserNotFound(username: String) extends Exception(s"User not found $username")

}
