import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { ErrorObservable } from "rxjs/observable/ErrorObservable";
import { catchError } from "rxjs/operators";
import { DocumentBrowser } from "./document-browser";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/x-www-form-urlencoded'
  })
};

@Injectable()
export class DocumentBrowserService {

  constructor(private http: HttpClient) { }

  getDocuments (browsedDocuments : DocumentBrowser) {
    return this.http.get("/api/browse",
      { params: {
        school: browsedDocuments.school,
        departement: browsedDocuments.departement,
        course: browsedDocuments.course}})
  }

  searchDocuments (q: string) {
    var blub = this.http.get("http://192.168.1.43:8080/api/search",
      {params: {
        query: q
      }})
      .subscribe((res: Response) => {
      var data = res.json();
        console.log(data);

    });
  }

}
