import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {QuestionsComponent} from '../app/questions/questions.component';
import {HeaderComponent} from "../app/header/header.component";
import {FooterComponent} from "../app/footer/footer.component";
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {QuestionsService} from "../app/questions/questions.service";

describe('QuestionsComponent', () => {
  let component: QuestionsComponent;
  let fixture: ComponentFixture<QuestionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuestionsComponent, HeaderComponent, FooterComponent ],
      imports: [RouterTestingModule, HttpClientTestingModule],
      providers: [QuestionsService]

    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuestionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
