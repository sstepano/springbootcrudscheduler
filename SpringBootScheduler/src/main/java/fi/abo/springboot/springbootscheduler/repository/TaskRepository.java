package fi.abo.springboot.springbootscheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.abo.springboot.springbootscheduler.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

}
