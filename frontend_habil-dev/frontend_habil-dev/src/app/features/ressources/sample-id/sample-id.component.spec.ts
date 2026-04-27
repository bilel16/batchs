import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SampleIdComponent } from './sample-id.component';

describe('SampleIdComponent', () => {
  let component: SampleIdComponent;
  let fixture: ComponentFixture<SampleIdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SampleIdComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SampleIdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
