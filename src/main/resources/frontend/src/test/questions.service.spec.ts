import {inject, TestBed, getTestBed} from '@angular/core/testing';

import {QuestionsService} from '../app/questions/questions.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {Question} from "../app/questions/question";

describe('QuestionsService', () => {
  let injector: TestBed;
  let questionsService: QuestionsService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [QuestionsService]
    });

    injector = getTestBed();
    questionsService = injector.get(QuestionsService);
    httpMock = injector.get(HttpTestingController);

  });

  it('should be created', inject([QuestionsService], (service: QuestionsService) => {
    expect(service).toBeTruthy();
  }));

    // the test below check to see if the component contains the required methods
    it('should have method getQuestions()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.getQuestions)
      }));
    it('should have method getQuestionsWithURL()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.getQuestionURL)
      }));
    it('should have method addAnswerToQuestion()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.addAnswerToQuestion)
      }));
    it('should have method editingQuestion()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.editingQuestion)
      }));
    it('should have method deletingQuestion()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.deletingQuestion)
      }));
    it('should have method editingAnswer()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.editingAnswer)
      }));
    it('should have method deletingAnswer()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.deletingAnswer)
      }));
    it('should have method searchDashboard()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.searchDashboard)
      }));
    it('should have method searchTag()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.searchTag)
      }));
    it('should have method upVotingQuestion()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.upVotingAnswer)
      }));
    it('should have method downVotingQuestion()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.downVotingQuestion)
      }));
    it('should have method upVotingAnswer()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.upVotingAnswer)
      }));
    it('should have method downVotingAnswer()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.downVotingAnswer)
      }));
    it('should have method unVotingQuestion()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.unVotingQuestion)
      }));
    it('should have method unVotingAnswer()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.unVotingAnswer)
      }));
    it('should have method getLeaderBoard()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.getLeaderBoard)
      }));
    it('should have method getAnswerWithURL()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.getAnswerWithURL)
      }));
    it('should have method getQuestionWithID()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.getQuestionWithID)
      }));
    it('should have method bestAnswer()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.bestAnswer)
      }));

  describe('#getQuestions', () => {
    it('should return an observable<Question> via GET', () => {
      let id = 1;
      let message = "gg";
      const mockData: Question = {id, message} as Question;

      questionsService.getQuestions().subscribe(data => {
        expect(data[0]).toBe(this.mockData);
      });
      const request = httpMock.expectOne('http://localhost:8080/questions');
      expect(request.request.method).toBe('GET');
      request.flush(mockData);
    });
  });
});
