import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-forgot-pass',
  templateUrl: './forgot-pass.component.html',
  styleUrls: ['../login/login.component.css']
})
export class ForgotPassComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }
  submitEmail(){
    this.router.navigateByUrl('/loginQuestion');
  }
}
