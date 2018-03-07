import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {UserProfileService} from "../user-profile/user-profile.service";

@Component({
  selector: 'app-forgot-pass',
  templateUrl: './forgot-pass.component.html',
  styleUrls: ['../login/login.component.css']
})

export class ForgotPassComponent implements OnInit {
  emailValid: boolean = false;
  answerValid: boolean = false;
  resetPass: boolean = false;
  emailErr; dirty: boolean;
  email: string = 'BBob@gmail.com';
  securityQuestion: string;
  emailData; answerData: string;


  constructor(private router: Router,private userProfileService: UserProfileService) { }

  ngOnInit() {
    this.dirty = false;
  }

  submitValues(): void{
    this.submitEmail()
  }

  submitEmail(){
    if(this.emailErr == false){
      this.emailValid = true;
      this.answerValid = true;
      this.dirty = false;
    }
    this.userProfileService.getSecurityQuestion(this.emailData).subscribe(
      securityQuestion => {
        this.securityQuestion = securityQuestion;
        this.emailErr = false;
        this.dirty = true;},
      error => this.emailErr = true );
  }

  submitAnswer(){
    console.log(this.answerData);
    // this.answerValid = false;
    // this.resetPass= true;
  }
}
