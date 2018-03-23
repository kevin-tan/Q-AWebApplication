import {Injectable} from '@angular/core';
import {CanActivate, Router} from "@angular/router";

@Injectable()
export class LoginRedirectService implements CanActivate{

  constructor(private router: Router) {}
  getStatus():boolean{
    return new Boolean(sessionStorage.getItem('status')).valueOf();
  }
  canActivate(): boolean{
    if(this.getStatus()== true){
      this.router.navigate(['dashboard']);
      return false;
    }else{
      return true;
    }
  }
}
