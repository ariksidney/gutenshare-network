import { Injectable } from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {ErrorObservable} from "rxjs/observable/ErrorObservable";

@Injectable()
export class AuthService {

  constructor(private http: HttpClient) { }

  authenticateUser(username: string, password: string): Observable<any> {
    let payload = new FormData();
    payload.append('username', username);
    payload.append('password', password);
    payload.append('grant_type', 'password');
    // return this.http.post('/toilet/post', payload)
    return this.http.post('/oauth/token', payload)
      .pipe(
        catchError(this.handleError)
      );
  }

  signUpUser(payload): Observable<any> {
    // return this.http.post('/toilet/post', payload)
    return this.http.post('/login/register', payload)
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
