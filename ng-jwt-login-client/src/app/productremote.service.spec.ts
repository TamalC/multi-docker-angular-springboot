import { TestBed } from '@angular/core/testing';

import { ProductremoteService } from './productremote.service';

describe('ProductremoteService', () => {
  let service: ProductremoteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductremoteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
