package com.example.test_overcome.data.local.dao

import androidx.room.*
import com.example.test_overcome.model.Ticket
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Query("SELECT * FROM ticket")
    fun getAllTickets(): Flow<List<Ticket?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: Ticket)

    @Update
    suspend fun updateTicket(ticket: Ticket)

    @Query("SELECT * FROM ticket WHERE id=:id")
    fun getTicketById(id: Int): Flow<Ticket?>
}