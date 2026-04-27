import { ComponentFixture, TestBed } from "@angular/core/testing";

import { MenuApplicationComponent } from "./menuapplication.component";

describe("MenuApplicationComponent", () => {
  let component: MenuApplicationComponent;
  let fixture: ComponentFixture<MenuApplicationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MenuApplicationComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(MenuApplicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
