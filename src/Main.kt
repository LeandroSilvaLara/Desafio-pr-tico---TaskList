import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicLong

// 1. Data class para representar uma tarefa
data class Task(
    val id: Long,
    val title: String,
    val description: String? = null,
    var isCompleted: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        private val idGenerator = AtomicLong(1)
        fun create(title: String, description: String? = null): Task {
            require(title.isNotBlank()) { "O título da tarefa não pode estar vazio." }
            return Task(idGenerator.getAndIncrement(), title, description)
        }
    }
}

// 2. Classe TaskManager para gerenciar as tarefas
class TaskManager {
    private val tasks = mutableListOf<Task>()

    // Adicionar nova tarefa
    fun addTask(title: String, description: String? = null): Resultado {
        val task = Task.create(title, description)
        tasks.add(task)
        return Resultado.Success("Tarefa adicionada com sucesso.")
    }

    // Listar todas as tarefas (desestruturação)
    fun listTasks() {
        tasks.forEach { (title, isCompleted) ->
            println("Tarefa: $title | Concluída: $isCompleted")
        }
    }

    // Buscar tarefa por ID
    fun findTaskById(id: Long): Task? {
        return tasks.find { it.id == id }
    }

    // Atualizar status da tarefa
    fun updateTaskStatus(id: Long, isCompleted: Boolean): Resultado {
        val task = findTaskById(id) ?: return Resultado.Error("Tarefa não encontrada.")
        task.isCompleted = isCompleted
        return Resultado.Success("Status da tarefa atualizado.")
    }

    // Excluir tarefa por ID
    fun deleteTask(id: Long): Resultado {
        val removed = tasks.removeIf { it.id == id }
        return if (removed) Resultado.Success("Tarefa removida.") else Resultado.Error("Tarefa não encontrada.")
    }

    // Filtrar tarefas concluídas ou pendentes
    fun filterTasks(concluidas: Boolean): List<Task> {
        return tasks.filter { it.isCompleted == concluidas }
    }
}

// 5. Sealed class para representar estados de retorno
sealed class Resultado {
    data class Success(val message: String) : Resultado()
    data class Error(val message: String) : Resultado()
}

// 6. Função de extensão para formatar Task
fun Task.formatar(): String {
    return "Tarefa #$id: $title - ${if (isCompleted) "Concluída" else "Pendente"}"
}

// Função de extensão para contar tarefas concluídas
fun List<Task>.countCompleted(): Int {
    return this.count { it.isCompleted }
}

// Exemplo de uso
fun main() {
    val manager = TaskManager()

    // Adicionar tarefas
    println(manager.addTask("Estudar Kotlin"))
    println(manager.addTask("Fazer exercícios", "Capítulo 5"))

    // Listar tarefas
    println("\nLista de Tarefas:")
    manager.listTasks()

    // Atualizar e listar novamente
    manager.updateTaskStatus(1, true)
    println("\nApós atualização:")
    manager.listTasks()

    // Filtrar concluídas
    val concluidas = manager.filterTasks(true)
    println("\nTarefas Concluídas:")
    concluidas.forEach { println(it.formatar()) }

    // Contagem de tarefas concluídas
    println("\nTarefas concluídas: ${concluidas.countCompleted()}")
}
