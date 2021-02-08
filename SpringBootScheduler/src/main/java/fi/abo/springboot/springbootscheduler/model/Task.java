package fi.abo.springboot.springbootscheduler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {

    private long id;
    private String name;
    private String recurrency;
    private String code;
 
    public Task() {
  
    }
 
    public Task(String name, String recurrency, String code) {
         this.name = name;
         this.recurrency = recurrency;
         this.code = code;
    }
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
        public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
 
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
 
    @Column(name = "recurrency", nullable = false)
    public String getRecurrency() {
        return recurrency;
    }
    public void setRecurrency(String recurrency) {
        this.recurrency = recurrency;
    }
 
    @Column(name = "code", nullable = false)
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", name=" + name + ", recurrency=" + recurrency + ", code=" + code
       + "]";
    }
 
}
