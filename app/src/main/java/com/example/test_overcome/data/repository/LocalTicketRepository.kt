package com.example.test_overcome.data.repository

import com.example.test_overcome.model.Ticket
import kotlinx.coroutines.flow.Flow

interface LocalTicketRepository {

    fun getAllTickets(): Flow<List<Ticket?>>

    suspend fun insertTicket(ticket: Ticket)

    suspend fun updateTicket(ticket: Ticket)

    fun getTicketById(id: Int): Flow<Ticket?>
}