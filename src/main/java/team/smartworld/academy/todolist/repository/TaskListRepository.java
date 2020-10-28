package team.smartworld.academy.todolist.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import team.smartworld.academy.todolist.entity.TaskList;

import java.util.UUID;

/**
 * Интерфейс репозитория для работы с БД класа TaskList
 */
@Repository
@Transactional
public interface TaskListRepository extends PagingAndSortingRepository<TaskList, UUID>, JpaSpecificationExecutor<TaskList> {

}
