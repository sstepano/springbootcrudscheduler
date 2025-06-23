import { HttpClientService } from '../service/http-client.service';
import { Task } from '../task';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-task',
  templateUrl: './create-task.component.html',
  styleUrls: ['./create-task.component.css']
})
export class CreateTaskComponent implements OnInit {

  task: Task = new Task();
  submitted = false;

  constructor(private httpClientService: HttpClientService,
    private router: Router) { }

  ngOnInit() {
  }

  newTask(): void {
    this.submitted = false;
    this.task = new Task();
  }

  save() {
    this.httpClientService
      .createTask(this.task).subscribe(data => {
        console.log(data)
        this.task = new Task();
        this.gotoList();
      },
        error => console.log(error));
  }

  onSubmit() {
    this.submitted = true;
    this.save();
  }

  gotoList() {
    this.router.navigate(['/tasks']);
  }
}
