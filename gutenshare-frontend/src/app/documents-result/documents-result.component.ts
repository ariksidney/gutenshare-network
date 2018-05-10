import {Component, Input, OnInit} from "@angular/core";

@Component({
  selector: 'app-documents-result',
  templateUrl: './documents-result.component.html',
  styleUrls: ['./documents-result.component.scss']
})

export class DocumentsResultComponent implements OnInit {

  @Input() documents: any[];

  currentSearchCriteria: string = '';
  sortReverse: boolean = false;

  constructor() {}

  ngOnInit(){}

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
}
