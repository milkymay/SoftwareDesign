package ru.akirakozov.dao;

import ru.akirakozov.model.Task;
import ru.akirakozov.model.TaskList;

import java.util.List;

public interface TaskDao {
    List<TaskList> getAllLists();

    List<Task> getTasksByListId(int listId);

    void addList(TaskList list);

    void deleteList(int listId);

    void addTask(Task task);

    void deleteTask(int taskId, int listId);

    void markAsDone(int taskId, int listId);
}
