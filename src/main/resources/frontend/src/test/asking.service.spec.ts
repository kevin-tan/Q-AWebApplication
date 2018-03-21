import { TestBed, inject } from '@angular/core/testing';

import { AskingService } from '../app/asking/asking.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('AskingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AskingService],
      imports:[HttpClientTestingModule]
    });
  });

  it('should be created', inject([AskingService], (service: AskingService) => {
    expect(service).toBeTruthy();
  }));
});
