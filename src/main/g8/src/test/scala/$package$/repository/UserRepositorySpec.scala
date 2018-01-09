package $package$.repository

import cats.effect.IO
import $package$.IOAssertion
import $package$.TestUsers._

class UserRepositorySpec extends RepositorySpec {

  private val repo = new PostgresUserRepository[IO](transactor)

  test("Find a user"){
    IOAssertion {
      for {
        user <- repo.findUser(users.head.username)
      } yield {
        user.fold(fail("User not found")) { u =>
          assert(u.username.value == users.head.username.value)
          assert(u.email.value == users.head.email.value)
        }
      }
    }
  }

  test("NOT find a user"){
    IOAssertion {
      for {
        user <- repo.findUser(users.last.username)
      } yield {
        assert(user.isEmpty)
      }
    }
  }

  test("find user query") {
    check(UserStatement.findUser(users.head.username))
  }

}
