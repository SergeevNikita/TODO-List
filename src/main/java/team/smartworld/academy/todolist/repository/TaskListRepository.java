package team.smartworld.academy.todolist.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import team.smartworld.academy.todolist.entity.TaskList;

/**
 *
 */
@Repository
@Transactional
public interface TaskListRepository extends CrudRepository<TaskList, Long> {

}
