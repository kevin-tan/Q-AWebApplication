import { TestBed, inject } from '@angular/core/testing';

import { AskingService } from './asking.service';

describe('AskingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AskingService]
    });
  });

  it('should be created', inject([AskingService], (service: AskingService) => {
    expect(service).toBeTruthy();
  }));
});
