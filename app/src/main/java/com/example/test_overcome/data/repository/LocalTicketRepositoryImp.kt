package com.example.test_overcome.data.repository

import com.example.test_overcome.data.local.database.TestTicketsDatabase
import com.example.test_overcome.model.Ticket
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalTicketRepositoryImp @Inject constructor(
    private val testTicketDatabase: TestTicketsDatabase
): LocalTicketRepository {
    override fun getAllTickets(): Flow<List<Ticket?>> {
        return testTicketDatabase.ticketDao().getAllTickets()
    }

    override suspend fun insertTicket(ticket: Ticket) {
        return testTicketDatabase.ticketDao().insertTicket(ticket)
    }

    override suspend fun updateTicket(ticket: Ticket) {
        return testTicketDatabase.ticketDao().updateTicket(ticket)
    }

    override fun getTicketById(id: Int): Flow<Ticket?> {
        return  testTicketDatabase.ticketDao().getTicketById(id)
    }
}