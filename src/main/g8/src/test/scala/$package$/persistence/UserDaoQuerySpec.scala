package $package$.persistence

import cats.effect.IO
import doobie.scalatest._
import doobie.util.transactor.Transactor
import org.scalatest.FunSuite
import $package$.model.UserName

// The Doobie way of testing queries using either scalatest or specs2. Here an example with the first one.
class UserDaoQuerySpec extends FunSuite with IOChecker {

  override def transactor: Transactor[IO] = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver", "jdbc:postgresql:users", "postgres", "postgres"
  )

  private val userDao = new PostgresUserDao[IO](transactor)

  test("user by name query") {
    check(userDao.userQuery(new UserName("gvolpe")))
  }
}
