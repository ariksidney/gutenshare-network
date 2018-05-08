import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {DocumentBrowser} from "../document-browser/document-browser";


const baseUrl = "http://localhost:8080";

@Injectable()
export class ApiService {


  constructor(private http: HttpClient) {

  }

  searchDocuments(q: string): Promise<any> {
    let params = new HttpParams().set('query', q);
    return this.http.get(baseUrl + "/api/search",
      {
        params: params, responseType: 'json'
      }).toPromise();
  }


  getDocuments(browsedDocuments: DocumentBrowser): Promise<any> {
    return this.http.get(baseUrl + "/api/browse",
      {
        params: {
          school: browsedDocuments.school,
          departement: browsedDocuments.departement,
          course: browsedDocuments.course
        }
      }).toPromise();
  }

  getDocumentDetails (id : string) {
    return this.http.get(baseUrl + "/api/document/" + id).toPromise();
}
}
