import { Component, NgZone, OnInit } from '@angular/core';

import { NavigationItem } from '../../core/interfaces/navigation';


@Component({
  selector: 'app-ressources',
  templateUrl: './ressources.component.html',
  styleUrl: './ressources.component.scss',
  standalone : false,
      providers: [NavigationItem],
})
export class RessourcesComponent implements OnInit{

  
  constructor() {}
  
  ngOnInit() {}

}
