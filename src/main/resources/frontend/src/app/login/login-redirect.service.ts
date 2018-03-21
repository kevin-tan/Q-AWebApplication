import {Injectable} from '@angular/core';
import {CanActivate, Router} from "@angular/router";

@Injectable()
export class LoginRedirectService implements CanActivate{

  constructor(private router: Router) { }
  canActivate(): boolean{
    if(sessionStorage.getItem('status') == 'true'){
      this.router.navigateByUrl('/dashboard');
      return false;
    }else{
      return true;
    }
  }
}
