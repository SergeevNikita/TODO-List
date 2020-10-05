package team.smartworld.academy.todolist.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Greeting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String content;

    public Greeting() {
    }

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

}
