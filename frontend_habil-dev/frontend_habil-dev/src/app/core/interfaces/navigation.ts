import { Injectable } from '@angular/core';
import {PATHS} from '../constants/paths';

export interface NavigationItem {
  id: string;
  title: string;
  type: 'item' | 'collapse' | 'group';
  translate?: string;
  icon?: string;
  hidden?: boolean;
  url?: string;
  classes?: string;
  exactMatch?: boolean;
  external?: boolean;
  target?: boolean;
  breadcrumbs?: boolean;
  function?: any;
  badge?: {
    title?: string;
    type?: string;
  };
  children?: Navigation[];
}

export interface Navigation extends NavigationItem {
  children?: NavigationItem[];
}

const NavigationItems = [
  {
    id: 'modules',
    title: 'Modules',
    type: 'group',
    classes: 'nav-item',
    icon: 'pi pi-th-large',
    children: [
      {
        id: "ADMIN_DASHBOARD",
        title: "Dashboard",
        hidden: false,
        type: "item",
        url: "/" + PATHS.DASHBOARD,
        classes: "nav-item",
        icon: "pi pi-home",
      },
      {
        id: "ADMIN_SEG_STR",
        title: "Structure Segment",
        hidden: false,
        type: "item",
        url: "/administration/" + PATHS.STR_SEG,
        classes: "nav-item",
        icon: "pi pi-home",
      },      {
        id: "ADMIN_PERSONNEL",
        title: "Personnel",
        hidden: false,
        type: "collapse",
        icon: "pi pi-user-edit",
        children: [
          {
            id: 'ADMIN_PERSONNEL_GESTION',
            title: 'Gestion',
            hidden: false,
            type: 'item',
            url: "/administration/" + PATHS.PERSONNEL,
            classes: 'nav-item',
            icon: 'pi pi-user-plus',
            exactMatch: true,
          },
          {
            id: 'ADMIN_PERSONNEL_HR',
            title: 'Espace RH',
            hidden: false,
            type: 'item',
            url: "/administration/personnel/" + PATHS.HR_PERSONAL,
            classes: 'nav-item',
            icon: 'pi pi-users',
            exactMatch: true,

          }
        ]
      },
      {
        id: "ADMIN_APPLICATION",
        title: "Application",
        hidden: false,
        type: "item",
        url: "/administration/" + PATHS.APPLICATION,
        classes: "nav-item",
        icon: "pi pi-desktop",
      },
      {
        id: "ADMIN_MENU_APP",
        title: "Menu",
        hidden: false,
        type: "item",
        url: "/administration/" + PATHS.MENU_APPLICATION,
        classes: "nav-item",
        icon: "pi pi-bars",
      },
      {
        id: "ADMIN_PROFIL",
        title: "Profil",
        hidden: false,
        type: "item",
        url: "/administration/" + PATHS.PROFILE,
        classes: "nav-item",
        icon: "pi pi-user",
      },
      {
        id: 'ADMIN_PACK',
        title: 'Pack',
        hidden: false,
        type: "item",
        url: "/administration/" + PATHS.PACK,
        classes: "nav-item",
        icon: "pi pi-user",
      },
      {
        id: "ADMIN_PROF_MENU_APP",
        title: "Profil Menus",
        hidden: false,
        type: "item",
        url: "/administration/" + PATHS.PROFIL_MENU_APPLICATION,
        classes: "nav-item",
        icon: "pi pi-users",
      },
      {
        id: "ADMIN_PROF_MENU_APP",
        title: "Pack Profiles",
        hidden: false,
        type: "item",
        url: "/administration/" + PATHS.PACK_PROFILE,
        classes: "nav-item",
        icon: "pi pi-users",
      },      
      {
        id: 'ADMIN_USER_PROF',
        title: 'Utilisateur profil',
        hidden: false,
        type: "item",
        url: "/administration/" + PATHS.UTILISATEUR_PROFIL,
        classes: "nav-item",
        icon: "pi pi-user-edit",
      },            {
        id: 'ADMIN_INTERIM',
        title: 'Intérim',
        hidden: false,
        type: "item",
        url: "/administration/" + PATHS.INTERIM,
        classes: "nav-item",
        icon: "pi pi-sync",
      },
      {
        id: 'ADMIN_REF',
        title: 'Réferentiel',
        hidden: false,
        type: 'collapse',
        icon: 'pi pi-database',
        children: [
          {
            id: 'ADMIN_REF',
            title: 'Sample Page',
            hidden: false,
            type: 'item',
            url: '/ressources/sample',
            classes: 'nav-item',
            icon: 'pi pi-file',
          },
          {
            id: 'ADMIN_REF',
            title: 'Sample Page',
            hidden: false,
            type: 'item',
            url: '/ressources/sample',
            classes: 'nav-item',
            icon: 'pi pi-file',
          }
        ],
      },
    ],
  },
];

@Injectable()
export class NavigationItem {
  public get() {
    return NavigationItems;
  }
}
