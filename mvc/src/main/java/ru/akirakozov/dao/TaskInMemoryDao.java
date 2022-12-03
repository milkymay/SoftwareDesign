package ru.akirakozov.dao;

import ru.akirakozov.model.Task;
import ru.akirakozov.model.TaskList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TaskInMemoryDao implements TaskDao {
    private final AtomicInteger lastListId = new AtomicInteger(0);
    private final AtomicInteger lastTaskId = new AtomicInteger(0);
    private final HashMap<Integer, TaskList> taskLists = new HashMap<>();
    private final List<Task> tasks = new ArrayList<>();

    @Override
    public List<TaskList> getAllLists() {
        return new ArrayList<>(taskLists.values());
    }

    @Override
    public List<Task> getTasksByListId(int listId) {
        return tasks.stream().filter(t -> t.getListId() == listId).collect(Collectors.toList());
    }

    @Override
    public void addList(TaskList list) {
        int id = lastListId.incrementAndGet();
        list.setId(id);
        taskLists.put(list.getId(), list);
    }

    @Override
    public void deleteList(int listId) {
        taskLists.remove(listId);
    }

    @Override
    public void addTask(Task task) {
        int listId = task.getListId();
        if (!taskLists.containsKey(listId)) {
            return;
        }
        int id = lastTaskId.incrementAndGet();
        task.setId(id);
        task.setListId(listId);
        tasks.add(task);
    }

    private Task findTask(final int taskId, final int listId) {
        for (final Task task : tasks) {
            if (task.getId() == taskId && task.getListId() == listId) {
                return task;
            }
        }
        return null;
    }

    @Override
    public void deleteTask(int taskId, int listId) {
        final Task task = findTask(taskId, listId);
        tasks.remove(task);
    }

    @Override
    public void markAsDone(int taskId, int listId) {
        final Task task = findTask(taskId, listId);
        if (task == null) {
            return;
        }
        task.setStatus(Task.TaskStatus.DONE);
    }
}