import { Component } from '@angular/core';
import {ApiService} from "./api/api.service";
import {SessionStorage} from "./login/session.storage";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent {

  title: string = 'gutenshare network';
  username: string;

  constructor(private session: SessionStorage) {
    this.username = window.sessionStorage.getItem('username');
  }

  logout() {
    this.session.logout();
  }

  onUserLogin(username: string) {
    this.username = username;
    console.log('propagiert...');
  }


}
