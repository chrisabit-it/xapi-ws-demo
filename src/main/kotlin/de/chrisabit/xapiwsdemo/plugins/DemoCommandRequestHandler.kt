package de.chrisabit.xapiwsdemo.plugins

import de.chrisabit.xapiwsdemo.model.*
import io.ktor.server.websocket.*
import io.ktor.util.logging.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.util.*

class DemoCommandRequestHandler : CommandRequestHandler {
    private val logger = KtorSimpleLogger(DemoCommandRequestHandler::javaClass.name)

    override suspend fun handleLogin(req: RetrieveRequest, ses: WebSocketServerSession): Boolean {
        val status = "login" == req.command
        val streamSessionId = if (status) {
            UUID.randomUUID().toString()
        } else {
            null
        }
        if (status) {
            logger.info("Login $streamSessionId")
        }
        ses.sendSerialized(LoginResult(status = status, streamSessionId = streamSessionId))
        return status
    }

    override suspend fun handleRetrieveRequest(req: RetrieveRequest, ses: WebSocketServerSession): Boolean {
        when (req.command) {
            "ping" -> ses.sendSerialized(RetrieveResponse(status = true))
            "getVersion" -> {
                val returnData = buildJsonObject {
                    put("version", "08.15")
                }
                ses.sendSerialized(RetrieveResponse(status = true, returnData = returnData))
            }
            else -> ses.sendSerialized(RetrieveResponse(status = false))
        }
        return true
    }

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun handleStreamRequest(req: StreamRequest, ses: WebSocketServerSession): Boolean {
        when (req.command) {
            "getTickPrices" -> {
                GlobalScope.launch {
                    val dispatcher = this.coroutineContext
                    CoroutineScope(dispatcher).launch {
                        val jsonContent = this::class.java.classLoader.getResource("${req.symbol}.json")?.readText()
                        if (jsonContent != null) {
                            val pushRates: List<PushRate> = Json.decodeFromString(jsonContent)

                            while (true) {
                                for (pushRate in pushRates) {
                                    val tickRecord = buildJsonObject {
                                        put("symbol", req.symbol)
                                        put("ask", pushRate.ask)
                                        put("bid", pushRate.bid)
                                    }
                                    ses.sendSerialized(StreamData(command = req.command, tickRecord))
                                    delay(pushRate.ms)
                                }
                            }
                        }
                    }
                }
            }
        }
        return true
    }
}
