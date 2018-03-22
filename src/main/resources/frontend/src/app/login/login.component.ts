import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {User} from "../user-profile/user";
import {UserProfileService} from "../user-profile/user-profile.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  usernameData ;passwordData: string;
  errorMsg = '';
  errorAlert: boolean;
  isLogged: boolean;

  constructor(private router: Router, private userProfileService: UserProfileService ) {}

  ngOnInit(){}

  onLogin(): void{
    let username = this.usernameData;
    let password = this.passwordData;
    const user: User = {username, password} as User;
    this.firstReq(user);
    this.fetchData(user);
    console.log(this.isLogged);

  }

  fetchData(user){
    this.userProfileService.login(user).subscribe(
      user => {
        sessionStorage.clear();
        if(user.id == null) {
          this.isLogged = false;
          sessionStorage.clear();
          this.errorAlert = true;
          this.errorMsg = 'Invalid Login, please try again';
        }
        else {
          sessionStorage.setItem('status', 'true');
          sessionStorage.setItem('id', user.id.toString());
          //This is needed!
          sessionStorage.setItem('username',user.username);
          this.router.navigateByUrl('/dashboard');
        }
        console.log(user);
      },
    );

  }

  firstReq(user){
    this.userProfileService.login(user).subscribe(user=>{
      if(user.id == null)
        this.isLogged = false;
      else
        this.isLogged = true;
    });
  }
}
