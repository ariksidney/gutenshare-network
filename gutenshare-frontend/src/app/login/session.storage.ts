import { Injectable } from '@angular/core';


const TOKEN_KEY = 'AuthToken';
const USERNAME = 'username';

@Injectable()
export class SessionStorage {

  constructor() { }

  logout() {
    window.sessionStorage.clear();
  }

  public saveToken(token: string) {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, JSON.stringify(token));
  }

  public getToken(): string {
    return JSON.parse(window.sessionStorage.getItem(TOKEN_KEY))['access_token'];
  }

  public hasToken(): boolean {
    if (window.sessionStorage.getItem(TOKEN_KEY)) {
      return true;
    }
    return false;
  }

  public getUser(): string {
    return window.sessionStorage.getItem(USERNAME);
  }

}
