package hu.javaoktatas.peldak.todolist.comparators;

import java.util.Comparator;

import hu.javaoktatas.peldak.todolist.model.ToDoItem;

public class ComparatorDatNov implements Comparator<ToDoItem> {
    @Override
    public int compare(ToDoItem t0, ToDoItem t1) {
        if (t0.getEv() == t1.getEv()) {
            if (t0.getHonap() == t1.getHonap()) {
                if (t0.getNap() == t1.getNap()) {
                    return -1;
                } else {
                    if (t0.getNap() < t1.getNap()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            } else {
                if (t0.getHonap() < t1.getHonap()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        } else {
            if (t0.getEv() < t1.getEv()) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
