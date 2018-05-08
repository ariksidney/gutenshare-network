import { Component, OnInit } from '@angular/core';
import { SCHOOLS } from '../mock-data/mock-data';
import { DOCUMENTS } from '../mock-data/mock-data';
import { DocumentBrowserService } from "./document-browser.service";
import { DocumentBrowser } from "./document-browser";
import {ApiService} from "../api/api.service";
import {ActivatedRoute, Router} from "@angular/router";

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

  constructor(private apiService:ApiService, private route: ActivatedRoute, private router: Router) {
    router.events.subscribe(e => this.checkIfQueryIsDefined());
  }

  ngOnInit() {}

  checkIfQueryIsDefined() {
    let query = this.route.snapshot.params.query;
    if (query != undefined) {
      this.apiService.searchDocuments(query).then(response => this.documents = (response));
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

    this.router.navigateByUrl('/browse');
  }
}

