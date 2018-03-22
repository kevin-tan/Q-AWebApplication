import {inject, TestBed} from '@angular/core/testing';

import {LoginRedirectService} from '../app/login/login-redirect.service';
import {RouterTestingModule} from "@angular/router/testing";
import {UserProfileService} from "../app/user-profile/user-profile.service";
import {HttpClientModule} from "@angular/common/http";


describe('LoginRedirectService should', () => {
  let redirectService: LoginRedirectService;
  let userService: UserProfileService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LoginRedirectService, UserProfileService],
      imports:[RouterTestingModule, HttpClientModule]
    });

    redirectService = TestBed.get(LoginRedirectService);
    userService = TestBed.get(UserProfileService);
  });

  it('create LoginRedirect Service', inject([LoginRedirectService], (service: LoginRedirectService) => {
    expect(service).toBeTruthy();
  }));
  it('NOT be able to go to login/registration page if user logged in', () => {
    spyOn(redirectService, 'getStatus').and.returnValue(true);
    expect(redirectService.canActivate()).toBe(false);
  });
  it('be able to go to login/registration page if user is NOT logged in', () => {
    userService.isLoggedIn = false;
    expect(redirectService.canActivate()).toBe(true);
  });
});
