import {Component, OnInit} from '@angular/core';
import {UserModel} from "../UserModel";
import {AuthService} from "../login/auth.service";
import {Router} from "@angular/router";
import {register} from "ts-node";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['../login/login.component.css']
})
export class RegistrationComponent implements OnInit {
  user: UserModel = new UserModel();
  errorAlert =  false;
  successAlert = false;

  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit() {
    this.user = {
      id: null,
      firstName: '',
      lastName: '',
      username: '',
      password: '',
      confirmPassword: '',
      email: '',
      securityQuestion: '',
      securityAnswer: ''
    };
  }

  onRegister(user: UserModel, isValid: boolean): void{
    // if(this.user.username != '' && this.user.firstName != '' && this.user.lastName != '' && this.user.email != '' && this.user.password != ''){
      this.auth.register(user).then((user) => {
        if (this.user.username == '') {
          this.errorAlert = false;
        }
        if (user.json().id == null) {
          this.errorAlert = true;
        }
        if (user.json().id != null) {
          this.successAlert = true;
          sessionStorage.setItem('status', 'true');
          sessionStorage.setItem('id', user.json().id);
        }
        console.log(user.json());
      })
    }
    // }
}
