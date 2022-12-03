package ru.akirakozov.model;


public class Task {
    public enum TaskStatus {
        ACTIVE,
        DONE
    }
    private int id;
    private int listId;
    private String name;
    private TaskStatus status;

    public Task() {
        this.name = "";
        this.status = TaskStatus.ACTIVE;
    }

    public Task(int id, int listId, String name) {
        this.id = id;
        this.listId = listId;
        this.name = name;
        this.status = TaskStatus.ACTIVE;
    }

    @Override
    public String toString() {
        return "Task \""+ name +"\"" + (status == TaskStatus.ACTIVE ? "" : "✔");
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public boolean isActive() {
        return status.equals(TaskStatus.ACTIVE);
    }

    public void setName(String name) {
        this.name = name;
    }
}