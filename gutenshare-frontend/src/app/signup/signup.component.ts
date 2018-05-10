import { Component, OnInit } from '@angular/core';
import {AuthService} from "../login/auth.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  constructor(
    private auth: AuthService,
    private fb: FormBuilder
  ) {
    this.initializeForm();
  }

  signUpForm: FormGroup;
  signUpFailedAlert: string = 'something went wrong';
  signUpFailed = false;

  ngOnInit() {
  }

  initializeForm():void {
    this.signUpForm = this.fb.group({
      username : [null, Validators.required],
      firstname : [null, Validators.required],
      lastname : [null, Validators.required],
      mail : [null, Validators.required],
      password : [null, Validators.required],
      passwordRepeat : [null, Validators.required],
    });
  }

  // loginUser(post) {
  //   this.auth.authenticateUser(post.username, post.password).subscribe(
  //     data => {
  //       this.loginFailed = false;
  //       window.sessionStorage.setItem('username', post.username);
  //       this.session.saveToken(data);
  //       this.onUserLogin.emit(post.username);
  //     },
  //     error => {
  //       console.log(error);
  //       this.loginFailed = true;
  //       window.sessionStorage.removeItem('username');
  //       this.onUserLogin.emit(null);
  //     });
  // }

  signUpUser(post) {
    if (post.password == post.passwordRepeat) {
      delete post.passwordRepeat;
      this.auth.signUpUser(post).subscribe(
        data => {
          console.log(data);
        },
        error => {
          console.log(error);
        });
    } else {
      // throw error
    }
  }

}
