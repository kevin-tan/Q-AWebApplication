import { Component, OnInit } from '@angular/core';
import { DashboardService } from '../dashboard.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  public dashboards = [];

  constructor(private _dashboardService: DashboardService) { }

  ngOnInit() {
    this._dashboardService.getDashboard().subscribe(data => this.dashboards = data);
  }
  
}
