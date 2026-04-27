import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PackProfileComponent } from './pack-profile.component';

describe('PackProfileComponent', () => {
  let component: PackProfileComponent;
  let fixture: ComponentFixture<PackProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PackProfileComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PackProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
