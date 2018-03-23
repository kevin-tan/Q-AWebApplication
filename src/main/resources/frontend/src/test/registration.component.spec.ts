import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RegistrationComponent} from '../app/registration/registration.component';
import {HeaderComponent} from "../app/header/header.component";
import {FooterComponent} from "../app/footer/footer.component";
import {FormsModule} from "@angular/forms";
import {UserProfileService} from "../app/user-profile/user-profile.service";
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {DebugElement} from "@angular/core";
import {By} from "@angular/platform-browser";

describe('RegistrationComponent', () => {
  let component: RegistrationComponent;
  let fixture: ComponentFixture<RegistrationComponent>;
  let debugFirstName: DebugElement;
  let debugLastName: DebugElement;
  let debugEmail: DebugElement;
  let debugUsername: DebugElement;
  let debugPassword: DebugElement;
  let debugConfirmPassword: DebugElement;
  let debugSecurityQuestion: DebugElement;
  let debugSecurityAnswer: DebugElement;
  let debugSignUpButton: DebugElement;
  let htmlFirstName: HTMLElement;
  let htmlLastName: HTMLElement;
  let htmlEmail: HTMLElement;
  let htmlUsername: HTMLElement;
  let htmlPassword: HTMLElement;
  let htmlConfirmPassword: HTMLElement;
  let htmlSecurityQuestion: HTMLElement;
  let htmlSecurityAnswer: HTMLElement;
  let htmlSignUpButton: HTMLElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegistrationComponent, HeaderComponent, FooterComponent ],
      imports: [FormsModule, RouterTestingModule, HttpClientTestingModule],
      providers: [UserProfileService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    debugFirstName = fixture.debugElement.query(By.css('input[name=firstName]'));
    htmlFirstName = debugFirstName.nativeElement;

    debugLastName = fixture.debugElement.query(By.css('input[name=lastName]'));
    htmlLastName = debugLastName.nativeElement;

    debugEmail = fixture.debugElement.query(By.css('input[name=email]'));
    htmlEmail = debugEmail.nativeElement;

    debugUsername = fixture.debugElement.query(By.css('input[name=username]'));
    htmlUsername = debugUsername.nativeElement;

    debugPassword = fixture.debugElement.query(By.css('input[name=password]'));
    htmlPassword = debugPassword.nativeElement;

    debugConfirmPassword = fixture.debugElement.query(By.css('input[name=passwordConfirm]'));
    htmlConfirmPassword = debugConfirmPassword.nativeElement;

    debugSecurityQuestion = fixture.debugElement.query(By.css('input[name=securityQuestion]'));
    htmlSecurityQuestion = debugSecurityQuestion.nativeElement;

    debugSecurityAnswer = fixture.debugElement.query(By.css('input[name=securityAnswer]'));
    htmlSecurityAnswer = debugSecurityAnswer.nativeElement;

    debugSignUpButton = fixture.debugElement.query(By.css('.submitButton'));
    htmlSignUpButton = debugSecurityAnswer.nativeElement;

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should display first name box', () => {
    expect(htmlFirstName).toBeTruthy();
  });
  it('should display last name box', () => {
    expect(htmlLastName).toBeTruthy();
  });
  it('should display email box', () => {
    expect(htmlEmail).toBeTruthy();
  });
  it('should display username box', () => {
    expect(htmlUsername).toBeTruthy();
  });
  it('should display password box', () => {
    expect(htmlPassword).toBeTruthy();
  });
  it('should display confirm password box', () => {
    expect(htmlConfirmPassword).toBeTruthy();
  });
  it('should display security question box', () => {
    expect(htmlSecurityQuestion).toBeTruthy();
  });
  it('should display security answer box', () => {
    expect(htmlSecurityAnswer).toBeTruthy();
  });
  it('should display sign up button', () => {
    expect(htmlSignUpButton).toBeTruthy();
  });

  describe('#onRegister()', () => {
    it('should display an error if username/email are taken', () => {
    });

    it('should redirect to dashboard when user successfully registered', () => {
    });
  });
});
