import { TaskDetailsComponent } from '../task-details/task-details.component';
import { Observable } from "rxjs";
import { HttpClientService } from "../service/http-client.service";
import { Task } from "../task";
import { Component, OnInit } from "@angular/core";
import { Router } from '@angular/router';

@Component({
  selector: "app-task-list",
  templateUrl: "./task-list.component.html",
  styleUrls: ["./task-list.component.css"]
})
export class TaskListComponent implements OnInit {
  tasks: Observable<Task[]> | undefined;

  constructor(private httpClientService: HttpClientService,
    private router: Router) { }

  ngOnInit() {
    this.reloadData();
  }

  reloadData() {
    this.tasks = this.httpClientService.getTaskList();
  }

  deleteTask(id: number) {
    this.httpClientService.deleteTask(id)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
        },
        error => console.log(error));
  }

  taskDetails(id: number) {
    this.router.navigate(['details', id]);
  }

  updateTask(id: number) {
    this.router.navigate(['update', id]);
  }
}
