import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {UserProfileService} from "../user-profile/user-profile.service";
import {User} from "../user-profile/user";

@Component({
  selector: 'app-forgot-pass',
  templateUrl: './forgot-pass.component.html',
  styleUrls: ['../login/login.component.css']
})

export class ForgotPassComponent implements OnInit {
  emailValid; answerValid; resetValid: boolean = false;
  emailErr; answerErr: boolean;
  securityQuestion; emailData; answerData; passData; passConfirmData: string;
  userResp; userAccess: User;

  constructor(private router: Router,private userProfileService: UserProfileService) { }

  ngOnInit() {}

  submitEmail(){
    this.userProfileService.getSecurityQuestion(this.emailData).subscribe(
      securityQuestion => {
        this.securityQuestion = securityQuestion;
        this.emailErr = false;
        if(!this.emailErr){
          this.emailValid = true;
          this.answerValid = true;
        }}, error => this.emailErr = true );
  }

  submitAnswer(){
    let email = this.emailData;
    let securityAnswer = this.answerData;
    const user: User = {email,securityAnswer} as User;

    this.userProfileService.resetPassword(user).subscribe(
      res => {this.userResp = res;
        if(this.userResp.id == null){
          this.answerErr = true;
        }else {
          this.answerErr = false;
          this.answerValid = false;
          this.resetValid= true;
          this.userAccess = user;}
      });
  }

  resetPassword(){
    this.userAccess.password  = this.passData;
    this.userProfileService.resetPassword(this.userAccess).subscribe();
    this.router.navigateByUrl('/login');

  }

}
