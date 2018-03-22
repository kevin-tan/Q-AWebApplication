import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardComponent } from '../app/dashboard/dashboard.component';
import { HeaderComponent } from "../app/header/header.component";
import { FooterComponent } from "../app/footer/footer.component";
import { RouterTestingModule } from "@angular/router/testing";
import { QuestionsService } from "../app/questions/questions.service";
import { FormsModule } from "@angular/forms";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { Question } from '../app/questions/question'

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let service: QuestionsService;
  let http: HttpClientTestingModule;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DashboardComponent, HeaderComponent, FooterComponent],
      imports: [RouterTestingModule, FormsModule, HttpClientTestingModule],
      providers: [QuestionsService]
    })
      .compileComponents();
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
        "bestAnswer": 1
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
        "bestAnswer": 2
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

});
