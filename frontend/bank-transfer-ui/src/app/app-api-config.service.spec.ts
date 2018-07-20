import { TestBed, inject } from '@angular/core/testing';

import { AppApiConfigService } from './app-api-config.service';

describe('AppApiConfigService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AppApiConfigService]
    });
  });

  it('should be created', inject([AppApiConfigService], (service: AppApiConfigService) => {
    expect(service).toBeTruthy();
  }));
});
