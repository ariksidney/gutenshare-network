import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';


@Injectable()
export class SessionStorage {

  constructor() {
  }

  logout() {
    sessionStorage.clear();
  }

  public saveToken(token: string) {
    sessionStorage.removeItem(environment.ssTokenKey);
    sessionStorage.setItem(environment.ssTokenKey, JSON.stringify(token));
  }

  public getToken(): string {
    return JSON.parse(sessionStorage.getItem(environment.ssTokenKey))['access_token'];
  }

  public hasToken(): boolean {
    if (sessionStorage.getItem(environment.ssTokenKey)) {
      return true;
    }
    return false;
  }

  public getUser(): string {
    return sessionStorage.getItem(environment.ssUsername);
  }

}
