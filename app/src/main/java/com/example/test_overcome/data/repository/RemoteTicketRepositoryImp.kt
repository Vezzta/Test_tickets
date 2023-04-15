package com.example.test_overcome.data.repository

import com.example.test_overcome.model.Ticket
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlinx.coroutines.flow.asFlow

class RemoteTicketRepositoryImp @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : RemoteTicketRepository {
    override fun getAllTickets(): Flow<List<Ticket?>> {
        val reference = firebaseDatabase.getReference("tickets")
        return callbackFlow {
            val eventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val tickets = mutableListOf<Ticket>()
                    for (childSnapshot in snapshot.children) {
                        val ticket = childSnapshot.getValue(Ticket::class.java)
                        if (ticket != null) {
                            tickets.add(ticket)
                        }
                    }
                    trySend(tickets).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }
            reference.addValueEventListener(eventListener)

            awaitClose { reference.removeEventListener(eventListener) }
        }
    }

    override suspend fun insertTicket(ticket: Ticket) {
        val reference = firebaseDatabase.getReference("tickets")
        val id = ticket.id
        var existingTicket =
            id?.toDouble()?.let { reference.orderByChild("id").equalTo(it).get().await() }

        if (existingTicket?.exists() == true) {
            var newId = id
            do {
                newId = newId!! + 1
                existingTicket =
                    reference.orderByChild("id").equalTo(newId.toDouble()).get().await()
            } while (existingTicket?.exists()!!)

            ticket.id = newId
        }

        reference.child(ticket.id.toString()).setValue(ticket)
    }

    override suspend fun updateTicket(ticket: Ticket) {
        val reference = firebaseDatabase.getReference("tickets/${ticket.id}")
        reference.setValue(ticket)
    }

    override fun getTicketById(id: Int): Flow<Ticket?> {
        val reference = firebaseDatabase.getReference("tickets/$id")
        return callbackFlow {
            val eventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val ticket = snapshot.getValue(Ticket::class.java)
                    trySend(ticket).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }
            reference.addValueEventListener(eventListener)

            awaitClose { reference.removeEventListener(eventListener) }
        }
    }

    override fun observeTickets(): Flow<List<Ticket?>> {
        val reference = firebaseDatabase.getReference("tickets")
        return callbackFlow {
            val eventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val tickets = mutableListOf<Ticket?>()
                    for (childSnapshot in snapshot.children) {
                        val ticket = childSnapshot.getValue(Ticket::class.java)
                        tickets.add(ticket)
                    }
                    trySend(tickets).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }
            reference.addValueEventListener(eventListener)

            awaitClose { reference.removeEventListener(eventListener) }
        }
    }

}