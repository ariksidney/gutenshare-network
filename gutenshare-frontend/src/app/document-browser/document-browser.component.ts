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
  noResults = false;

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

  getDocuments() {
    this.noResults = false;
      this.apiService.getDocuments(this.browseCategories)
        .subscribe(
          response => {
            this.matchingDocuments = response.body;
            this.browseCategories = new BrowseCategories;
            if (response.status == 204) {
              this.noResults = true;
            }
          },
          error => {
            this.noResults = true;
          });

      this.router.navigateByUrl('/browse');
  }
}

