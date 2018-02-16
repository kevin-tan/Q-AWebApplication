import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.css']
})
export class StatusComponent implements OnInit {

  isLoggedIn: boolean = false;
  FirstName: string = sessionStorage.getItem('firstname');
  LastName: string = sessionStorage.getItem('lastname');
  constructor() { }

  ngOnInit(): void {
    const status = sessionStorage.getItem('status');
    if(status == 'true') {
      this.isLoggedIn = true;
    }else{
      this.isLoggedIn = false;
    }
  }

}
