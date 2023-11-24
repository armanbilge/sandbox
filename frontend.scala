//> using platform js
//> using dep com.armanbilge::calico::0.2.1
//> using dep org.http4s::http4s-dom::0.2.10

import calico.*
import calico.html.io.{*, given}
import cats.effect.*
import cats.syntax.all.*
import fs2.concurrent.SignallingRef
import org.http4s.dom.FetchClientBuilder

object Frontend extends IOWebApp:

  enum Tab:
    case Home, Request

  def render =
    SignallingRef[IO].of(Tab.Home).toResource.flatMap { nav =>
      div(
        cls := "container is-widescreen",
        div(
          cls := "tabs",
          ul(
            Tab.values.toList.map { page =>
              li(
                cls <-- nav.map(_ == page).ifF(List("is-active"), Nil),
                a(onClick --> (_.foreach(_ => nav.set(page))), page.toString)
              )
            }
          )
        ),
        nav.map {
          case Tab.Home =>
            div(
              "Navigate to the Request tab to send an HTTP request to the API"
            )
          case Tab.Request =>
            val client = FetchClientBuilder[IO].create
            SignallingRef[IO].of(false).toResource.flatMap { sent =>
              div(
                sent.map {
                  case false =>
                    "Click the button to send an HTTP request to the API"
                  case true =>
                    "Request sent! Now navigate back to the Home tab and watch the server logs."
                },
                br(()),
                button(
                  disabled <-- sent,
                  onClick --> {
                    _.foreach(_ => sent.set(true) *> client.expect[Unit]("/api"))
                  },
                  "Request!"
                )
              )
            }

        }
      )
    }
