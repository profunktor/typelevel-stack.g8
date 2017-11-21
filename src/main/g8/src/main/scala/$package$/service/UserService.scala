package $package$.service

import cats.effect.{Async, Sync}
import cats.syntax.flatMap._ // For `>>=` (alias for `flatMap`)
import $package$.persistence.UserDao
import $package$.model._

object UserService {
  def apply[F[_] : Async]: UserService[F] = new UserService[F](UserDao[F])
}

class UserService[F[_] : Async](userDao: UserDao[F]) {

  def findUser(username: UserName): F[User] = {
    val ifEmpty = Sync[F].raiseError[User](UserNotFound(username.value))
    userDao.find(username).>>= { maybeUser =>
      maybeUser.fold(ifEmpty) { user =>
        Sync[F].delay(user)
      }
    }
  }

}

