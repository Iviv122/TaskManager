package com.example.taskmanager.util

import androidx.room.TypeConverter
import java.sql.Date

public class DateConverter {
    @TypeConverter
    public fun toDate(dateLong: Long): Date {
        return Date(dateLong);
    }

    @TypeConverter
    public fun fromDate(date: Date): Long {
        return date.time;
    }
}