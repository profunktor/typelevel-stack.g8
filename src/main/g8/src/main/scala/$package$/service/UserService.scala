package $package$.service

import cats.effect.{Async, Sync}
import cats.syntax.applicative._ // For `pure`
import cats.syntax.flatMap._
import $package$.persistence.UserDao
import $package$.model._

object UserService {
  def apply[F[_] : Async]: UserService[F] = new UserService[F](UserDao[F])
}

class UserService[F[_] : Async](userDao: UserDao[F]) {

  def findUser(username: UserName): F[User] = {
    val ifEmpty = Sync[F].raiseError[User](UserNotFound(username.value))
    userDao.find(username) flatMap { maybeUser =>
      maybeUser.fold(ifEmpty)(_.pure[F])
    }
  }

}

