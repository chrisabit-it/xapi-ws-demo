package de.chrisabit.xapiwsdemo.plugins

import de.chrisabit.xapiwsdemo.model.RetrieveRequest
import de.chrisabit.xapiwsdemo.model.StreamRequest
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.get
import java.time.Duration

fun Application.configureSockets() {
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {
        webSocket("/demo") {
            call.application.log.info("demo")

            val requestHandler = call.application.get<CommandRequestHandler>()

            var loggedIn = false

            try {
                do {
                    val retrieveRequest = receiveDeserialized<RetrieveRequest>()
                    loggedIn = if (!loggedIn) {
                        requestHandler.handleLogin(retrieveRequest, this)
                    } else {
                        requestHandler.handleRetrieveRequest(retrieveRequest, this)
                    }
                } while (loggedIn)

                close(CloseReason(CloseReason.Codes.NORMAL, "done"))
            }
            catch (e: ClosedReceiveChannelException) {
                call.application.log.info("websocket closed")
            }
        }
    }
    routing {
        webSocket("/demoStream") {
            call.application.log.info("demoStream")
            val requestHandler = call.application.get<CommandRequestHandler>()

            try {
                while (true) {
                    val streamRequest = receiveDeserialized<StreamRequest>()
                    call.application.log.info(streamRequest.symbol)
                    requestHandler.handleStreamRequest(streamRequest, this)
                }
            }
            catch (e: ClosedReceiveChannelException) {
                call.application.log.info("websocket closed")
            }
        }
    }
}
