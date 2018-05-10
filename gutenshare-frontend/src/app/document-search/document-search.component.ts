import {Component, OnInit} from "@angular/core";
import {ApiService} from "../api/api.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-document-search',
  templateUrl: './document-search.component.html',
  styleUrls: ['./document-search.component.scss']
})

export class DocumentSearchComponent implements OnInit {

  constructor(private apiService:ApiService, private router: Router) {}

  ngOnInit(){}

  search(q: string) {

    this.apiService.searchDocuments(q).then(response => console.log(response));
    this.router.navigateByUrl('/browse/' + q);
  }
}
