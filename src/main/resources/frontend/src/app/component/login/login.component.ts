import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {UserModel} from "../../model/UserModel";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: UserModel = new UserModel();

  constructor(private auth: AuthService) { }

  ngOnInit(){}
  onLogin(): void{
    this.auth.login(this.user).then((user) =>{
      console.log(user.json());
    })
      .catch((err) =>{
        console.log(err);
      });
  }
}
