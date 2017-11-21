package $package$.persistence

import cats.effect.Async
import cats.syntax.applicativeError._ // For `recoverWith`
import $package$.model._
import doobie.free.connection.ConnectionIO
import doobie.implicits._
import doobie.util.invariant.UnexpectedEnd
import doobie.util.transactor.Transactor

// It requires a created database `users` with user `postgres` and password `postgres`
object UserDao {
  def apply[F[_] : Async]: UserDao[F] = new PostgresUserDao[F](
    Transactor.fromDriverManager[F](
      "org.postgresql.Driver", "jdbc:postgresql:users", "postgres", "postgres")
    )
  )
}

class PostgresUserDao[F[_] : Async](xa: Transactor[F]) extends UserDao[F] {

  override def find(username: UserName): F[Option[User] = {
    val userStatement: ConnectionIO[UserDTO] =
      sql"SELECT u.id, u.username, u.email FROM user AS u WHERE u.username=${username.value}"
        .query[UserDTO].unique

    // You migth have more than one query involving joins. In such a case a for-comprehention would be better
    val program: ConnectionIO[User] = userStatement.flatMap(_.toUser)

    program.map(Option.apply).transact(xa).recoverWith {
      case UnexpectedEnd => Async[F].delay(None) // In case the user is not unique in your db. Check out Doobie's docs.
    }
  }

}

trait UserDao[F[_]] {
  def find(username: UserName): F[Option[User]]
}

