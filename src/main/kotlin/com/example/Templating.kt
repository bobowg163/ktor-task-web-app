package com.example

import com.example.model.Priority
import com.example.model.Task
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
        get("/html-thymeleaf") {
            call.respond(ThymeleafContent("index", mapOf("user" to ThymeleafUser(1, "user1"))))
        }
        get("/tasks") {
            val tasks = listOf(
                Task("cleaning", "Clean the house", Priority.Low),
                Task("gardening", "Mow the lawn", Priority.Medium),
                Task("shopping", "Buy the groceries", Priority.High),
                Task("painting", "Paint the fence", Priority.Medium)
            )
            call.respond(ThymeleafContent("all-tasks", mapOf("tasks" to tasks)))
        }
    }
}
data class ThymeleafUser(val id: Int, val name: String)
