import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BnaLogoComponent } from './bna-logo.component';

describe('BnaLogoComponent', () => {
  let component: BnaLogoComponent;
  let fixture: ComponentFixture<BnaLogoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BnaLogoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BnaLogoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
