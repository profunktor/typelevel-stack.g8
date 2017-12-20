package $package$.service

import cats.effect.IO
import $package$.TestUsers.users
import $package$.model.{User, UserName}
import $package$.repository.algebra.UserRepository

object TestUserService {

  private val testUserRepo: UserRepository[IO] =
    (username: UserName) => IO {
      users.find(_.username.value == username.value)
    }

  val service: UserService[IO] = new UserService[IO](testUserRepo)

}
