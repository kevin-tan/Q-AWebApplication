import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLogged: boolean;
  id; username: string;
  constructor(private router: Router) { }

  ngOnInit() {
    this.isLogged = new Boolean(sessionStorage.getItem('status')).valueOf();
    this.id = sessionStorage.getItem('id');
    this.username = sessionStorage.getItem('username');
  }
  onLogout(): void{
    sessionStorage.clear();
    this.isLogged = false;
    this.router.navigateByUrl('/dashboard');
  }
}
