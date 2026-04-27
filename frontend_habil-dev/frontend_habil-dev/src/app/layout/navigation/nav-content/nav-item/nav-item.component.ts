import {Component, Input, NgZone, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import { NavigationItem } from '../../../../core/interfaces/navigation';
import { NextConfig } from '../../../../core/configuration/NextConfig';

@Component({
  selector: 'app-nav-item',
  templateUrl: './nav-item.component.html',
  styleUrls: ['./nav-item.component.scss'],
  standalone:false
})
export class NavItemComponent implements OnInit {
  @Input() item: any;
  public flatConfig: any;
  public themeLayout: string;
  i :any;
 Menus:any;
  constructor(private location: Location) {
    this.flatConfig = NextConfig.config;
    this.themeLayout = this.flatConfig['layout'];
    const Menus = sessionStorage.getItem('menus');
  }

  ngOnInit() {
    // Navigation filtering is now handled at the parent level
    // No need for individual item visibility logic here
  }

  closeOtherMenu(event : any) {
    if (this.flatConfig['layout'] === 'vertical') {
      const ele = event.target;
      if (ele !== null && ele !== undefined) {
        const parent = ele.parentElement;
        const up_parent = parent.parentElement.parentElement;
        const last_parent = up_parent.parentElement;
        const sections = document.querySelectorAll('.pcoded-hasmenu');
        for (let i = 0; i < sections.length; i++) {
          sections[i].classList.remove('active');
          sections[i].classList.remove('pcoded-trigger');
        }

        if (parent.classList.contains('pcoded-hasmenu')) {
          parent.classList.add('pcoded-trigger');
          parent.classList.add('active');
        } else if (up_parent.classList.contains('pcoded-hasmenu')) {
          up_parent.classList.add('pcoded-trigger');
          up_parent.classList.add('active');
        } else if (last_parent.classList.contains('pcoded-hasmenu')) {
          last_parent.classList.add('pcoded-trigger');
          last_parent.classList.add('active');
        }
      }
      if ((document.querySelector('app-navigation.pcoded-navbar')!.classList.contains('mob-open'))) {
        document.querySelector('app-navigation.pcoded-navbar')!.classList.remove('mob-open');
      }
    } else {
      setTimeout(() => {
        const sections = document.querySelectorAll('.pcoded-hasmenu');
        for (let i = 0; i < sections.length; i++) {
          sections[i].classList.remove('active');
          sections[i].classList.remove('pcoded-trigger');
        }

        let current_url = this.location.path();
        if ((this.location as any)['_baseHref']) {
          current_url = (this.location as any)['_baseHref'] + this.location.path();
        }
        const link = "a.nav-link[ href='" + current_url + "' ]";
        const ele = document.querySelector(link);
        if (ele !== null && ele !== undefined) {
          const parent = ele.parentElement;
          const up_parent = parent!.parentElement!.parentElement;
          const last_parent = up_parent!.parentElement;
          if (parent!.classList.contains('pcoded-hasmenu')) {
            parent!.classList.add('active');
          } else if (up_parent!.classList.contains('pcoded-hasmenu')) {
            up_parent!.classList.add('active');
          } else if (last_parent!.classList.contains('pcoded-hasmenu')) {
            last_parent!.classList.add('active');
          }
        }
      }, 500);
    }
  }

}
