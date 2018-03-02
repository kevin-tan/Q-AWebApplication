import {Component, OnInit} from '@angular/core';
import {AuthService} from "./auth.service";
import {UserModel} from "../UserModel";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: UserModel = new UserModel();
  public errorMsg = '';
  errorAlert: boolean;

  constructor(private auth: AuthService, private router: Router) {
  }

  ngOnInit(){}

  onLogin(): void{
    this.auth.login(this.user)
      .catch((err)=>{
        this.errorAlert = true;
        this.errorMsg = 'Username and password required';
      })
      .then((user) =>{
      sessionStorage.clear();
      if(user.json().id == null) {
        sessionStorage.setItem('status', 'false');
        this.errorAlert = true;
        this.errorMsg = 'Invalid Login, please try again';
      }
      else {
        sessionStorage.setItem('status', 'true');
        sessionStorage.setItem('id', user.json().id);
        sessionStorage.setItem('user', user.json().username);
        sessionStorage.setItem('firstname', user.json().firstName);
        sessionStorage.setItem('lastname', user.json().lastName);
        sessionStorage.setItem('dateJoined', user.json().dateJoined);
        sessionStorage.setItem('reputation',user.json().reputation);
        sessionStorage.setItem('username',user.json().username);
        //sessionStorage.setItem('user', JSON.stringify(user));
        this.router.navigateByUrl('/dashboard');
      }
      console.log(user.json());
    })
  }
}
