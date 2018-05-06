import { Component, OnInit } from '@angular/core';
import { SCHOOLS } from '../mock-data/mock-data';
import { DOCUMENTS } from '../mock-data/mock-data';

@Component({
  selector: 'app-document-browser',
  templateUrl: './document-browser.component.html',
  styleUrls: ['./document-browser.component.scss']
})

export class DocumentBrowserComponent implements OnInit {

  schools: any[] = SCHOOLS;

  documents: any[] = [];
  currentSearchCriteria: string = '';
  sortReverse: boolean = false;

  constructor() { }

  ngOnInit() {
  }

  changeSortingCriteria(sortCriteria: string): void {
    this.sortReverse = this.currentSearchCriteria == sortCriteria && !this.sortReverse;

    this.currentSearchCriteria = sortCriteria;

    if (!this.sortReverse)
    {
      this.documents.sort((a,b) => 0 - (a[sortCriteria].toLowerCase() > b[sortCriteria].toLowerCase() ? -1 : 1));
    }
    else
    {
      this.documents.sort((a,b) => 0 - (a[sortCriteria].toLowerCase() > b[sortCriteria].toLowerCase() ? 1 : -1));
    }
  }

  getDocuments()
  {
    this.documents = DOCUMENTS;
    this.changeSortingCriteria('title');
  }
}

