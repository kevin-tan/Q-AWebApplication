import {TestBed, inject, getTestBed} from '@angular/core/testing';

import { UserProfileService } from '../app/user-profile/user-profile.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {User} from "../app/user-profile/user";
import {isNumber} from "util";
import {QuestionsService} from "../app/questions/questions.service";

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

  it('should be created', inject([UserProfileService], (userService: UserProfileService) => {
    expect(userService).toBeTruthy();
  }));
  it('should have method login()',
    inject([UserProfileService], (userService: UserProfileService) => {
      expect(userService.login)
    }));
  it('should have method register()',
    inject([UserProfileService], (userService: UserProfileService) => {
      expect(userService.register)
    }));
  it('should have method getUserByID()',
    inject([UserProfileService], (userService: UserProfileService) => {
      expect(userService.getUserByID)
    }));
  it('should have method getSecurityQuestion()',
    inject([UserProfileService], (userService: UserProfileService) => {
      expect(userService.getSecurityQuestion)
    }));
  it('should have method resetPassword()',
    inject([UserProfileService], (userService: UserProfileService) => {
      expect(userService.resetPassword)
    }));


  describe('#login',() => {
    it('should return an Observable<User> via POST',()=>{
      let username = 'user1';
      let password = 'Pass';
      const mockUser: User = {username, password} as User;

      userService.login(mockUser).subscribe(res =>{
        expect(res).toBe(mockUser);
      });
      const req = httpMock.expectOne(userService.baseURL+'login');
      expect(req.request.method).toBe('POST');
      req.flush(mockUser);
    });
  });

  describe('#register',() => {
    it('should return an Observable<User> via POST',()=>{
      let firstName = 'John';
      let lastName = 'Doe';
      let email = 'JohnDoe@gmail.com';
      let username = 'john';
      let password = 'pass';
      let securityQuestion = 'where was I born';
      let securityAnswer = 'Montreal';
      const mockUser: User = {firstName, lastName, email, username, password, securityQuestion, securityAnswer} as User;

      userService.register(mockUser).subscribe(res =>{
        expect(res).toBe(mockUser);
      });
      const req = httpMock.expectOne(userService.baseURL+'register');
      expect(req.request.method).toBe('POST');
      req.flush(mockUser);
    })
  });

  describe('#getUserByID',() => {
    it('should return an Observable<User> via GET',()=>{
      let userID = 1;

      let id = 1;
      let firstName = 'John';
      let lastName = 'Doe';
      let email = 'JohnDoe@gmail.com';
      let username = 'john';
      let dateJoined = 'Mar 14, 2018, 2:00:34 AM';
      let reputation = 0;

      const mockUser: User = { id, firstName, lastName, email, username, dateJoined, reputation} as User;

      userService.getUserByID(userID).subscribe(res =>{
        expect(res).toBe(mockUser);
      });
      const req = httpMock.expectOne(userService.baseURL+'users/'+userID);
      expect(req.request.method).toBe('GET');
      req.flush(mockUser);
    })
  });

  describe('#getSecurityQuestion',() => {
    it('should return an Observable<User> via GET',()=>{
      let email = 'JohnDoe@gmail.com';
      let securityQuestion = 'Where are you born'

      userService.getSecurityQuestion(email).subscribe(res =>{
        expect(res).toBe(securityQuestion);
      });
      const req = httpMock.expectOne(userService.userURL+email+'/securityQuestion');
      expect(req.request.method).toBe('GET');
    })
  });

  describe('#resetPassword',() => {
    it('should return an Observable<User> via PUT',()=>{
      let email = 'JohnDoe@gmail.com';
      let securityAnswer = 'Montreal';
      const mockUser: User = {email,securityAnswer} as User;

      userService.resetPassword(mockUser).subscribe(res =>{
        expect(res.id).toEqual(mockUser.id);
      });
      const req = httpMock.expectOne(userService.baseURL+'login/resetPassword');
      expect(req.request.method).toBe('PUT');
      req.flush(mockUser);
    })
  });

});

