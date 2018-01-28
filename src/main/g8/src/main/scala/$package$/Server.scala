package $package$

import cats.effect.{Effect, IO}
import fs2.StreamApp.ExitCode
import fs2.{Scheduler, Stream, StreamApp}
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext.Implicits.global

// The only place where the Effect is defined. You could change it for `monix.eval.Task` for example.
object Server extends HttpServer[IO]

class HttpServer[F[_] : Effect] extends StreamApp[F] {

  private val ctx = new Module[F]

  override def stream(args: List[String], requestShutdown: F[Unit]): Stream[F, ExitCode] =
    Scheduler(corePoolSize = 2) flatMap { implicit scheduler =>
      BlazeBuilder[F]
        .bindHttp() // Default address `localhost:8080`
        .mountService(ctx.userHttpEndpoint, "/users") // You can mount as many services as you want
        .serve
    }

}
