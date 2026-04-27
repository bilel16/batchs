import { Component, OnInit, OnDestroy, AfterViewInit, HostListener, ViewChild, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription, forkJoin } from 'rxjs';
import { StatisticsService } from '../../core/services/backend/statistics.service';
import { PersonnelStats, ApplicationStats, ProfileStats, ApplicationProfileCount } from '../../core/models/statistics';
import { GovernorateInfo } from '../tunisia-map/tunisia-map.component';


type ApexChartsCtor = new (
  el: HTMLElement,
  options: any
) => {
  render(): Promise<void>;
  destroy(): void;
};

let ApexChartsLib: ApexChartsCtor;

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
  standalone: false
})
export class DashboardComponent implements OnInit, AfterViewInit, OnDestroy {

  private summaryChart: InstanceType<typeof ApexChartsLib> | null = null;
  private structureChart: InstanceType<typeof ApexChartsLib> | null = null;
  private applicationChart: InstanceType<typeof ApexChartsLib> | null = null;
  private profileSummaryChart: InstanceType<typeof ApexChartsLib> | null = null;
  private profileChart: InstanceType<typeof ApexChartsLib> | null = null;
  private profilesByAppChart: InstanceType<typeof ApexChartsLib> | null = null;

  // Sparkline charts for cards
  private personnelSparkChart: InstanceType<typeof ApexChartsLib> | null = null;
  private applicationSparkChart: InstanceType<typeof ApexChartsLib> | null = null;
  private profileSparkChart: InstanceType<typeof ApexChartsLib> | null = null;
  private assignmentChart: InstanceType<typeof ApexChartsLib> | null = null;
  private statsSub?: Subscription;

  loading = false;
  error: string | null = null;

  // Map full-screen state
  isMapFullScreen = false;

  // ViewChild references for map movement
  @ViewChild('mapCardContent') mapCardContent!: ElementRef;
  @ViewChild('fullscreenMapContent') fullscreenMapContent!: ElementRef;
  // Drawer state for governorate details
  isDrawerVisible = false;
  drawerGovernorate: GovernorateInfo | null = null;

  // Profile details modal state
  isProfileDetailsVisible = false;
  selectedAppForDetails: ApplicationProfileCount | null = null;
  selectedProfileType: 'active' | 'inactive' | 'all' = 'all';

  // Card data
  totalPersonnel = 0;
  activePersonnel = 0;
  inactivePersonnel = 0;
  totalApplications = 0;
  totalMenus = 0;
  totalProfiles = 0;
  activeProfiles = 0;
  inactiveProfiles = 0;

  // Store data for rendering after DOM is ready
  private personnelStats: PersonnelStats | null = null;
  private appStats: ApplicationStats | null = null;
  private profileStatsData: ProfileStats | null = null;
  private structureLabels: { [key: number]: string } = {};

  constructor(
    private statsService: StatisticsService,
    private router: Router
  ) {}

  ngOnInit(): void {}  
  async ngAfterViewInit(): Promise<void> { 
      if (typeof window === 'undefined') return;

        // Dynamic imports
  const apexModule = await import('apexcharts');
  ApexChartsLib = apexModule.default;
    // Initialize AOS for scroll animations - elements appear/disappear on scroll
  const aosModule = await import('aos');
  aosModule.default.init();    
    this.loadStats();
    
    // Refresh AOS after data loads to detect new elements
    setTimeout(() => {
      aosModule.default.refresh();
    }, 500);
  }

  private loadStats(): void {
    this.loading = true;
    this.error = null;

    this.statsSub = this.statsService.getStats().subscribe({
      next: (resp) => {
        const stats = resp.data;
        console.log('Stats loaded', stats);

        // Store personnel stats
        this.personnelStats = stats;

        // Set card data
        this.totalPersonnel = stats.total;
        this.activePersonnel = stats.active;
        this.inactivePersonnel = stats.inactive;

        const structureIds = stats.perStructure.map(s => s.structureId);

        forkJoin({
          labels: this.statsService.getStructureLabels(structureIds),
          appStats: this.statsService.getApplicationStats(),
          profileStats: this.statsService.getProfileStats()
        }).subscribe({
          next: (results) => {
            const labels = results.labels.data;
            const appStats = results.appStats.data;
            const profileStats = results.profileStats.data;

            console.log('Structure labels:', labels);
            console.log('Application stats:', appStats);
            console.log('Profile stats:', profileStats);

            // Store data
            this.structureLabels = labels;
            this.appStats = appStats;
            this.profileStatsData = profileStats;

            // Set card data
            this.totalApplications = appStats.totalApplications;
            this.totalMenus = appStats.totalMenus;
            this.totalProfiles = profileStats.totalProfiles;
            this.activeProfiles = profileStats.activeProfiles;
            this.inactiveProfiles = profileStats.inactiveProfiles;

            this.loading = false;

            // Wait for Angular to update the DOM and render charts
            setTimeout(() => {
              this.renderAllCharts();
            }, 100);
          },
          error: (err) => {
            console.error('Error loading labels/app stats/profile stats', err);
            this.error = 'Erreur lors du chargement des données complémentaires';
            this.loading = false;
          }
        });
      },
      error: (err) => {
        console.error('Error loading stats', err);
        this.error = 'Erreur lors du chargement des statistiques';
        this.loading = false;
      }
    });
  }

  private renderAllCharts(): void {
    if (!this.personnelStats || !this.appStats || !this.profileStatsData) {
      console.error('Data not loaded yet');
      return;
    }

    // Render sparkline charts (old layout - keeping for backward compatibility)
    this.renderPersonnelSparkline(this.personnelStats);
    this.renderApplicationSparkline(this.appStats);
    this.renderProfileSparkline(this.profileStatsData);    // Render main charts (old layout - keeping for backward compatibility)
    this.renderStructureChart(this.personnelStats, this.structureLabels);
    this.renderApplicationChart(this.appStats);
    this.renderProfileChart(this.profileStatsData);

    // Render Bento Grid assignment chart
    this.renderAssignmentChart();

    // Render Profiles by Application chart
    this.renderProfilesByAppChart(this.profileStatsData);
  }

  private renderPersonnelSparkline(stats: PersonnelStats): void {
    if (this.personnelSparkChart) {
      this.personnelSparkChart.destroy();
    }

    const el = document.querySelector('#personnelSpark');
    if (!el) {
      console.warn('personnelSpark element not found');
      return;
    }

    const options: ApexCharts.ApexOptions = {
      chart: {
        type: 'area',
        height: 160,
        sparkline: {
          enabled: true
        }
      },
      stroke: {
        curve: 'smooth',
        width: 2
      },
      fill: {
        opacity: 0.3,
        type: 'gradient',
        gradient: {
          shadeIntensity: 1,
          opacityFrom: 0.7,
          opacityTo: 0.3,
          stops: [0, 90, 100]
        }
      },
      series: [{
        name: 'Personnel',
        data: stats.perStructure.slice(0, 10).map(s => s.total)
      }],
      colors: ['#ffffff'],
      title: {
        text: this.totalPersonnel.toString(),
        offsetX: 20,
        offsetY: 20,
        style: {
          fontSize: '32px',
          fontWeight: '700',
          color: '#ffffff'
        }
      },
      subtitle: {
        text: 'Total Personnel',
        offsetX: 20,
        offsetY: 60,
        style: {
          fontSize: '14px',
          color: 'rgba(255, 255, 255, 0.8)'
        }      }
    };

    this.personnelSparkChart = new ApexChartsLib(el as HTMLElement, options);
    this.personnelSparkChart.render();
  }

  private renderApplicationSparkline(appStats: ApplicationStats): void {
    if (this.applicationSparkChart) {
      this.applicationSparkChart.destroy();
    }

    const el = document.querySelector('#applicationSpark');
    if (!el) {
      console.warn('applicationSpark element not found');
      return;
    }

    const options: ApexCharts.ApexOptions = {
      chart: {
        type: 'area',
        height: 160,
        sparkline: {
          enabled: true
        }
      },
      stroke: {
        curve: 'smooth',
        width: 2
      },
      fill: {
        opacity: 0.3,
        type: 'gradient',
        gradient: {
          shadeIntensity: 1,
          opacityFrom: 0.7,
          opacityTo: 0.3,
          stops: [0, 90, 100]
        }
      },
      series: [{
        name: 'Menus',
        data: appStats.perApplication.slice(0, 10).map(a => a.menuCount)
      }],
      colors: ['#ffffff'],
      title: {
        text: this.totalApplications.toString(),
        offsetX: 20,
        offsetY: 20,
        style: {
          fontSize: '32px',
          fontWeight: '700',
          color: '#ffffff'
        }
      },
      subtitle: {
        text: 'Total Applications',
        offsetX: 20,
        offsetY: 60,
        style: {
          fontSize: '14px',
          color: 'rgba(255, 255, 255, 0.8)'
        }      }
    };

    this.applicationSparkChart = new ApexChartsLib(el as HTMLElement, options);
    this.applicationSparkChart.render();
  }

  private renderProfileSparkline(profileStats: ProfileStats): void {
    if (this.profileSparkChart) {
      this.profileSparkChart.destroy();
    }

    const el = document.querySelector('#profileSpark');
    if (!el) {
      console.warn('profileSpark element not found');
      return;
    }

    const options: ApexCharts.ApexOptions = {
      chart: {
        type: 'area',
        height: 160,
        sparkline: {
          enabled: true
        }
      },
      stroke: {
        curve: 'smooth',
        width: 2
      },
      fill: {
        opacity: 0.3,
        type: 'gradient',
        gradient: {
          shadeIntensity: 1,
          opacityFrom: 0.7,
          opacityTo: 0.3,
          stops: [0, 90, 100]
        }
      },
      series: [{
        name: 'Profils',
        data: profileStats.perApplication.slice(0, 10).map(a => a.totalProfiles)
      }],
      colors: ['#ffffff'],
      title: {
        text: this.totalProfiles.toString(),
        offsetX: 20,
        offsetY: 20,
        style: {
          fontSize: '32px',
          fontWeight: '700',
          color: '#ffffff'
        }
      },
      subtitle: {
        text: 'Total Profils',
        offsetX: 20,
        offsetY: 60,
        style: {
          fontSize: '14px',
          color: 'rgba(255, 255, 255, 0.8)'
        }
      }    };

    this.profileSparkChart = new ApexChartsLib(el as HTMLElement, options);
    this.profileSparkChart.render();
  }

  private renderStructureChart(stats: PersonnelStats, labels: { [key: number]: string }): void {
    if (this.structureChart) {
      this.structureChart.destroy();
    }

    const el = document.querySelector('#structureChart');
    if (!el) {
      console.warn('structureChart element not found - will retry');
      // Retry after a short delay
      setTimeout(() => {
        const retryEl = document.querySelector('#structureChart');
        if (retryEl) {
          this.renderStructureChart(stats, labels);
        }
      }, 200);
      return;
    }

    const sorted = [...stats.perStructure].sort((a, b) => b.total - a.total);
    const top = sorted.slice(0, 10);

    const categories = top.map(s => {
      const label = labels[s.structureId];
      return label ? `${label}` : `Structure ${s.structureId}`;
    });

    const activeData = top.map(s => s.active);
    const inactiveData = top.map(s => s.inactive);

    const options: ApexCharts.ApexOptions = {
      chart: {
        type: 'bar',
        height: 380,
        stacked: true,
        toolbar: {
          show: false
        }
      },
      plotOptions: {
        bar: {
          horizontal: false,
          columnWidth: '55%',
          borderRadius: 5
        }
      },
      dataLabels: {
        enabled: false
      },
      xaxis: {
        categories,
        labels: {
          rotate: -45,
          rotateAlways: true,
          style: {
            fontSize: '11px'
          }
        }
      },
      yaxis: {
        title: {
          text: 'Nombre de personnel',
          style: {
            fontSize: '12px',
            fontWeight: 500
          }
        }
      },
      series: [
        { name: 'Actifs', data: activeData },
        { name: 'Inactifs', data: inactiveData }
      ],
      colors: ['#00E396', '#FF4560'],
      legend: {
        position: 'top',
        horizontalAlign: 'left'
      },
      title: {
        text: 'Personnel par structure (Top 10)',
        align: 'left',
        style: {
          fontSize: '18px',
          fontWeight: '600',
          color: '#263238'
        }
      },
      tooltip: {
        shared: true,
        intersect: false
      },
      grid: {
        borderColor: '#f1f1f1'
      }
    };    this.structureChart = new ApexChartsLib(el as HTMLElement, options);
    this.structureChart.render();
  }

  private renderApplicationChart(stats: ApplicationStats): void {
    if (this.applicationChart) {
      this.applicationChart.destroy();
    }

    const el = document.querySelector('#applicationChart');
    if (!el) {
      console.warn('applicationChart element not found - will retry');
      // Retry after a short delay
      setTimeout(() => {
        const retryEl = document.querySelector('#applicationChart');
        if (retryEl) {
          this.renderApplicationChart(stats);
        }
      }, 200);
      return;
    }

    const sorted = [...stats.perApplication].sort((a, b) => b.menuCount - a.menuCount);
    const top = sorted.slice(0, 10);

    const categories = top.map(a => a.applicationLabel || a.applicationCode);
    const menuCounts = top.map(a => a.menuCount);

    const options: ApexCharts.ApexOptions = {
      chart: {
        type: 'bar',
        height: 380,
        toolbar: {
          show: false
        }
      },
      plotOptions: {
        bar: {
          horizontal: true,
          distributed: true,
          borderRadius: 5,
          dataLabels: {
            position: 'top'
          }
        }
      },
      dataLabels: {
        enabled: true,
        textAnchor: 'start',
        style: {
          colors: ['#fff'],
          fontSize: '12px',
          fontWeight: 600
        },
        formatter: function (val: any) {
          return val + ' menus';
        },
        offsetX: 0
      },
      xaxis: {
        categories,
        title: {
          text: 'Nombre de menus',
          style: {
            fontSize: '12px',
            fontWeight: 500
          }
        }
      },
      yaxis: {
        labels: {
          style: {
            fontSize: '11px'
          }
        }
      },
      series: [{
        name: 'Menus',
        data: menuCounts
      }],
      title: {
        text: `Menus par application (Top 10)`,
        align: 'left',
        style: {
          fontSize: '18px',
          fontWeight: '600',
          color: '#263238'
        }
      },
      legend: {
        show: false
      },
      colors: ['#008FFB', '#00E396', '#FEB019', '#FF4560', '#775DD0',
        '#546E7A', '#26a69a', '#D10CE8', '#F9A3A4', '#90EE7E'],
      grid: {
        borderColor: '#f1f1f1'
      }
    };    this.applicationChart = new ApexChartsLib(el as HTMLElement, options);
    this.applicationChart.render();
  }

  private renderProfileChart(stats: ProfileStats): void {
    if (this.profileChart) {
      this.profileChart.destroy();
    }

    const el = document.querySelector('#profileChart');
    if (!el) {
      console.warn('profileChart element not found - will retry');
      // Retry after a short delay
      setTimeout(() => {
        const retryEl = document.querySelector('#profileChart');
        if (retryEl) {
          this.renderProfileChart(stats);
        }
      }, 200);
      return;
    }

    const sorted = [...stats.perApplication].sort((a, b) => b.totalProfiles - a.totalProfiles);
    const top = sorted.slice(0, 10);

    const categories = top.map(a => a.applicationLabel || a.applicationCode);
    const activeData = top.map(a => a.activeProfiles);
    const inactiveData = top.map(a => a.inactiveProfiles);

    const options: ApexCharts.ApexOptions = {
      chart: {
        type: 'bar',
        height: 380,
        stacked: true,
        toolbar: {
          show: false
        }
      },
      plotOptions: {
        bar: {
          horizontal: true,
          borderRadius: 5
        }
      },
      dataLabels: {
        enabled: true,
        style: {
          fontSize: '11px',
          fontWeight: 600
        }
      },
      xaxis: {
        categories,
        title: {
          text: 'Nombre de profils',
          style: {
            fontSize: '12px',
            fontWeight: 500
          }
        }
      },
      yaxis: {
        labels: {
          style: {
            fontSize: '11px'
          }
        }
      },
      series: [
        { name: 'Actifs', data: activeData },
        { name: 'Inactifs', data: inactiveData }
      ],
      colors: ['#00E396', '#FF4560'],
      legend: {
        position: 'top',
        horizontalAlign: 'left'
      },
      title: {
        text: 'Profils par application (Top 10)',
        align: 'left',
        style: {
          fontSize: '18px',
          fontWeight: '600',
          color: '#263238'
        }
      },
      tooltip: {
        shared: true,
        intersect: false,
        y: {
          formatter: function (val: any) {
            return val + ' profils';
          }
        }
      },
      grid: {
        borderColor: '#f1f1f1'
      }
    };    this.profileChart = new ApexChartsLib(el as HTMLElement, options);
    this.profileChart.render();
  }

  private renderAssignmentChart(): void {
    if (this.assignmentChart) {
      this.assignmentChart.destroy();
    }

    const el = document.querySelector('#assignmentChart');
    if (!el) {
      console.warn('assignmentChart element not found');
      return;
    }    // Mock data for pack vs custom profiles (65% pack, 35% custom)
    const options: ApexCharts.ApexOptions = {
      chart: {
        type: 'donut',
        height: 220,
        fontFamily: 'inherit'
      },
      series: [65, 35],
      labels: ['Via Packs', 'Direct Profile'],
      colors: ['#19e64d', '#cbd5e1'],
      plotOptions: {
        pie: {
          donut: {
            size: '70%',
            labels: {
              show: true,
              name: {
                show: true,
                fontSize: '12px',
                fontWeight: 500,
                color: '#64748b'
              },
              value: {
                show: true,
                fontSize: '28px',
                fontWeight: 700,
                color: '#1e293b',
                formatter: function (val) {
                  return val + '%';
                }
              },
              total: {
                show: true,
                label: 'Pack Based',
                fontSize: '12px',
                fontWeight: 500,
                color: '#64748b',
                formatter: function () {
                  return '65%';
                }
              }
            }
          }
        }
      },
      dataLabels: {
        enabled: false
      },
      legend: {
        show: false
      },
      stroke: {
        width: 0
      },
      tooltip: {
        y: {
          formatter: function (val) {
            return val + '%';
          }
        }
      }
    };    this.assignmentChart = new ApexChartsLib(el as HTMLElement, options);
    this.assignmentChart.render();
  }

  /**
   * Renders a grouped bar chart showing active and inactive profiles per application
   * Better visualization than 100% stacked for comparing actual numbers
   */  private renderProfilesByAppChart(stats: ProfileStats): void {
    if (this.profilesByAppChart) {
      this.profilesByAppChart.destroy();
    }

    const el = document.querySelector('#profilesByAppChart');
    if (!el) {
      console.warn('profilesByAppChart element not found');
      return;
    }

    // Sort by total profiles and show ALL applications
    const sorted = [...stats.perApplication].sort((a, b) => b.totalProfiles - a.totalProfiles);
    
    // Show all applications
    const allApps = sorted;

    const categories = allApps.map(a => a.applicationLabel || a.applicationCode);
    const activeData = allApps.map(a => a.activeProfiles);
    const inactiveData = allApps.map(a => a.inactiveProfiles);

    const options: ApexCharts.ApexOptions = {
      series: [
        {
          name: 'Profils Actifs',
          data: activeData
        },
        {
          name: 'Profils Inactifs',
          data: inactiveData
        }
      ],
      chart: {
        type: 'bar',
        height: 450,
        stacked: true,
        toolbar: {
          show: true,
          tools: {
            download: true,
            zoom: false,
            zoomin: false,
            zoomout: false,
            pan: false,
            reset: false
          }
        },
        fontFamily: 'inherit',
        events: {
          dataPointSelection: (event: any, chartContext: any, config: any) => {
            const seriesIndex = config.seriesIndex; // 0 = active, 1 = inactive
            const dataPointIndex = config.dataPointIndex;
            const clickedApp = allApps[dataPointIndex];
            
            const profileType: 'active' | 'inactive' = seriesIndex === 0 ? 'active' : 'inactive';
            
            console.log(`📊 Clicked: ${clickedApp.applicationLabel} - ${profileType} profiles`);
            this.openProfileDetails(clickedApp, profileType);
          }
        }
      },
      plotOptions: {
        bar: {
          horizontal: false,
          borderRadius: 4,
          borderRadiusApplication: 'end',
          columnWidth: '85%'
        }
      },
      dataLabels: {
        enabled: true,
        formatter: (val: any) => {
          if (val === 0) return '';
          return val >= 1000 ? (val / 1000).toFixed(1) + 'K' : val.toString();
        },
        style: {
          fontSize: '9px',
          fontWeight: 600,
          colors: ['#fff']
        }
      },
      stroke: {
        show: true,
        width: 1,
        colors: ['#fff']
      },
      xaxis: {
        categories,
        labels: {
          rotate: -45,
          rotateAlways: true,
          style: {
            fontSize: '10px',
            fontWeight: 500
          },
          trim: true,
          maxHeight: 120
        }
      },
      yaxis: {
        title: {
          text: 'Nombre de profils',
          style: {
            fontSize: '13px',
            fontWeight: 600,
            color: '#64748b'
          }
        },
        labels: {
          formatter: (val: any) => {
            return val >= 1000 ? (val / 1000).toFixed(0) + 'K' : val.toString();
          },
          style: {
            fontSize: '11px'
          }
        }
      },
      fill: {
        opacity: 1
      },
      colors: ['#00E396', '#FF4560'],
      legend: {
        position: 'top',
        horizontalAlign: 'left',
        fontSize: '13px',
        fontWeight: 600,
        markers: {
          size: 6,
          shape: 'circle'
        },
        itemMargin: {
          horizontal: 15,
          vertical: 5
        }
      },
      tooltip: {
        shared: true,
        intersect: false,
        y: {
          formatter: function (val: any) {
            return val + ' profils';
          }
        }
      },
      grid: {
        borderColor: '#e2e8f0',
        strokeDashArray: 3,
        xaxis: {
          lines: {
            show: false
          }
        },
        yaxis: {
          lines: {
            show: true
          }
        },
        padding: {
          top: 0,
          right: 10,
          bottom: 10,
          left: 10        }
      }
    };

    this.profilesByAppChart = new ApexChartsLib(el as HTMLElement, options);
    this.profilesByAppChart.render();
  }

  /**
   * Toggle map full-screen mode
   */
  toggleMapFullScreen(): void {
    this.isMapFullScreen = !this.isMapFullScreen;
    
    if (this.isMapFullScreen) {
      console.log('🔲 Map expanded to full screen');
      // Prevent body scroll when overlay is open
      document.body.style.overflow = 'hidden';
      
      // Move map to fullscreen container after view updates
      setTimeout(() => {
        this.moveMapToFullscreen();
      }, 0);
    } else {
      console.log('🔳 Map collapsed to card view');
      document.body.style.overflow = '';
      
      // Move map back to card
      setTimeout(() => {
        this.moveMapToCard();
      }, 0);
    }
  }

  /**
   * Move map component to fullscreen container
   */
  private moveMapToFullscreen(): void {
    if (this.mapCardContent && this.fullscreenMapContent) {
      const mapElement = this.mapCardContent.nativeElement.querySelector('app-tunisia-map');
      if (mapElement) {
        this.fullscreenMapContent.nativeElement.appendChild(mapElement);
        console.log('✅ Map moved to fullscreen');
      }
    }
  }

  /**
   * Move map component back to card
   */
  private moveMapToCard(): void {
    if (this.mapCardContent && this.fullscreenMapContent) {
      const mapElement = this.fullscreenMapContent.nativeElement.querySelector('app-tunisia-map');
      if (mapElement) {
        this.mapCardContent.nativeElement.appendChild(mapElement);
        console.log('✅ Map moved back to card');
      }
    }
  }

  /**
   * Close full-screen map (ESC key handler)
   */
  closeMapFullScreen(): void {
    if (this.isMapFullScreen) {
      this.isMapFullScreen = false;
      document.body.style.overflow = '';
      
      // Move map back to card
      setTimeout(() => {
        this.moveMapToCard();
      }, 0);
      
      console.log('🔳 Map full-screen closed');
    }
  }  /**
   * Handle ESC key to close full-screen
   */
  @HostListener('document:keydown.escape')
  handleEscapeKey(): void {
    if (this.isMapFullScreen) {
      this.closeMapFullScreen();
    }
    if (this.isDrawerVisible) {
      this.closeDrawer();
    }
    if (this.isProfileDetailsVisible) {
      this.closeProfileDetails();
    }
  }

  // ════════════════════════════════════════════════════════════════════════
  // DRAWER MANAGEMENT FOR GOVERNORATE DETAILS
  // ════════════════════════════════════════════════════════════════════════

  /**
   * Opens the drawer with governorate information.
   * Called when a governorate is selected on the map.
   */
  onGovernorateSelected(governorate: GovernorateInfo): void {
    this.drawerGovernorate = governorate;
    this.isDrawerVisible = true;
    console.log('📍 Governorate selected:', governorate);
  }

  /**
   * Closes the info drawer.
   */
  closeDrawer(): void {
    if (this.isDrawerVisible) {
      this.isDrawerVisible = false;
      console.log('🚪 Drawer closing...');
    }
  }

  /**
   * Handles the drawer closed event after animation completes.
   */
  onDrawerClosed(): void {
    this.drawerGovernorate = null;
    console.log('🚪 Drawer closed and cleaned up');
  }

  /**
   * Formats numbers with thousand separators.
   */
  formatNumber(num: number | undefined): string {
    if (num === undefined) return 'N/A';
    return num.toLocaleString('fr-FR');
  }

  // ════════════════════════════════════════════════════════════════════════
  // PROFILE DETAILS MODAL MANAGEMENT
  // ════════════════════════════════════════════════════════════════════════

  /**
   * Opens the profile details modal for a specific application
   * @param app - The application profile count data
   * @param profileType - Which profile type was clicked ('active' or 'inactive')
   */
  openProfileDetails(app: ApplicationProfileCount, profileType: 'active' | 'inactive'): void {
    this.selectedAppForDetails = app;
    this.selectedProfileType = profileType;
    this.isProfileDetailsVisible = true;
    document.body.style.overflow = 'hidden';
    console.log(`📋 Opening profile details for ${app.applicationLabel} (${profileType})`);
  }

  /**
   * Closes the profile details modal
   */
  closeProfileDetails(): void {
    this.isProfileDetailsVisible = false;
    document.body.style.overflow = '';
    this.selectedAppForDetails = null;
    console.log('📋 Profile details modal closed');
  }

  /**
   * Gets the count for the selected profile type
   */
  getSelectedProfileCount(): number {
    if (!this.selectedAppForDetails) return 0;
    
    if (this.selectedProfileType === 'active') {
      return this.selectedAppForDetails.activeProfiles;
    } else if (this.selectedProfileType === 'inactive') {
      return this.selectedAppForDetails.inactiveProfiles;
    } else {
      return this.selectedAppForDetails.totalProfiles;
    }
  }
  /**
   * Gets the title for the modal based on profile type
   */
  getProfileDetailsTitle(): string {
    if (!this.selectedAppForDetails) return '';
    
    const appName = this.selectedAppForDetails.applicationLabel || this.selectedAppForDetails.applicationCode;
    const typeLabel = this.selectedProfileType === 'active' ? 'Actifs' : 'Inactifs';
    
    return `${appName} - Profils ${typeLabel}`;
  }
  /**
   * Navigates to the profile page with pre-applied filters
   * Redirects users to see the actual detailed list of profiles
   * Passes application code and status for automatic table filtering
   */
  viewProfileDetails(): void {
    if (!this.selectedAppForDetails) return;

    const queryParams = {
      filterByApp: this.selectedAppForDetails.applicationCode,
      filterByStatus: this.selectedProfileType === 'active' ? '1' : '0', // 1=active, 0=inactive
      fromDashboard: 'true',
      appLabel: this.selectedAppForDetails.applicationLabel || this.selectedAppForDetails.applicationCode
    };

    console.log('🔗 Navigating to profile page with filters:', queryParams);
    console.log(`📊 User will see: ${this.selectedProfileType === 'active' ? 'Active' : 'Inactive'} profiles for ${queryParams.appLabel}`);

    // Close modal first
    this.closeProfileDetails();

    // Navigate to profile page with query parameters for table filtering
    this.router.navigate(['/administration/profile'], { queryParams });
  }

  ngOnDestroy(): void {
    if (this.summaryChart) this.summaryChart.destroy();
    if (this.structureChart) this.structureChart.destroy();
    if (this.applicationChart) this.applicationChart.destroy();
    if (this.profileSummaryChart) this.profileSummaryChart.destroy();
    if (this.profileChart) this.profileChart.destroy();
    if (this.personnelSparkChart) this.personnelSparkChart.destroy();
    if (this.applicationSparkChart) this.applicationSparkChart.destroy();
    if (this.profileSparkChart) this.profileSparkChart.destroy();
    if (this.assignmentChart) this.assignmentChart.destroy();
    if (this.profilesByAppChart) this.profilesByAppChart.destroy();
    if (this.statsSub) this.statsSub.unsubscribe();
  }
}
