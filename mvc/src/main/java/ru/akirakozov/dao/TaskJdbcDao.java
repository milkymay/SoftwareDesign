package ru.akirakozov.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import ru.akirakozov.model.Task;
import ru.akirakozov.model.TaskList;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author akirakozov
 */
public class TaskJdbcDao extends JdbcDaoSupport implements TaskDao {

    public TaskJdbcDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);

        getJdbcTemplate().update("CREATE TABLE IF NOT EXISTS lists" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "name TEXT NOT NULL)");
        getJdbcTemplate().update("CREATE TABLE IF NOT EXISTS tasks" +
                "(id INTEGER NOT NULL," +
                "listId INTEGER NOT NULL," +
                "name TEXT NOT NULL," +
                "status INTEGER NOT NULL," +
                "PRIMARY KEY (id, listId))");
    }

    @Override
    public List<TaskList> getAllLists() {
        String sql = "SELECT * FROM lists ORDER BY id";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(TaskList.class));
    }

    @Override
    public List<Task> getTasksByListId(int listId) {
        String sql = "SELECT * FROM tasks WHERE listId=" + listId + " ORDER BY id";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(Task.class));
    }

    @Override
    public void addList(TaskList taskList) {
        String sql = "INSERT INTO lists (name) VALUES (?)";
        getJdbcTemplate().update(sql, taskList.getName());
    }

    @Override
    public void deleteList(int listId) {
        getJdbcTemplate().update("DELETE FROM lists WHERE id = " + listId);
        getJdbcTemplate().update("DELETE FROM tasks WHERE listId = " + listId);
    }

    @Override
    public void addTask(Task task) {
        final String sqlGetCount = "SELECT * FROM tasks WHERE listId = " + task.getListId();
        final List<Task> tasks = getJdbcTemplate().query(sqlGetCount, new BeanPropertyRowMapper<>(Task.class));
        final int nextTaskId = tasks.isEmpty() ? 0 : tasks.get(tasks.size() - 1).getId();
        final String sqlInsert = "INSERT INTO tasks (id, listId, name, status) VALUES (?, ?, ?, ?)";
        getJdbcTemplate().update(sqlInsert, nextTaskId + 1, task.getListId(), task.getName(), 0);
    }

    @Override
    public void deleteTask(int id, int listId) {
        String sql = "DELETE FROM tasks WHERE id = " + id + " AND listId = " + listId;
        getJdbcTemplate().update(sql);
    }

    @Override
    public void markAsDone(int id, int listId) {
        String sql = "UPDATE tasks SET status = 1 WHERE id = " + id + " AND listId = " + listId;
        getJdbcTemplate().update(sql);
    }
}