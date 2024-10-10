package de.chrisabit.xapiwsdemo.plugins

import de.chrisabit.xapiwsdemo.model.RetrieveRequest
import de.chrisabit.xapiwsdemo.model.StreamRequest
import io.ktor.server.websocket.*

interface CommandRequestHandler {
    suspend fun handleLogin(req: RetrieveRequest, ses: WebSocketServerSession): Boolean
    suspend fun handleRetrieveRequest(req: RetrieveRequest, ses: WebSocketServerSession): Boolean
    suspend fun handleStreamRequest(req: StreamRequest, ses: WebSocketServerSession): Boolean
}