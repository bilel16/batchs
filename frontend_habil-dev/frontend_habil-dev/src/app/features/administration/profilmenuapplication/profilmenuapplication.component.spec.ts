import { ComponentFixture, TestBed } from "@angular/core/testing";

import { ProfilMenuApplicationComponent } from "./profilmenuapplication.component";

describe("MenuApplicationComponent", () => {
  let component: ProfilMenuApplicationComponent;
  let fixture: ComponentFixture<ProfilMenuApplicationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfilMenuApplicationComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ProfilMenuApplicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
