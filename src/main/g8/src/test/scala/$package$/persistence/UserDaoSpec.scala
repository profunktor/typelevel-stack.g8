package $package$.persistence

import cats.Applicative
import cats.effect.IO
import doobie.free.connection.ConnectionIO
import doobie.h2._
import doobie.implicits._
import doobie.util.transactor.Transactor
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}
import $package$.IOAssertion
import $package$.TestUsers._
import $package$.model.{Email, User, UserName}

// The difference with the QueryDaoSpec is that here we test everything and not only a single query
class UserDaoSpec extends FlatSpecLike with Matchers with BeforeAndAfterAll with H2Setup {

  private val h2Transactor: IO[H2Transactor[IO]] = 
    H2Transactor[IO]("jdbc:h2:mem:users;MODE=PostgreSQL;DB_CLOSE_DELAY=-1", "sa", "")

  override def beforeAll(): Unit = {
    super.beforeAll()

    val program = for {
      xa  <- h2Transactor
      _   <- createUserTable.transact(xa)
      _   <- insertData(xa)
    } yield ()

    program.unsafeRunSync()
  }

  it should "find an user or not" in IOAssertion {
    for {
      xa    <- h2Transactor
      dao   = new PostgresUserDao[IO](xa)
      user1 <- dao.find(users.head.username)
      user2 <- dao.find(new UserName("random fella"))
    } yield {
      user1 should be (Some(users.head))
      user2 should be (None)
    }
  }

}

trait H2Setup {

  import cats.instances.list._

  val createUserTable: ConnectionIO[Int] =
    sql"""
      CREATE TABLE api_user (
        id SERIAL PRIMARY KEY,
        username VARCHAR (100) NOT NULL UNIQUE,
        email VARCHAR (500) NOT NULL UNIQUE
      )
      """.update.run

  private def insertUserStatement(user: User): ConnectionIO[Int] = {
    sql"INSERT INTO api_user (username, email) VALUES (\${user.username}, \${user.email})"
      .update.withUniqueGeneratedKeys("id")
  }

  def insertData(xa: Transactor[IO]): IO[List[Int]] = {
    Applicative[IO].traverse(users)(u => insertUserStatement(u).transact(xa))
  }

}
