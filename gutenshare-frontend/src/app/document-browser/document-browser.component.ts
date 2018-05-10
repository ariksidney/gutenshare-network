import { Component, OnInit } from '@angular/core';
import { BrowseCategories } from "./browse-categories";
import { ApiService } from "../api/api.service";
import { ActivatedRoute, Router } from "@angular/router";

@Component({
  selector: 'app-document-browser',
  templateUrl: './document-browser.component.html',
  styleUrls: ['./document-browser.component.scss']
})

export class DocumentBrowserComponent implements OnInit {

  predefinedCategories: any = [];
  browseCategories = new BrowseCategories;
  matchingDocuments: any[] = [];

  activeSchool: string = '';
  activeDepartement: string = '';
  activeCourse: string = '';

  constructor(private apiService:ApiService, private route: ActivatedRoute, private router: Router) {
    router.events.subscribe(e => { this.checkIfQueryIsDefined(), this.fetchCategories()});
  }

  ngOnInit() {
  }

  fetchCategories() {
    this.apiService.getCategories()
      .then(response => {
        this.predefinedCategories = response;
      });
  }

  checkIfQueryIsDefined() {
    let query = this.route.snapshot.params.query;

    if (query != undefined) {
      this.apiService.searchDocuments(query).then(response => this.matchingDocuments = (response));
    }
  }

  addSchool(school: string) {
    this.browseCategories.school = school;
  }

  addDepartement(departement: string) {
    this.browseCategories.departement = departement;
  }

  addCourse(course: string) {
    this.activeSchool = course;
    this.browseCategories.course = course;
  }

  getDocuments() {
    this.apiService.getDocuments(this.browseCategories).then(
      response => {this.matchingDocuments = response}
    );

    this.router.navigateByUrl('/browse');
  }
}

