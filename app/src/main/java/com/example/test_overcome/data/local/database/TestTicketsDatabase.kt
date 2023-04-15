package com.example.test_overcome.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.test_overcome.data.local.converters.FilesTypeConverter
import com.example.test_overcome.data.local.converters.StringListConverter
import com.example.test_overcome.data.local.dao.TicketDao
import com.example.test_overcome.model.Files
import com.example.test_overcome.model.Ticket

@Database(entities = [Ticket::class, Files::class], version = 1)
@TypeConverters(StringListConverter::class, FilesTypeConverter::class)
abstract class TestTicketsDatabase : RoomDatabase(){

    abstract fun ticketDao(): TicketDao

    companion object{
        @JvmStatic
        fun newInstance(context: Context) : TestTicketsDatabase{
            return Room.databaseBuilder(context, TestTicketsDatabase::class.java, "TestTicket")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}