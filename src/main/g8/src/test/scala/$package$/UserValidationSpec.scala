package $package$

import org.scalatest.FunSuite
import org.scalatest.prop.PropertyChecks
import $package$.model.CreateUser
import $package$.validation.UserValidation

class UserValidationSpec extends FunSuite with UserValidationFixture {

  forAll(invalidExamples) { createUser =>
    test(s"invalid user \$createUser") {
      assert(UserValidation.validateCreateUser(createUser).isInvalid)
    }
  }

  forAll(validExamples) { createUser =>
    test(s"valid user \$createUser") {
      assert(UserValidation.validateCreateUser(createUser).isValid)
    }
  }

}

trait UserValidationFixture extends PropertyChecks {

  val invalidExamples = Table(
    "createUser",
    CreateUser("", ""),
    CreateUser("", "gvolpe@github.com"),
    CreateUser("gvolpe", ""),
    CreateUser("gvolpe", "g volpe@github.com")
  )

  val validExamples = Table(
    "createUser",
    CreateUser("gvolpe", "gvolpe@github.com"),
    CreateUser("asd", "asd@gmail.com"),
    CreateUser("msaib", "msabin@typelevel.org")
  )

}

