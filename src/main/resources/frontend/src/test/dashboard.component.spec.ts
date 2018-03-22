import { async, ComponentFixture, TestBed, getTestBed } from '@angular/core/testing';

import { DashboardComponent } from '../app/dashboard/dashboard.component';
import { HeaderComponent } from "../app/header/header.component";
import { FooterComponent } from "../app/footer/footer.component";
import { RouterTestingModule } from "@angular/router/testing";
import { QuestionsService } from "../app/questions/questions.service";
import { FormsModule } from "@angular/forms";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { Question } from '../app/questions/question'
import { Injectable } from '@angular/core';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let service: QuestionsService;
  let http: HttpClientTestingModule;
  let injector: TestBed;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DashboardComponent, HeaderComponent, FooterComponent],
      imports: [RouterTestingModule, FormsModule, HttpClientTestingModule],
      providers: [QuestionsService]
    })
      .compileComponents();

      injector = getTestBed();
      service = injector.get(QuestionsService);

  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    service = new QuestionsService(this.http);

    let mockData: Question[] = [
      {
        "id": 1,
        "updatedTime": "TestTime1",
        "postedDate": "TestDate1",
        "message": "TestMessage1",
        "author": "TestAuthor1",
        "votes": null,
        "answerModel": null,
        "questionCategories": null,
        "questionTitle": "TestTitle1",
        "bestAnswer": 1,
        "userId": 1
      },
      {
        "id": 2,
        "updatedTime": "TestTime2",
        "postedDate": "TestDate2",
        "message": "TestMessage2",
        "author": "TestAuthor2",
        "votes": null,
        "answerModel": null,
        "questionCategories": null,
        "questionTitle": "TestTitle2",
        "bestAnswer": 2,
        "userId": 2
      }
    ] as Question[];

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('#getAllQuestions()', () => {
    it('should return an array of Questions', () => {
      spyOn(service, 'getQuestions').and.returnValue(this.mockData);
      expect(component.getAllQuestions()).toEqual(this.mockData);
    })
  })

  describe('#OnSearch(var)', () => {
    it('should return an array of Questions', () => {
      spyOn(service, 'searchDashboard').and.returnValue(this.mockData);
      expect(component.OnSearch("any")).toEqual(this.mockData);
    })
  })

  describe('#TagSearch(var)', () => {
    it('should return an array of Questions', () => {
      spyOn(service, 'searchTag').and.returnValue(this.mockData);
      expect(component.TagSearch("any")).toEqual(this.mockData);
    })
  })

});
