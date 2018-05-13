import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse} from "@angular/common/http";
import {BrowseCategories} from "../document-browser/browse-categories";
import {DocumentRating} from "../document-detail/document-rating";
import {DocumentComment} from "../document-detail/document-comment";
import {catchError} from "rxjs/operators";
import {ErrorObservable} from "rxjs/observable/ErrorObservable";
import {SessionStorage} from "../login/session.storage";
import {Observable} from "rxjs/Observable";
import {Response} from "@angular/http";

const baseUrl = "http://api.gutenshare.network:28080";
// const baseUrl = "http://localhost:4200";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/x-www-form-urlencoded'
  })
};

@Injectable()
export class ApiService {

  constructor(private http: HttpClient,
              private session: SessionStorage) {
  }

  searchDocuments(q: string): Promise<any> {
    let params = new HttpParams().set('query', q);
    return this.http.get(baseUrl + "/api/search",
      {
        params: params, responseType: 'json'
      }).toPromise();
  }

  getDocuments(browseCategories: BrowseCategories): Observable<any> {
    let httpParams = new HttpParams();

    if (browseCategories.school.length > 0) {
      httpParams = httpParams.set('school', browseCategories.school[0]);
    }

    if (browseCategories.departement.length > 0) {
      httpParams = httpParams.set('departement', browseCategories.departement[0]);
    }

    if (browseCategories.course.length > 0) {
      httpParams = httpParams.set('course', browseCategories.course[0]);
    }

    return this.http.get<any>(baseUrl + "/api/browse",
      {
        params: httpParams,
        observe: 'response' as 'body'
      });
  }

  getCategories() {
    return this.http.get(baseUrl + "/api/categories").toPromise();
  }

  getDocumentDetails(id: string) {
    return this.http.get(baseUrl + "/api/document/" + id).toPromise();
  }

  postComment(documentComment: DocumentComment) {
    let user = this.session.getUser();
    return this.http.post<FormData>(
      `${baseUrl}/api/document/comment?documentid=${documentComment.documentid}&user=${user}&comment=${documentComment.content}`, ''
    ).toPromise();
  }

  postRating(documentReview: DocumentRating) {
    let user = this.session.getUser();
    return this.http.post<FormData>(
      `${baseUrl}/api/document/rating?documentid=${documentReview.documentid}&user=${user}&rating=${documentReview.rating}`, ''
      ).toPromise();
  }

  addDocument(payload : FormData) {
    payload.append('user', this.session.getUser());
    return this.http.post<FormData>(baseUrl + "/api/document", payload)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}
        Error Message:
        ${error.message}`);
      console.log(error);
    }
    // return an ErrorObservable with a user-facing error message
    return new ErrorObservable(
      'Something bad happened. handleError was called.');
  };
}

