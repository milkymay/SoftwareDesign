package ru.akirakozov.controller;

import ru.akirakozov.dao.TaskDao;
import ru.akirakozov.model.Task;
import ru.akirakozov.model.TaskList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    private final TaskDao taskDao;

    public TaskController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @RequestMapping(value = "/lists", method = RequestMethod.GET)
    public String showTaskLists(ModelMap map) {
        List<TaskList> lists = taskDao.getAllLists();
        List<TaskList> resultLists = new ArrayList<>();
        for (TaskList list : lists) {
            TaskList tempList = list.copyWithoutTasks();
            taskDao.getTasksByListId(tempList.getId()).forEach(tempList::addTask);
            resultLists.add(tempList);
        }
        prepareModelMap(map, resultLists);
        return "index";
    }

    @RequestMapping(value = "/add-list", method = RequestMethod.POST)
    public String addList(@ModelAttribute("taskList") TaskList taskList) {
        taskDao.addList(taskList);
        return "redirect:/lists";
    }

    @RequestMapping(value = "/add-task", method = RequestMethod.POST)
    public String addTask(@ModelAttribute("task") Task task) {
        taskDao.addTask(task);
        return "redirect:/lists";
    }

    @RequestMapping(value = "/delete-list", method = RequestMethod.POST)
    public String deleteList(@RequestParam(name = "taskListId") int listId) {
        taskDao.deleteList(listId);
        return "redirect:/lists";
    }

    @RequestMapping(value = "/mark-task", method = RequestMethod.POST)
    public String markAsFinished(@RequestParam(name = "taskId") int taskId, @RequestParam(name = "listId") int listId) {
        taskDao.markAsDone(taskId, listId);
        return "redirect:/lists";
    }

    @RequestMapping(value = "/delete-task", method = RequestMethod.POST)
    public String deleteTask(@RequestParam(name = "taskId") int taskId, @RequestParam(name = "listId") int listId) {
        taskDao.deleteTask(taskId, listId);
        return "redirect:/lists";
    }

    private void prepareModelMap(ModelMap map, List<TaskList> lists) {
        map.addAttribute("taskLists", lists);
        map.addAttribute("taskList", new TaskList());
        map.addAttribute("task", new Task());
    }
}