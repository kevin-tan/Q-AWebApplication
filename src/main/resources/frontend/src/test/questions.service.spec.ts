import {inject, TestBed} from '@angular/core/testing';

import {QuestionsService} from '../app/questions/questions.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('QuestionsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [QuestionsService],
      imports: [HttpClientTestingModule]
    });
  });

  it('should be created', inject([QuestionsService], (service: QuestionsService) => {
    expect(service).toBeTruthy();
  }));
});
