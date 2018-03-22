import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardComponent } from '../app/dashboard/dashboard.component';
import {HeaderComponent} from "../app/header/header.component";
import {FooterComponent} from "../app/footer/footer.component";
import {RouterTestingModule} from "@angular/router/testing";
import {QuestionsService} from "../app/questions/questions.service";
import {FormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashboardComponent, HeaderComponent, FooterComponent ],
      imports: [RouterTestingModule, FormsModule, HttpClientTestingModule],
      providers: [QuestionsService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
