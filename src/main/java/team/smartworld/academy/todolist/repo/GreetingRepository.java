package team.smartworld.academy.todolist.repo;

import org.springframework.data.repository.CrudRepository;
import team.smartworld.academy.todolist.models.Greeting;

public interface GreetingRepository extends CrudRepository<Greeting, Long> {

}
