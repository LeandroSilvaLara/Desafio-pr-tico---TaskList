Explicação:
Task é uma data class que representa cada tarefa, gerando automaticamente toString, equals, hashCode e suporte à desestruturação.
Companion Object é usado para gerar IDs únicos automaticamente usando AtomicLong.
TaskManager gerencia as tarefas, armazenadas em uma lista mutável (tasks).
Métodos como addTask, listTasks, updateTaskStatus, deleteTask e filterTasks implementam as funcionalidades descritas no desafio.
Sealed class Resultado representa o retorno das operações com estados Success e Error.
Funções de extensão:
Task.formatar formata uma tarefa como string.
List<Task>.countCompleted conta as tarefas concluídas na lista.
