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

  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit() {}
  onRegister(): void{
    this.auth.register(this.user).then((user) =>{
      this.router.navigateByUrl('/login');
      console.log(user.json());
    })
    console.log(this.user);
  }
}
