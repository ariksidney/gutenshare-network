import { Injectable } from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {ErrorObservable} from "rxjs/observable/ErrorObservable";
import {JwtHelperService} from "@auth0/angular-jwt";
import {CanActivate} from "@angular/router";

@Injectable()
export class AuthService implements CanActivate {

  private jwtHelper: any;

  constructor(private http: HttpClient
              // ,private jwtHelper: JwtHelperService) {}
  ){
    this.jwtHelper = new JwtHelperService();
  }

  canActivate() {
    if (this.isLoggedIn()) {
      return true;
    } else {
      window.alert("You don't have permission to view this page");
      return false;
    }
  }

  isLoggedIn() {
    if (sessionStorage.getItem('AuthToken')) {
      return !this.jwtHelper.isTokenExpired(this.getToken());
    }
    return false;
  }

  getToken(): any {
    return JSON.parse(sessionStorage.getItem('AuthToken'))['access_token'];
  }

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

  // isAuthenticated(): void {
  //   const token = localStorage.getItem('AuthToken');
  //   console.log(token);
  //   console.log('isExpired?');
  //   console.log(this.jwtHelper.isTokenExpired(token));
  //   // return !this.jwtHelper.isTokenExpired(token);
  // }

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
