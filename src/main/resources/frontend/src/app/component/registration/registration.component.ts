import { Component, OnInit } from '@angular/core';
import {UserModel} from "../../model/UserModel";
import {AuthService} from "../../service/auth.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['../login/login.component.css']
})
export class RegistrationComponent implements OnInit {
  user: UserModel = new UserModel();

  constructor(private auth: AuthService) { }

  ngOnInit() {}

  onRegister(): void{
    this.auth.register(this.user).then((user) =>{
      console.log(user.json());
    })
      .catch((err) =>{
        console.log(err);
      });
  }


}
