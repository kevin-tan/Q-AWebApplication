import { TestBed, inject } from '@angular/core/testing';

import { VerifyAuthenticationService } from './verify-authentication.service';

describe('VerifyAuthenticationService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [VerifyAuthenticationService]
    });
  });

  it('should be created', inject([VerifyAuthenticationService], (service: VerifyAuthenticationService) => {
    expect(service).toBeTruthy();
  }));
});
