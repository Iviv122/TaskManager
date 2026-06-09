package com.example.taskmanager.models

public class Task(
    private val id : Int,
    private val title : String,
    private val desc : String
){
    fun getTitle() : String{
        return title
    }
    fun getDesc() : String{
        return desc
    }
    fun getId() : Int{
        return id
    }
}