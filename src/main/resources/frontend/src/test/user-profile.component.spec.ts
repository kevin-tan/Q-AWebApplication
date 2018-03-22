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

describe('UserProfileComponent', () => {
  let component: UserProfileComponent;
  let fixture: ComponentFixture<UserProfileComponent>;
  let element: HTMLElement;

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
  });

  it('should create', async() => {
    expect(component).toBeTruthy();
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
