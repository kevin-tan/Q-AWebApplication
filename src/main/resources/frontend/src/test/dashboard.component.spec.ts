import { async, ComponentFixture, TestBed, getTestBed } from '@angular/core/testing';

import { DashboardComponent } from '../app/dashboard/dashboard.component';
import { HeaderComponent } from "../app/header/header.component";
import { FooterComponent } from "../app/footer/footer.component";
import { RouterTestingModule } from "@angular/router/testing";
import { QuestionsService } from "../app/questions/questions.service";
import { FormsModule } from "@angular/forms";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { Question } from '../app/questions/question'
import { Router } from '@angular/router';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let service: QuestionsService;
  let injector: TestBed;
  let router: Router;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DashboardComponent, HeaderComponent, FooterComponent],
      imports: [RouterTestingModule, FormsModule, HttpClientTestingModule],
      providers: [QuestionsService]
    })
      .compileComponents();

    injector = getTestBed();
    router = TestBed.get(Router);
    router.initialNavigation();

  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    service = new QuestionsService(this.http);

    let mockEmpty: Question[];
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
    it('should return an array of Questions if there are questions in the server', () => {
      spyOn(service, 'getQuestions').and.returnValue(this.mockData);
      expect(component.getAllQuestions()).toEqual(this.mockData);
    });
    it('should return an empty array  if there are no questions in the server', () => {
      spyOn(service, 'getQuestions').and.returnValue(this.mockEmpty);
      expect(component.getAllQuestions()).toEqual(this.mockEmpty);
    });
  });

  describe('#OnSearch(var)', () => {
    it('should return an array of Questions if there is a matching search in the server', () => {
      spyOn(service, 'searchDashboard').and.returnValue(this.mockData);
      expect(component.OnSearch("any")).toEqual(this.mockData);
    });
    it('should return an empty array  if there are no questions in the server', () => {
      spyOn(service, 'searchDashboard').and.returnValue(this.mockEmpty);
      expect(component.OnSearch("any")).toEqual(this.mockEmpty);
    });
  });

  describe('#TagSearch(var)', () => {
    it('should return an array of Questions if there are matching tags in the server', () => {
      spyOn(service, 'searchTag').and.returnValue(this.mockData);
      expect(component.TagSearch("any")).toEqual(this.mockData);
    });
    it('should return an empty array  if there are no questions in the server', () => {
      spyOn(service, 'searchTag').and.returnValue(this.mockEmpty);
      expect(component.TagSearch("any")).toEqual(this.mockEmpty);
    });
  });
  
  // describe('#OnSelectAsking', () => {
  //   it('should navigate you the asking page /dashboard/asking/', fakeAsync(() => {
  //     let path: String = 'dashboard/asking';
  //     router.navigate([path])
  //     tick();
  //     expect(router.url).toEqual(path)
  // }))
  // })
});
