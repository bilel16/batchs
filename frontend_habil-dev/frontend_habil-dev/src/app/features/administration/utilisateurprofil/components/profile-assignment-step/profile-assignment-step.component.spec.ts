import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileAssignmentStepComponent } from './profile-assignment-step.component';

describe('ProfileAssignmentStepComponent', () => {
  let component: ProfileAssignmentStepComponent;
  let fixture: ComponentFixture<ProfileAssignmentStepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfileAssignmentStepComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfileAssignmentStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
