package $package$.service

import cats.effect.IO
import $package$.TestUsers.users
import $package$.model.{User, UserName}
import $package$.persistence.UserDao

object TestUserService {

  val service: UserService[IO] = new UserService[IO](
    new UserDao[IO] {
      override def find(username: UserName): IO[Option[User]] = IO {
        users.find(_.username.value == username.value)
      }
    }
  )

}
