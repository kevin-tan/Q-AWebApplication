import {Injectable} from '@angular/core';
import {CanActivate, Router} from "@angular/router";

@Injectable()
export class VerifyAuthenticationService implements CanActivate{

  constructor(private router: Router) { }
  canActivate(): boolean {
    if (sessionStorage.getItem('status') == 'true')
      return true;
    else {
      this.router.navigateByUrl('/login');
      return false;
    }
  }
}
