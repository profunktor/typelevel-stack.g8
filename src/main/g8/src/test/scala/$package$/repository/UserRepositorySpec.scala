package $package$.repository

import cats.effect.IO
import doobie.h2.H2Transactor
import $package$.IOAssertion
import $package$.TestUsers._

class UserRepositorySpec extends RepositorySpec {

  test("Find a user"){
    IOAssertion {
      H2Transactor.newH2Transactor[IO](dbUrl, dbUser, dbPass, ec, ec).use { xa =>
        val repo = new PostgresUserRepository[IO](xa)
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
  }

  test("NOT find a user"){
    IOAssertion {
      H2Transactor.newH2Transactor[IO](dbUrl, dbUser, dbPass, ec, ec).use { xa =>
        val repo = new PostgresUserRepository[IO](xa)
        for {
          user <- repo.findUser(users.last.username)
        } yield {
          assert(user.isEmpty)
        }
      }
    }
  }

  //FIXME: IOChecker is not updated. Still requires to implement transactor: Transactor[M]
//  test("find user query") {
//    check(UserStatement.findUser(users.head.username))
//  }

}
