import { Task } from '../task';
import { Component, OnInit, Input } from '@angular/core';
import { HttpClientService } from '../service/http-client.service';
import { TaskListComponent } from '../task-list/task-list.component';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-task-details',
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.css']
})
export class TaskDetailsComponent implements OnInit {

  id: number = 0;
  task: Task = new Task();

  constructor(private route: ActivatedRoute, private router: Router,
    private httpClientService: HttpClientService) { }

  ngOnInit() {
    this.task = new Task();

    this.id = this.route.snapshot.params['id'];

    this.httpClientService.getTask(this.id)
      .subscribe(data => {
        console.log(data)
        this.task = data;
      }, error => console.log(error));
  }

  list() {
    this.router.navigate(['tasks']);
  }
}
