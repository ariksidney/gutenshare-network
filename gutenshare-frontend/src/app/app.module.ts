import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

import { AppComponent } from './app.component';
import { UserComponent } from "./user/user.component";
import { CreateDocumentComponent } from './create-document/create-document.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { ProfileComponent } from './profile/profile.component';
import { SettingsComponent } from './settings/settings.component';

import { DocumentService } from './create-document/document.service'
import { UserService } from './user/user.service';

import { TextfilterPipe } from './pipes/textfilter.pipe';
import { ExtractDepartmentPipe } from "./pipes/extract-departments.pipe";
import { ExtractCoursesPipe } from "./pipes/extract-courses.pipe";

import { AppRoutingModule } from "./app-routing.module";
import { DocumentBrowserComponent } from './document-browser/document-browser.component';
import {DocumentDetailComponent} from "./document-detail/document-detail.component";
import {DocumentBrowserService} from "./document-browser/document-browser.service";
import {ApiService} from "./api/api.service";

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    CreateDocumentComponent,
    TextfilterPipe,
    ExtractDepartmentPipe,
    ExtractCoursesPipe,
    HomeComponent,
    LoginComponent,
    SignupComponent,
    ProfileComponent,
    SettingsComponent,
    DocumentBrowserComponent,
    DocumentDetailComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [ UserService, DocumentService, DocumentBrowserService, ApiService],
  bootstrap: [ AppComponent ]
})

export class AppModule {
}
