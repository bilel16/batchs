import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSelectionStepComponent } from './user-selection-step.component';

describe('UserSelectionStepComponent', () => {
  let component: UserSelectionStepComponent;
  let fixture: ComponentFixture<UserSelectionStepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserSelectionStepComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserSelectionStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
