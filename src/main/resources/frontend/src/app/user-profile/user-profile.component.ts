import { Component, OnInit } from '@angular/core';
import {UserModel} from "../UserModel";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  Username: string = sessionStorage.getItem('username');
  FirstName: string = sessionStorage.getItem('firstname');
  LastName: string = sessionStorage.getItem('lastname');
  datejoined: string = sessionStorage.getItem('dateJoined');
  Reputation: string = sessionStorage.getItem('reputation');
  FirstL = this.FirstName.charAt(0).toUpperCase();
  LastL = this.LastName.charAt(0).toUpperCase();
  constructor() { }

  ngOnInit() {

  }



}
