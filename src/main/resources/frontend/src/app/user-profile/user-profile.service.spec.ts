import {TestBed, inject, getTestBed} from '@angular/core/testing';

import { UserProfileService } from './user-profile.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {User} from "./user";
import {isNumber} from "util";

describe('UserProfileService', () => {
  let injector: TestBed;
  let userService: UserProfileService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule],
      providers: [UserProfileService]
    });

    injector = getTestBed();
    userService = injector.get(UserProfileService);
    httpMock = injector.get(HttpTestingController);
  });

  it('should be created', inject([UserProfileService], (service: UserProfileService) => {
    expect(service).toBeTruthy();
  }));

  describe('#loginService',() => {
    it('should return an Observable<User>',()=>{
      let username = 'user1';
      let password = 'Pass';
      const dummyUser: User = {username, password} as User;

      userService.login(dummyUser).subscribe(res =>{
        expect(res.username).toBe(username);
      });
      const req = httpMock.expectOne(userService.baseURL+'login');
      expect(req.request.method).toBe('POST');
      req.flush(dummyUser);
    });
  });

  describe('#registerService',() => {
    it('should return an Observable<User>',()=>{

    })
  });
});

