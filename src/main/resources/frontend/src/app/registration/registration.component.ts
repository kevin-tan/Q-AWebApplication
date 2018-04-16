import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserProfileService} from "../user-profile/user-profile.service";
import {User} from "../user-profile/user";
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['../login/login.component.css']
})
export class RegistrationComponent implements OnInit {
  usernameData: string;
  firstNameData: string;
  lastNameData: string;
  emailData: string;
  passwordData: string;
  securityQuestionData: string;
  securityAnswerData: string;
  userAlert;emailAlert; successAlert: boolean =  false;
  response: Observable<User>;

  constructor(private router: Router, private  userService: UserProfileService) {}

  ngOnInit() {}

  onRegister(): void{
    let firstName = this.firstNameData;
    let lastName = this.lastNameData;
    let email = this.emailData;
    let username = this.usernameData;
    let password = this.passwordData;
    let securityQuestion = this.securityQuestionData;
    let securityAnswer = this.securityAnswerData;
    const user: User = {firstName, lastName, email, username, password, securityQuestion, securityAnswer} as User;

    this.userService.register(user).subscribe(user =>{
      if(user.username == '') {
        this.userAlert = true;
      }else this.userAlert = false;
      if (user.email == '') {
        this.emailAlert = true;
      }else this.emailAlert = false;
      if (user.id != null) {
        this.successAlert = true;
        sessionStorage.setItem('status', 'true');
        sessionStorage.setItem('id', user.id.toString());
        sessionStorage.setItem('username', user.username);
        this.router.navigateByUrl('/dashboard');
      }
    });
    }

}
