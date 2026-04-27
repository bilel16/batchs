import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelSeparatorComponent } from './panel-separator.component';

describe('PanelSeparatorComponent', () => {
  let component: PanelSeparatorComponent;
  let fixture: ComponentFixture<PanelSeparatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PanelSeparatorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PanelSeparatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
