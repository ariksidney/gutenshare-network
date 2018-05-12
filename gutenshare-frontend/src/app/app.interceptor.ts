import { Injectable } from '@angular/core';
import {HttpInterceptor, HttpRequest, HttpHandler, HttpSentEvent, HttpHeaderResponse, HttpProgressEvent,
  HttpResponse, HttpUserEvent, HttpErrorResponse} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import {SessionStorage} from './login/session.storage';
import 'rxjs/add/operator/do';

// todo: set keys below
const AUTH_HEADER_KEY: string = '';
const GUTENSHARE_AUTH_USER: string = '';
const GUTENSHARE_AUTH_SECRET: string = '';

@Injectable()
export class Interceptor implements HttpInterceptor {

  constructor(private session: SessionStorage,
              private router: Router) {
  }

  // intercept(req: HttpRequest<any>, next: HttpHandler): Observable<any> {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpUserEvent<any>> {
    let authReq = req;
    if (!this.session.hasToken()) {
      authReq = req.clone({ headers: req.headers.set(
          AUTH_HEADER_KEY, 'Basic ' + btoa(`${GUTENSHARE_AUTH_USER}:${GUTENSHARE_AUTH_SECRET}`))});
    } else {
      authReq = req.clone({ headers: req.headers.set(AUTH_HEADER_KEY, 'Bearer ' + this.session.getToken())});
    }
    return next.handle(authReq).do(
      (err: any) => {
        if (err instanceof HttpErrorResponse) {

          if (err.status === 401) {
            this.router.navigate(['user']); // todo: reroute where?
          }
        }
      }
    );
  }

}
