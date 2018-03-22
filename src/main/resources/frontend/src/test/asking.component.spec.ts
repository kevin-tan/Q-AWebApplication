import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AskingComponent } from '../app/asking/asking.component';
import {HeaderComponent} from "../app/header/header.component";
import {FooterComponent} from "../app/footer/footer.component";
import {RouterTestingModule} from "@angular/router/testing";
import {AskingService} from "../app/asking/asking.service";
import {FormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('AskingComponent', () => {
  let component: AskingComponent;
  let fixture: ComponentFixture<AskingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AskingComponent, HeaderComponent, FooterComponent ],
      imports: [RouterTestingModule, FormsModule, HttpClientTestingModule],
      providers: [AskingService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AskingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
