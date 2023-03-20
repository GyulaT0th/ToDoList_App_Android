package hu.javaoktatas.peldak.todolist.model;

import java.util.List;

public interface IModel {
    List<ToDoItem> getAllToDoItem();
    ToDoItem getToDoItem(int id);
    void saveOrUpdateToDoItem(ToDoItem tdi);
    void removeAllToDoItem();
    void removeToDoItem(ToDoItem tdi);
}
