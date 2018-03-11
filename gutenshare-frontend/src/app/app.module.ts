import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";


import { AppComponent } from './app.component';
import { UserComponent } from "./user/user.component";

import { UserService } from './user/user.service';
import {CreateDocumentComponent} from './create-document/create-document.component';
import { DocumentService } from './create-document/document.service'

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    CreateDocumentComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [UserService, DocumentService],
  bootstrap: [AppComponent]
})
export class AppModule { }
