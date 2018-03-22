import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ForgotPassComponent } from '../app/forgot-pass/forgot-pass.component';
import {FormsModule} from "@angular/forms";
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {UserProfileService} from "../app/user-profile/user-profile.service";

describe('ForgotPassComponent', () => {
  let component: ForgotPassComponent;
  let fixture: ComponentFixture<ForgotPassComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ForgotPassComponent ],
      imports: [FormsModule, RouterTestingModule, HttpClientTestingModule],
      providers: [UserProfileService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ForgotPassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
