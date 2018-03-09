import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {UserProfileComponent} from "../user-profile/user-profile.component";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  userComponent: UserProfileComponent;
  isLogged: boolean = false;
  id: string;
  constructor(private router: Router ) { }

  ngOnInit() {
    this.isLogged = new Boolean(sessionStorage.getItem('status')).valueOf();
    this.id = sessionStorage.getItem('id');
  }
  onLogout(): void{
    sessionStorage.clear();
    this.isLogged = false;
    this.router.navigateByUrl('/dashboard');
  }
  navigateProfile():void{
    location.reload();
    this.router.navigate(['profile', this.id]);

  }
}
