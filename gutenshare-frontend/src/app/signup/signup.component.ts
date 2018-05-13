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
  signUpFailedAlert: string = 'This username is already taken';
  signUpFailed: boolean = false;
  passwordMismatchAlert: string = 'Passwords are not matching';
  passwordMismatch: boolean = false;
  showMissingInfoAlert = false;
  missingFieldAlert: string = '* This field is required';
  signUpSuccessful: boolean = false;

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

  signUpUser(post) {
    this.signUpSuccessful = false;
    if (!this.signUpForm.valid) {
      this.showMissingInfoAlert = true;
    } else {
      this.showMissingInfoAlert = false;
      if (post.password == post.passwordRepeat) {
        this.passwordMismatch = false;
        delete post.passwordRepeat;
        this.auth.signUpUser(post).subscribe(
          data => {
            this.signUpFailed = false;
            this.signUpSuccessful = true;
          },
          error => {
            this.signUpFailed = true;
          });
      } else {
        this.passwordMismatch = true;
      }
    }
  }

}
