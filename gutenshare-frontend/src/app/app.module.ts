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

import { DocumentService } from './create-document/document.service'
import { UserService } from './user/user.service';

import { TextfilterPipe } from './pipes/textfilter.pipe';

import { AppRoutingModule } from "./app-routing.module";


@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    CreateDocumentComponent,
    TextfilterPipe,
    HomeComponent,
    LoginComponent,
    SignupComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [ UserService, DocumentService ],
  bootstrap: [ AppComponent ]
})

export class AppModule {
}
