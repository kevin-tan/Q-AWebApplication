import {inject, TestBed} from '@angular/core/testing';

import {VerifyAuthenticationService} from '../app/login/verify-authentication.service';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {RouterTestingModule} from "@angular/router/testing";

describe('VerifyAuthenticationService', () => {

  let verifyAuth: VerifyAuthenticationService;
  let next: ActivatedRouteSnapshot;
  let state: RouterStateSnapshot;
  let router: Router;
  let isLogged : boolean;


  beforeEach(async() => {
    TestBed.configureTestingModule({
      providers: [VerifyAuthenticationService],
      imports:[RouterTestingModule]
    });
    verifyAuth = new VerifyAuthenticationService(router);
  });
  beforeEach(()=>{
    isLogged = new Boolean(sessionStorage.getItem('status')).valueOf()
    verifyAuth = TestBed.get(VerifyAuthenticationService);
  });

  // it('be able to hit route when user is logged in', ()=>{
  //   isLogged = true;
  //   expect(verifyAuth.canActivate()).toBe(true);
  // });
  // it('should be created', inject([VerifyAuthenticationService], (service: VerifyAuthenticationService) => {
  //   expect(service).toBeTruthy();
  // }));
});
