import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserProfileComponent } from '../app/user-profile/user-profile.component';
import {RouterTestingModule} from "@angular/router/testing";
import {HeaderComponent} from "../app/header/header.component";
import {FooterComponent} from "../app/footer/footer.component";
import {FormsModule} from "@angular/forms";
import {UserProfileService} from "../app/user-profile/user-profile.service";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {QuestionsService} from "../app/questions/questions.service";
import {User} from "../app/user-profile/user";
import {DebugElement} from "@angular/core";
import {By} from "@angular/platform-browser";

describe('UserProfileComponent', () => {
  let component: UserProfileComponent;
  let fixture: ComponentFixture<UserProfileComponent>;

  let debugUsername: DebugElement;
  let htmlUsername: HTMLElement;


  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserProfileComponent, HeaderComponent, FooterComponent ],
      imports: [ RouterTestingModule, FormsModule, HttpClientTestingModule ],
      providers: [ UserProfileService, QuestionsService ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    debugUsername = fixture.debugElement.query(By.css('input[name=username]'));
    htmlUsername = debugUsername.nativeElement;
  });

  it('should create', async() => {
    expect(component).toBeTruthy();
  });
  it('should display username info', () => {
    expect(htmlPassword).toBeTruthy();
  });
  it('should display email info if user logged in', () => {
    expect(htmlPassword).toBeTruthy();
  });
  it('should display first name info', () => {
    expect(htmlPassword).toBeTruthy();
  });
  it('should display last name info', () => {
    expect(htmlPassword).toBeTruthy();
  });
  it('should display date joined info', () => {
    expect(htmlPassword).toBeTruthy();
  });
  it('should display reputation info', () => {
    expect(htmlPassword).toBeTruthy();
  });
  it('should display edit profile button if user logged in', () => {
    expect(htmlPassword).toBeTruthy();
  });


  describe('#registerService',() => {
    it('should return an Observable<User>',()=>{
      let firstName = 'John';
      let lastName = 'Doe';
      let email = 'JohnDoe@gmail.com';
      let username = 'john';
      let password = 'pass';
      let securityQuestion = 'where was I born';
      let securityAnswer = 'Montreal';
      const dummyUser: User = {firstName, lastName, email, username, password, securityQuestion, securityAnswer} as User;

    })
  });
});
