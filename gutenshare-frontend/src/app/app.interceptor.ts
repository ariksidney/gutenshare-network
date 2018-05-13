import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpHandler, HttpInterceptor, HttpRequest, HttpUserEvent} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Router} from '@angular/router';
import {SessionStorage} from './login/session.storage';
import 'rxjs/add/operator/do';
import {environment} from "../environments/environment";


@Injectable()
export class Interceptor implements HttpInterceptor {

  constructor(private session: SessionStorage,
              private router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpUserEvent<any>> {
    let authReq = req;
    if (!this.session.hasToken()) {
      authReq = req.clone({
        headers: req.headers.set(
          'Authorization', 'Basic ' + btoa(`${environment.basicAuthUser}:${environment.basicAuthSecret}`))
      });
    } else {
      authReq = req.clone({headers: req.headers.set('Authorization', 'Bearer ' + this.session.getToken())});
    }
    return next.handle(authReq).do(
      (err: any) => {
        if (err instanceof HttpErrorResponse) {

          if (err.status === 401) {
            this.router.navigate(['/home/login']);
          }
          if (err.status === 409) {
            this.router.navigate(['/home/signup']);
          }
        }
      }
    );
  }

}
