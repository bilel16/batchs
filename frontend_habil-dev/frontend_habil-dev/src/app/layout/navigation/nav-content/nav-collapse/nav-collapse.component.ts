import { Component, Input, OnInit, AfterViewInit } from "@angular/core";
import { animate, style, transition, trigger } from "@angular/animations";
import { Router, NavigationEnd, ActivatedRoute } from "@angular/router";
import { filter } from "rxjs/operators";
import { NextConfig } from "../../../../core/configuration/NextConfig";
import { Location } from '@angular/common';

@Component({
  selector: "app-nav-collapse",
  templateUrl: "./nav-collapse.component.html",
  styleUrls: ["./nav-collapse.component.scss"],
  standalone: false,
  animations: [
    trigger("slideInOut", [
      transition(":enter", [
        style({ transform: "translateY(-100%)", display: "block" }),
        animate("250ms ease-in", style({ transform: "translateY(0%)" })),
      ]),
      transition(":leave", [
        animate("250ms ease-in", style({ transform: "translateY(-100%)" })),
      ]),
    ]),
  ],
})
export class NavCollapseComponent implements OnInit, AfterViewInit {
  public visible;
  @Input() item: any;
  public nextConfig: any;
  public themeLayout: string;
  hasActiveChild: boolean = false;

  constructor(private router: Router, private location: Location) {
    this.visible = false;
    this.nextConfig = NextConfig.config;
    this.themeLayout = this.nextConfig.layout;
  }

    ngOnInit() {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.checkActiveRoute();
    });
    
    // Initial check
    this.checkActiveRoute();
  }

  checkActiveRoute() {
    const currentUrl = this.location.path();
    console.log(currentUrl)
    this.hasActiveChild = this.item.children?.some((child : any) => 
      currentUrl.includes(child.url)
    );
  }

  ngAfterViewInit() {
    // Immediately expand all menus with active children
    this.preExpandActiveMenus();
  }

  private preExpandActiveMenus() {
    const currentUrl = this.location.path();
   console.log('Snapshot URL:', this.location.path());
    // Find all collapse components and check for active children
    const collapseElements = document.querySelectorAll('.pcoded-hasmenu[data-item-id]');
    console.log(collapseElements)    
    collapseElements.forEach(element => {
      const submenuItems = element.querySelectorAll('.pcoded-submenu a[href]');
      console.log(submenuItems)
      const hasActiveChild = Array.from(submenuItems).some(link => 
        currentUrl.includes((link as HTMLAnchorElement).getAttribute('href') || '')
      );
      console.log(hasActiveChild)
      if (hasActiveChild) {
        element.classList.add('pcoded-trigger', 'preloaded-expand');
      }
    });
    
    // Remove preloaded class after transitions are enabled
    setTimeout(() => {
      document.querySelectorAll('.preloaded-expand').forEach(el => {
        el.classList.remove('preloaded-expand');
      });
    }, 100);
  }
  
  navCollapse(e : any) {
    this.visible = !this.visible;

    let parent = e.target;
    if (this.themeLayout === "vertical") {
      parent = parent.parentElement;
    }

    const sections = document.querySelectorAll(".pcoded-hasmenu");
    console.log(parent.tagName)
    if (parent.tagName === 'A') {
      parent = parent.parentElement;
       console.log(parent);
    }

    if (parent.tagName === 'SPAN') {
      parent = parent.parentElement?.parentElement;
       console.log(parent);
    }

    for (let i = 0; i < sections.length; i++) {
     
      if (sections[i] !== parent) {
         
        sections[i].classList.remove("pcoded-trigger");
      }
    }

    let firstParent = parent.parentElement;
    let preParent = parent.parentElement.parentElement;
    if (firstParent.classList.contains("pcoded-hasmenu")) {
      do {
        firstParent.classList.add("pcoded-trigger");
        firstParent.parentElement.classList.toggle('pcoded-trigger');
        firstParent = firstParent.parentElement.parentElement.parentElement;
      } while (firstParent.classList.contains("pcoded-hasmenu"));
    } else if (preParent.classList.contains("pcoded-submenu")) {
      do {
        preParent.parentElement.classList.add("pcoded-trigger");
        preParent = preParent.parentElement.parentElement.parentElement;
      } while (preParent.classList.contains("pcoded-submenu"));
    }
    parent.classList.toggle("pcoded-trigger");
  }
}
