package $package$.service

import cats.effect.Async
import cats.syntax.applicative._ // For `pure`
import cats.syntax.flatMap._
import $package$.persistence.UserDao
import $package$.model._

object UserService {
  def apply[F[_] : Async]: UserService[F] = new DefaultUserService[F](UserDao[F])
}

class DefaultUserService[F[_] : Async](userDao: UserDao[F]) extends UserService[F] {

  override def findUser(username: UserName): F[User] = {
    val ifEmpty = Async[F].raiseError[User](UserNotFound(username.value))
    userDao.find(username) flatMap { maybeUser =>
      maybeUser.fold(ifEmpty)(_.pure[F])
    }
  }

  // TODO: To be completed by final user :)
  override def findAll: F[List[User]] = List.empty[User].pure[F]
  override def addUser(user: User): F[Unit] = ().pure[F]
  override def updateUser(user: User): F[Unit] = ().pure[F]
  override def deleteUser(username: UserName): F[Unit] = ().pure[F]
}

trait UserService[F[_]] {
  def findAll: F[List[User]]
  def findUser(username: UserName): F[User]
  def addUser(user: User): F[Unit]
  def updateUser(user: User): F[Unit]
  def deleteUser(username: UserName): F[Unit]
}

