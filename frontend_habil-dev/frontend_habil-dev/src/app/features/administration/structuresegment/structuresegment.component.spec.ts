import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StructuresegmentComponent } from './structuresegment.component';

describe('StructuresegmentComponent', () => {
  let component: StructuresegmentComponent;
  let fixture: ComponentFixture<StructuresegmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StructuresegmentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StructuresegmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
