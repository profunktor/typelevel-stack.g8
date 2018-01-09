package $package$.repository

import cats.effect.IO
import doobie.h2.H2Transactor
import doobie.scalatest.IOChecker
import doobie.util.transactor.Transactor
import org.flywaydb.core.Flyway
import org.scalatest.{BeforeAndAfterAll, FunSuiteLike}

trait RepositorySpec extends FunSuiteLike with IOChecker with BeforeAndAfterAll {

  private val dbUrl   = "jdbc:h2:mem:users;MODE=PostgreSQL;DB_CLOSE_DELAY=-1"
  private val dbUser  = "sa"
  private val dbPass  = ""

  override def transactor: Transactor[IO] =
    H2Transactor.newH2Transactor[IO](dbUrl, dbUser, dbPass).unsafeRunSync()

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
