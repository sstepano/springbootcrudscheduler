package fi.abo.springboot.springbootscheduler.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.abo.springboot.springbootscheduler.exception.ResourceNotFoundException;
import fi.abo.springboot.springbootscheduler.model.Task;
import fi.abo.springboot.springbootscheduler.repository.TaskRepository;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

@Configuration
@EnableScheduling
@RestController 
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class TaskController {
    @Value("${thread.pool.size}")
    private int POOL_SIZE; 	

    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    TaskScheduler taskScheduler;
    
    Map<Long, ScheduledFuture<?>> scheduledFutures = new HashMap<Long, ScheduledFuture<?>>();  
    
    @Bean
    TaskScheduler threadPoolTaskScheduler() {  
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        //scheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
        scheduler.setPoolSize(POOL_SIZE);
        scheduler.setThreadNamePrefix("My-Scheduler-");
        scheduler.initialize();
        return scheduler;
    }    
    
    private final GroovyClassLoader loader = new GroovyClassLoader();
    GroovyShell shell = new GroovyShell();
    
    private void addWithGroovyShellRun(String str) {
    	Script script = shell.parse(str);
        script.run();
    }

    private Runnable getTaskCode(String code) {
    	Runnable taskCode = () -> addWithGroovyShellRun(code); 
		return taskCode;
    }
    
    public void MyJointCompilationApp() {
        // ...
        shell = new GroovyShell(loader, new Binding());
        // ...
    }    
  
    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable(value = "id") Long taskId)
        throws ResourceNotFoundException {
        Task task = taskRepository.findById(taskId)
          .orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + taskId));
        return ResponseEntity.ok().body(task);
    }
    
    @PostMapping("/tasks")
    public Task createTask(@Valid @RequestBody Task task) {
    	//GroovyClassLoader gcl = new GroovyClassLoader();
    	ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(getTaskCode(task.getCode()), new CronTrigger(task.getRecurrency()));
    	Task createdTask = taskRepository.save(task);
    	scheduledFutures.put(createdTask.getId(), scheduledFuture);  
        return createdTask;
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable(value = "id") Long taskId,
         @Valid @RequestBody Task taskDetails) throws ResourceNotFoundException {
        Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + taskId));     
     
       	ScheduledFuture<?> scheduledFuture = scheduledFutures.get(taskId);
        scheduledFuture.cancel(true);
        scheduledFutures.remove(taskId);
    	scheduledFuture = taskScheduler.schedule(getTaskCode(taskDetails.getCode()), new CronTrigger(taskDetails.getRecurrency()));
    	scheduledFutures.put(taskId, scheduledFuture);        
       
        task.setName(taskDetails.getName());
        task.setRecurrency(taskDetails.getRecurrency());
        task.setCode(taskDetails.getCode());
        final Task updatedTask = taskRepository.save(task);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/tasks/{id}")
    public Map<String, Boolean> deleteTask(@PathVariable(value = "id") Long taskId)
         throws ResourceNotFoundException {
        Task task = taskRepository.findById(taskId)
       .orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + taskId));
        
       	ScheduledFuture<?> scheduledFuture = scheduledFutures.get(taskId);
        scheduledFuture.cancel(true);
        scheduledFutures.remove(taskId);        

        taskRepository.delete(task);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
