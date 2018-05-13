import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AuthService} from "./auth.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SessionStorage} from "./session.storage";
import {Router} from "@angular/router";
import {TransferService} from "../transfer.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(
    private auth: AuthService,
    private session: SessionStorage,
    private fb: FormBuilder,
    private router: Router,
    private transfer: TransferService
  ) {
    this.initializeForm();
  }

  @Output() onUserLogin = new EventEmitter<string>();

  loginForm: FormGroup;
  loginFailedAlert: string = 'Invalid user credentials';
  loginFailed = false;

  ngOnInit() {
  }

  initializeForm():void {
    this.loginForm = this.fb.group({
      username : [null, Validators.required],
      password : [null, Validators.required],
    });
  }

  loginUser(post) {
    this.auth.authenticateUser(post.username, post.password).subscribe(
      data => {
        this.loginFailed = false;
        window.sessionStorage.setItem('username', post.username);
        this.session.saveToken(data);
        this.transfer.emitUsername(post.username);
        this.router.navigate(['browse']);
      },
      error => {
        console.log(error);
        this.loginFailed = true;
        window.sessionStorage.removeItem('username');
        this.onUserLogin.emit(null);
      });
  }

}
