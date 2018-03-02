import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-forgot-pass',
  templateUrl: './forgot-pass.component.html',
  styleUrls: ['../login/login.component.css']
})
export class ForgotPassComponent implements OnInit {
  emailValid: boolean = false;
  answerValid: boolean = false;
  reset: boolean = false;

  constructor(private router: Router) { }

  ngOnInit() {
  }
  submitValues(){

  }
  submitEmail(){
    this.emailValid = true;
    this.answerValid = true;
  }
  submitAnswer(){
    this.answerValid = false;
    this.reset= true;
  }
}
