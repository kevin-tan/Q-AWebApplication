import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RegistrationComponent} from '../app/registration/registration.component';
import {HeaderComponent} from "../app/header/header.component";
import {FooterComponent} from "../app/footer/footer.component";
import {FormsModule} from "@angular/forms";
import {UserProfileService} from "../app/user-profile/user-profile.service";
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('RegistrationComponent', () => {
  let component: RegistrationComponent;
  let fixture: ComponentFixture<RegistrationComponent>;

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
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
