import {inject, TestBed} from '@angular/core/testing';

import {VerifyAuthenticationService} from './verify-authentication.service';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";

describe('VerifyAuthenticationService', () => {

  let verifyAuth: VerifyAuthenticationService;
  let next: ActivatedRouteSnapshot;
  let state: RouterStateSnapshot;
  let router: Router;
  let isLogged : boolean;


  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [VerifyAuthenticationService, Router]
    });
    verifyAuth = new VerifyAuthenticationService(router);
  });
  beforeEach(()=>{
    isLogged = new Boolean(sessionStorage.getItem('status')).valueOf()
    verifyAuth = TestBed.get(VerifyAuthenticationService);
  });

  it('be able to hit route when user is logged in', ()=>{
    isLogged = true;
    expect(verifyAuth.canActivate()).toBe(true);
  });
  // it('should be created', inject([VerifyAuthenticationService], (service: VerifyAuthenticationService) => {
  //   expect(service).toBeTruthy();
  // }));
});
