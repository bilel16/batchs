import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationSelectionComponent } from './application-selection.component';

describe('ApplicationSelectionComponent', () => {
  let component: ApplicationSelectionComponent;
  let fixture: ComponentFixture<ApplicationSelectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ApplicationSelectionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApplicationSelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
