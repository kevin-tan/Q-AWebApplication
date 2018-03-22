import {inject, TestBed} from '@angular/core/testing';

import {LoginRedirectService} from '../app/login/login-redirect.service';
import {RouterTestingModule} from "@angular/router/testing";

describe('LoginRedirectService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LoginRedirectService],
      imports:[RouterTestingModule]
    });
  });

  it('should be created', inject([LoginRedirectService], (service: LoginRedirectService) => {
    expect(service).toBeTruthy();
  }));
});
