import {TestBed, inject, getTestBed} from '@angular/core/testing';

import { AskingService } from '../app/asking/asking.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {Question} from "../app/questions/question";

describe('AskingService', () => {

  let injector: TestBed;
  let askingService: AskingService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AskingService],
      imports:[HttpClientTestingModule]
    });

    injector = getTestBed();
    askingService = injector.get(AskingService);
    httpMock = injector.get(HttpTestingController);

  });

  it('should be created', inject([AskingService], (service: AskingService) => {
    expect(service).toBeTruthy();
  }));

  it('should have method addQuestions()',
    inject([AskingService], (service: AskingService) => {
      expect(askingService.addQuestion)
    }));

  describe('#addQuestion', () => {
    it('should return an observable<Question> via POST', () => {
      let id = 1;
      let message = "gg";
      const mockData: Question = {id, message} as Question;

      askingService.addQuestion(mockData).subscribe(data => {
        expect(data).toEqual(mockData);
      });
      const request = httpMock.expectOne('http://localhost:8080/user/' + sessionStorage.getItem('id') + '/questions');
      expect(request.request.method).toBe('POST');
      request.flush(mockData);
    });
  });
});
