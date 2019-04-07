package $package$

import cats.effect.{ConcurrentEffect, ContextShift, ExitCode, IO, IOApp, Timer}
import cats.syntax.functor._
import org.http4s.server.blaze.BlazeServerBuilder

// The only place where the Effect is defined. You could change it for `TaskApp` and `monix.eval.Task` for example.
object Server extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    new HttpServer[IO].server.as(ExitCode.Success)

}

class HttpServer[F[_]: ConcurrentEffect: ContextShift: Timer] {

  private val ctx = new Module[F]

  def server: F[Unit] =
    BlazeServerBuilder[F]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(ctx.httpApp)
      .serve
      .compile
      .drain

}
