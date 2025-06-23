import { Component, OnInit } from '@angular/core';
import { Task } from '../task';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClientService } from '../service/http-client.service';

@Component({
  selector: 'app-update-task',
  templateUrl: './update-task.component.html',
  styleUrls: ['./update-task.component.css']
})
export class UpdateTaskComponent implements OnInit {

  id: number = 0;
  task: Task  = new Task();

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

  updateTask() {
    this.httpClientService.updateTask(this.id, this.task)
      .subscribe(data => {
        console.log(data);
        this.task = new Task();
        this.gotoList();
      }, error => console.log(error));
  }

  onSubmit() {
    this.updateTask();
  }

  gotoList() {
    this.router.navigate(['/tasks']);
  }
}
