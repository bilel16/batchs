import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import { NextConfig } from '../../core/configuration/NextConfig';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss'],
  standalone:false
})
export class NavigationComponent implements OnInit {
  public windowWidth: number;
  public flatConfig: any;
  @Output() onNavMobCollapse = new EventEmitter();

  constructor() {
    this.flatConfig = NextConfig.config;
    this.windowWidth = window.innerWidth;
  }

  ngOnInit() { 

  }

  navMobCollapse() {
    if (this.windowWidth < 992) {
      this.onNavMobCollapse.emit();
    }
  }
}
