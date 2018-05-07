import { Component } from '@angular/core';
import {DocumentBrowserService} from "./document-browser/document-browser.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent {
  title = 'gutenshare network';

  searchInput = '';

  constructor(private documentBrowserService: DocumentBrowserService) {}

  search(q: string) {
    console.log(q);
    var blub = this.documentBrowserService.searchDocuments(q);
  }
}
