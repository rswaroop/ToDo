package swaroop.todo.db;

import com.turbomanage.storm.api.Entity;

@Entity
public class ToDoData {

    private long id;
    private String todoItem;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTodoItem() {
        return todoItem;
    }

    public void setTodoItem(String item) {
        this.todoItem = item;
    }
}
