import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";

@Injectable({ providedIn: "root" })
export class ApplicationContextService {
  private selectedAppSubject = new BehaviorSubject<string | null>(null);
  constructor() {
    // Load from localStorage on service initialization
    const savedApp = localStorage.getItem("selectedApp");
    if (savedApp) {
      this.selectedAppSubject.next(savedApp);
    }
  }
  selectedApp$ = this.selectedAppSubject.asObservable();

  setSelectedApp(codApp: string) {
    this.selectedAppSubject.next(codApp);
    localStorage.setItem("selectedApp", codApp);
  }

  getSelectedApp(): string | null {
    return this.selectedAppSubject.value || localStorage.getItem("selectedApp");
  }
  clearSelectedApp() {
    this.selectedAppSubject.next(null); // Add this line
    localStorage.removeItem("selectedApp");
  }
}
