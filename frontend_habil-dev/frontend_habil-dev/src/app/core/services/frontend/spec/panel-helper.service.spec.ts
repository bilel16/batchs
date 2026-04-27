import { TestBed } from '@angular/core/testing';

import { PanelHelperService } from '../panel-helper.service';

describe('PanelHelperService', () => {
  let service: PanelHelperService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PanelHelperService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
