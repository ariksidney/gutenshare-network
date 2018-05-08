import { Component } from '@angular/core';
import {DocumentBrowserService} from "./document-browser/document-browser.service";
import {ApiService} from "./api/api.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent {
  title = 'gutenshare network';

  searchInput = '';

  constructor(){}
}
