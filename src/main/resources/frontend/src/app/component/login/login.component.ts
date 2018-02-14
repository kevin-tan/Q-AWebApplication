import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {UserModel} from "../../model/UserModel";
import { Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: UserModel = new UserModel();

  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit(){}

  onLogin(): void{
    this.auth.login(this.user).then((user) =>{
      sessionStorage.clear();
      if(user.json().id == null) {
        sessionStorage.setItem('status', 'false');
      }
      else {
        sessionStorage.setItem('status', 'true');
        sessionStorage.setItem('id', user.json().id);
        sessionStorage.setItem('firstname', user.json().firstName);
        sessionStorage.setItem('lastname', user.json().lastName);
        //sessionStorage.setItem('user', JSON.stringify(user));
        this.router.navigateByUrl('/status');
      }
      console.log(user.json());
    })
  }
}
