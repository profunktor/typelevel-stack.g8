package $package$.repository

import cats.effect.{ContextShift, IO}
//import doobie.h2.H2Transactor
//import doobie.scalatest.IOChecker
//import doobie.util.transactor.Transactor
import org.flywaydb.core.Flyway
import org.scalatest.{BeforeAndAfterAll, FunSuiteLike}

import scala.concurrent.ExecutionContext

// FIXME: IOChecker is outdated
//trait RepositorySpec extends FunSuiteLike with IOChecker with BeforeAndAfterAll {
trait RepositorySpec extends FunSuiteLike with BeforeAndAfterAll {

  val ec: ExecutionContext = ExecutionContext.global
  implicit val cs: ContextShift[IO] = IO.contextShift(ec)

  val dbUrl   = "jdbc:h2:mem:users;MODE=PostgreSQL;DB_CLOSE_DELAY=-1"
  val dbUser  = "sa"
  val dbPass  = ""

  //override def transactor: Transactor[IO] =
  //  H2Transactor.newH2Transactor[IO](dbUrl, dbUser, dbPass, ec, ec).unsafeRunSync()

  override protected def beforeAll(): Unit = {
    super.beforeAll()
    migrateDB.unsafeRunSync()
  }

  private val migrateDB: IO[Unit] =
    IO {
      val flyway = new Flyway
      flyway.setDataSource(dbUrl, dbUser, dbPass)
      flyway.migrate()
    }

}
