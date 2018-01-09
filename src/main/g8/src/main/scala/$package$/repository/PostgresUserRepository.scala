package $package$.repository

import cats.effect.Async
import cats.syntax.applicativeError._
import $package$.model._
import $package$.repository.algebra.UserRepository
import doobie.free.connection.ConnectionIO
import doobie.implicits._
import doobie.util.invariant.UnexpectedEnd
import doobie.util.query.Query0
import doobie.util.transactor.Transactor

// It requires a created database `users` with db user `postgres` and password `postgres`. See `users.sql` file in resources.
class PostgresUserRepository[F[_] : Async](xa: Transactor[F]) extends UserRepository[F] {

  override def findUser(username: UserName): F[Option[User]] = {
    val statement: ConnectionIO[UserDTO] = UserStatement.findUser(username).unique 

    // You might have more than one query involving joins. In such case a for-comprehension would be better
    val program: ConnectionIO[User] = statement.map(_.toUser)

    program.map(Option.apply).transact(xa).recoverWith {
      case UnexpectedEnd => Async[F].delay(None) // In case the user is not unique in your db. Check out Doobie's docs.
    }
  }

}

object UserStatement {

  def findUser(username: UserName): Query0[UserDTO] = {
    sql"SELECT * FROM api_user WHERE username=\${username.value}"
      .query[UserDTO]
  }

}

