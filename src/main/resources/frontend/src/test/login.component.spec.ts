import {async, ComponentFixture, inject, TestBed} from '@angular/core/testing';

import {LoginComponent} from '../app/login/login.component';
import {HeaderComponent} from "../app/header/header.component";
import {FooterComponent} from "../app/footer/footer.component";
import {FormsModule} from "@angular/forms";
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {UserProfileService} from "../app/user-profile/user-profile.service";
import {User} from "../app/user-profile/user";
import {Observable} from "rxjs/Observable";
import {HttpEvent, HttpEventType} from "@angular/common/http";
import {parseHttpResponse} from "selenium-webdriver/http";

describe('LoginComponent ', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let userService: UserProfileService;
  let httpMock: HttpTestingController;

  let username = 'user1';
  let password = 'Pass';
  const dummyUser: User = {username, password} as User;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginComponent , HeaderComponent, FooterComponent ],
      imports: [ FormsModule, RouterTestingModule, HttpClientTestingModule],
      providers: [UserProfileService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    userService = TestBed.get(UserProfileService);
    httpMock = TestBed.get(HttpTestingController);
    fixture.detectChanges();
  });

  afterEach(()=>{
    sessionStorage.clear();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('onLogin should return an error if login credential are incorrect',
    (done)=>{

    const mockUser = {username: 'user1', password: 'pass'} as User;
    const mockResponse = {id: null} as User;

    userService.login(mockUser).subscribe(res => {
      expect(res).toEqual(mockResponse);
      done();
    });

    const loginRequest = httpMock.expectOne(userService.baseURL+'login');
    loginRequest.flush(mockResponse);

    expect(loginRequest).toBeDefined();
    expect(loginRequest.request.method).toBe('POST');
    });

  it('should redirect to dashboard if login is successful', () => {
    expect(component).toBeTruthy();
  });
  it('clicking login should submit a user', () => {
    expect(component).toBeTruthy();
  });

});
