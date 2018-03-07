import {Component, OnInit} from '@angular/core';
import {UserModel} from "../UserModel";
import {AuthService} from "../login/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['../login/login.component.css']
})
export class RegistrationComponent implements OnInit {
  user: UserModel = new UserModel();
  userAlert;emailAlert; successAlert: boolean =  false;

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

  onRegister(user: UserModel): void{
      this.auth.register(user).then((user) => {
        if(user.json().username == '') {
          this.userAlert = true;
        }else this.userAlert = false;
        if (user.json().email == '') {
          this.emailAlert = true;
        }else this.emailAlert = false;
        if (user.json().id != null) {
          this.successAlert = true;
          sessionStorage.setItem('status', 'true');
          sessionStorage.setItem('id', user.json().id);
          this.router.navigateByUrl('/dashboard');
        }
        console.log(user.json());
      })
    }
}
