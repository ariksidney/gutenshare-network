import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {DocumentBrowser} from "../document-browser/document-browser";
import {DocumentReview} from "../document-detail/document-rating";
import {DocumentComment} from "../document-detail/document-comment";

const baseUrl = "http://api.gutenshare.network:28080";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/x-www-form-urlencoded'
  })
};

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

  getDocumentDetails(id: string) {
    return this.http.get(baseUrl + "/api/document/" + id).toPromise();
  }

  postComment(documentComment: DocumentComment) {
    return this.http.post<FormData>(
      `${baseUrl}/api/document/comment?documentid=${documentComment.documentid}&user=rudi&comment=${documentComment.content}`, ''
    ).toPromise();
  }

  postRating(documentReview: DocumentReview) {
    return this.http.post<FormData>(
      `${baseUrl}/api/document/rating?documentid=${documentReview.documentid}&user=rudi&rating=${documentReview.rating}`, ''
      ).toPromise();
  }
}
