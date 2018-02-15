import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-greeting',
  templateUrl: './greeting.component.html',
  styleUrls: ['../home/home.component.css']
})
export class GreetingComponent implements OnInit {
  FirstName: string = sessionStorage.getItem('firstname');
  LastName: string = sessionStorage.getItem('lastname');

  constructor() { }

  ngOnInit() {
  }

}
