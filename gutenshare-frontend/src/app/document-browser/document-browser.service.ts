import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {DocumentBrowser} from "./document-browser";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/x-www-form-urlencoded'
  })
};

@Injectable()
export class DocumentBrowserService {

  constructor(private http: HttpClient) {
  }

  result;

  getDocuments(browsedDocuments: DocumentBrowser) {
    return this.http.get("/api/browse",
      {
        params: {
          school: browsedDocuments.school,
          departement: browsedDocuments.departement,
          course: browsedDocuments.course
        }
      })
  }

  searchDocuments(q: string) {
    let params = new HttpParams().set('query', q);
    return this.http.get("http://localhost:8080/api/search",
      {
        params: params, responseType: 'json'
      }).toPromise();
  }

}
