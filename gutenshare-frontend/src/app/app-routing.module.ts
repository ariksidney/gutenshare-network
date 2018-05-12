import { NgModule }              from '@angular/core';
import { RouterModule, Routes }  from '@angular/router';

import { HomeComponent } from "./home/home.component";
import { CreateDocumentComponent } from "./create-document/create-document.component";
import { LoginComponent } from "./login/login.component";
import { SignupComponent } from "./signup/signup.component";
import { ProfileComponent } from "./profile/profile.component";
import { SettingsComponent } from "./settings/settings.component";
import { DocumentBrowserComponent } from "./document-browser/document-browser.component";
import {DocumentDetailComponent} from "./document-detail/document-detail.component";
import {AuthService} from "./login/auth.service";


const appRoutes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  {
    path: 'home',
    component: HomeComponent,
    children: [
      {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
      },
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'signup',
        component: SignupComponent
      }
    ]
  },
  {
    path: 'upload',
    component: CreateDocumentComponent,
    canActivate: [ AuthService ]
  },
  { path: 'profile', component: ProfileComponent },
  { path: 'settings', component: SettingsComponent },
  {
    path: 'browse',
    component: DocumentBrowserComponent,
    canActivate: [ AuthService ]
  },
  {
    path: 'browse/:query',
    component: DocumentBrowserComponent,
    canActivate: [ AuthService ]
  },
  {
    path: 'detail/:id',
    component: DocumentDetailComponent,
    canActivate: [ AuthService ]
  },
  { path: '**', redirectTo: 'home' }
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      appRoutes
      // { enableTracing: true } // <-- debugging purposes only
    )
  ],
  exports: [
    RouterModule
  ]
})

export class AppRoutingModule {
}
