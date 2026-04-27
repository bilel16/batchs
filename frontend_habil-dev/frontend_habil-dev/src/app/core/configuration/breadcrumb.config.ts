export interface BreadcrumbConfig {
  title: string;
  icon: string;
  items: Array<{ label: string; icon: string; routerLink: string }>;
  url: string;
  subItems?: Array<{ label: string; routerLink: string }>;
  buildBreadcrumb?: (id?: string) => Array<{ label: string; routerLink: string }>;
}

export const BREADCRUMB_MAP: Record<string, BreadcrumbConfig> = {
  '/': {
    title: 'Accueil',
    icon: 'pi pi-home',
    url: '/',
    items: [],
  },

  '/ressources/sample': {
    title: 'Sample Page',
    icon: 'pi pi-file-plus',
    url: '/ressources/sample',
    items: [],
    subItems: [
      {
        label: 'Sample Page',
        routerLink: '/sample',
      },
    ],
  },
  '/ressources/sample/:id': {
    title: 'Sample ',
    icon: 'pi pi-book',
    url: '/ressources',
    items: [],
    buildBreadcrumb: (id) => [
      { label: 'Ressources', routerLink: '/ressources' },
      { label: `Sample #${id}`, routerLink: `/ressources/sample/${id}` }
    ]
  },
};
