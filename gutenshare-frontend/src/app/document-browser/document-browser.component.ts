import { Component, OnInit } from '@angular/core';
import { SCHOOLS } from '../mock-data/mock-data';
import { DOCUMENTS } from '../mock-data/mock-data';
import { DocumentBrowserService } from "./document-browser.service";
import { DocumentBrowser } from "./document-browser";
import {ApiService} from "../api/api.service";

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

  browsedDocuments = new DocumentBrowser();

  constructor(private apiService:ApiService) { }

  ngOnInit() {
  }

  changeSortingCriteria(sortCriteria: string): void {
    this.sortReverse = this.currentSearchCriteria == sortCriteria && !this.sortReverse;

    this.currentSearchCriteria = sortCriteria;

      if (!this.sortReverse) {
        this.documents.sort((a, b) => 0 - (a[sortCriteria].toLowerCase() > b[sortCriteria].toLowerCase() ? -1 : 1));
      }
      else {
        this.documents.sort((a, b) => 0 - (a[sortCriteria].toLowerCase() > b[sortCriteria].toLowerCase() ? 1 : -1));
      }

  }

  addSchool(school: string) {
    this.browsedDocuments.school = school;
  }

  addDepartement(departement: string) {
    this.browsedDocuments.departement = departement;
  }

  addCourse(course: string) {
    this.browsedDocuments.course = course;
  }

  getDocuments() {
    this.apiService.getDocuments(this.browsedDocuments).then(
      response => this.documents = response
    );
    this.changeSortingCriteria('title');
  }
}

