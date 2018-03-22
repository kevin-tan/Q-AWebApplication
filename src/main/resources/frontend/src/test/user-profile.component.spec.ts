import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserProfileComponent } from '../app/user-profile/user-profile.component';
import {RouterTestingModule} from "@angular/router/testing";
import {HeaderComponent} from "../app/header/header.component";
import {FooterComponent} from "../app/footer/footer.component";
import {FormsModule} from "@angular/forms";
import {UserProfileService} from "../app/user-profile/user-profile.service";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {QuestionsService} from "../app/questions/questions.service";

describe('UserProfileComponent', () => {
  let component: UserProfileComponent;
  let fixture: ComponentFixture<UserProfileComponent>;

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
});
