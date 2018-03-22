import {Injectable} from '@angular/core';
import {CanActivate, Router} from "@angular/router";
import {UserProfileService} from "../user-profile/user-profile.service";

@Injectable()
export class LoginRedirectService implements CanActivate{

  constructor(private router: Router, private userService: UserProfileService) { }
  canActivate(): boolean{
    if(this.userService.isLoggedIn){
      this.router.navigate(['dashboard']);
      return false;
    }else{
      return true;
    }
  }
}
