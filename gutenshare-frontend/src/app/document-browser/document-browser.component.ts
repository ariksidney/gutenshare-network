import { Component, OnInit } from '@angular/core';
import { SCHOOLS } from '../mock-data/mock-data';


@Component({
  selector: 'app-document-browser',
  templateUrl: './document-browser.component.html',
  styleUrls: ['./document-browser.component.scss']
})

export class DocumentBrowserComponent implements OnInit {

  schools: any[] = SCHOOLS;

  constructor() { }

  ngOnInit() {
  }

}
