import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { JwtModule } from '@auth0/angular-jwt';

import { AppComponent } from './app.component';
import { UserComponent } from "./user/user.component";
import { CreateDocumentComponent } from './create-document/create-document.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { ProfileComponent } from './profile/profile.component';
import { SettingsComponent } from './settings/settings.component';

import { UserService } from './user/user.service';

import { TextfilterPipe } from './pipes/textfilter.pipe';
import { ExtractDepartmentPipe } from "./pipes/extract-departments.pipe";
import { ExtractCoursesPipe } from "./pipes/extract-courses.pipe";

import { AppRoutingModule } from "./app-routing.module";
import { DocumentBrowserComponent } from './document-browser/document-browser.component';
import {AuthService} from "./login/auth.service";
import { Interceptor } from './app.interceptor';
import {SessionStorage} from "./login/session.storage";
import { DocumentDetailComponent } from "./document-detail/document-detail.component";
import { ApiService } from "./api/api.service";
import { DocumentsResultComponent } from "./documents-result/documents-result.component";
import { DocumentSearchComponent } from "./document-search/document-search.component";


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
    DocumentDetailComponent,
    DocumentsResultComponent,
    DocumentSearchComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [
    ApiService,
    UserService,
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: Interceptor,
      multi: true
    },
    SessionStorage
  ],
  bootstrap: [ AppComponent ]
})

export class AppModule {
}
