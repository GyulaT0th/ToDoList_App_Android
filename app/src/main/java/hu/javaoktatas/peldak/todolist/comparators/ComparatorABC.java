package hu.javaoktatas.peldak.todolist.comparators;

import java.util.Comparator;

import hu.javaoktatas.peldak.todolist.model.ToDoItem;

public class ComparatorABC implements Comparator<ToDoItem>{

    @Override
    public int compare(ToDoItem t0, ToDoItem t1) {
        return t0.getCim().compareToIgnoreCase(t1.getCim());
    }
}
