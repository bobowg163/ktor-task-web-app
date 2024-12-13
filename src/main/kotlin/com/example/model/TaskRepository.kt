package com.example.model

object TaskRepository {
    private val tasks = mutableListOf(
        Task("cleaning", "Clean the house", Priority.Low),
        Task("gardening", "Mow the lawn", Priority.Medium),
        Task("shopping", "Buy the groceries", Priority.High),
        Task("painting", "Paint the fence", Priority.Medium)
    )

    fun allTasks(): List<Task> = tasks

    fun tasksByPriority(priority: Priority): List<Task> = tasks.filter { it.priority == priority }

    fun tasksByName(name: String) = tasks.find { it.name.equals(name, true) }

    fun addTask(task: Task) {
        if(tasksByName(task.name) != null) {
            throw IllegalArgumentException("Task with name ${task.name} already exists")
        }
        tasks.add(task)
    }
}