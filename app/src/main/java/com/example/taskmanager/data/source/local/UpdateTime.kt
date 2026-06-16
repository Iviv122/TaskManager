package com.example.taskmanager.data.source.local

public enum class UpdateTime (
    val updateTime: Long,
    val standartLabel: String,
){
    EveryMinute(60000,"Every minute"),
    EveryFiveMinute(5*60000,"Every 5 minutes"),
    EveryTenMinute(10*60000,"Every 10 minute"),
    EveryFifteenMinute(15*60000,"Every 15 minute"),
    EveryHalfHour(30*60000,"Every 30 minute"),
    EveryHour(60*60000,"Every 60 minutes"),
}

