package com.example.taskmanager.data.source.local

public enum class UpdateTime(
    val updateTime: Long,
    val standardLabel: String,
) {
    EveryFifteenSeconds(60000 / 4, "Every 15 seconds"),
    EveryMinute(60000, "Every minute"),
    EveryFiveMinute(5 * 60000, "Every 5 minutes"),
    EveryTenMinute(10 * 60000, "Every 10 minutes"),
    EveryFifteenMinute(15 * 60000, "Every 15 minutes"),
    EveryHalfHour(30 * 60000, "Every 30 minutes"),
    EveryHour(60 * 60000, "Every 60 minutes"),
}

