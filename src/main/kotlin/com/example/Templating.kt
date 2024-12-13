package com.example

import com.example.model.Priority
import com.example.model.Task
import com.example.model.TaskRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.Thymeleaf
import io.ktor.server.thymeleaf.ThymeleafContent
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

fun Application.configureTemplating() {
    install(Thymeleaf) {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "templates/thymeleaf/"
            suffix = ".html"
            characterEncoding = "utf-8"
        })
    }
    routing {
        get("/tasks") {
            val tasks = TaskRepository.allTasks()
            call.respond(ThymeleafContent("all-tasks", mapOf("tasks" to tasks)))
        }
        get("/byName") {
            val name = call.request.queryParameters["name"]
            if (name == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val task = TaskRepository.tasksByName(name)
            if (task == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            call.respond(ThymeleafContent("single-task", mapOf("task" to task)))
        }
        get("/byPriority"){
            val priorityAsText = call.request.queryParameters["priority"]
            if (priorityAsText == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            try {
                val priority = Priority.valueOf(priorityAsText)
                val tasks = TaskRepository.tasksByPriority(priority)


                if (tasks.isEmpty()) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }
                val data = mapOf(
                    "priority" to priority,
                    "tasks" to tasks
                )
                call.respond(ThymeleafContent("tasks-by-priority", data))
            } catch (ex: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

