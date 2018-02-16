import {Component, OnInit} from '@angular/core';
import {DashboardService} from "./dashboard.service";
import { Router } from '@angular/router'

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  public questions = [];

  constructor(private _dashboardService: DashboardService, private router: Router) { }

  ngOnInit() {
    this._dashboardService.getDashboard().subscribe(data => this.questions = data);
  }

  OnSelect(question){
    this.router.navigate(['/question', question.id]);
  }

}
