package $package$.service

import org.scalatest.{FlatSpecLike, Matchers}
import $package$.IOAssertion
import $package$.TestUsers._
import $package$.model.{UserName, UserNotFound}

class UserServiceSpec extends FlatSpecLike with Matchers {

  it should "retrieve an user" in IOAssertion {
    TestUserService.service.findUser(users.head.username).map { user =>
      user should be (users.head)
    }
  }

  it should "fail retrieving an user" in {
    intercept[UserNotFound] {
      TestUserService.service.findUser(new UserName("xxx")).unsafeRunSync()
    }
  }

}
