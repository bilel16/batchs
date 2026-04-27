import { CommonModule } from '@angular/common';
import {Component, Input, OnDestroy, Inject, ViewEncapsulation} from '@angular/core';

@Component({
    selector: 'app-spinner',
    templateUrl: './spinner.component.html',
    styleUrls: [
        './spinner.component.scss'
    ],
    standalone : false,
    encapsulation: ViewEncapsulation.None
})
export class SpinnerComponent implements OnDestroy {
    public isSpinnerVisible = true;
    isLoading = true;

  
    constructor() {
        setTimeout(() => this.isLoading = false, 2300);
    }

    ngOnDestroy(): void {
        this.isSpinnerVisible = false;
    }
}
