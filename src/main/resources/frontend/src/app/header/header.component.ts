import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLogged: boolean = false;
  constructor(private router: Router) { }

  ngOnInit() {
    const status = sessionStorage.getItem('status');
    this.isLogged = new Boolean(status).valueOf();
    console.log(this.isLogged);
  }
  onLogout(): void{
    sessionStorage.clear();
    this.router.navigateByUrl('');
  }
}
