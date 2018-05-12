import {Component, OnInit} from '@angular/core';
import {ApiService} from "./api/api.service";
import {SessionStorage} from "./login/session.storage";
import {TransferService} from "./transfer.service";
import {AuthService} from "./login/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent implements OnInit {

  title: string = 'gutenshare network';
  username: string;
  isLoggedIn: boolean = false;

  constructor(private session: SessionStorage,
              private transfer: TransferService,
              private auth: AuthService) {
    this.transfer.userEmitted$.subscribe(
      username => {
        this.username = username;
        this.isLoggedIn = true;
      });
  }

  ngOnInit() {
    this.isLoggedIn = this.auth.isLoggedIn();
  }

  logout() {
    this.session.logout();
    this.username = null;
    this.isLoggedIn = false;
  }

}
