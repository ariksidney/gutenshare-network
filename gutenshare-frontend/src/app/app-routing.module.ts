import { NgModule }              from '@angular/core';
import { RouterModule, Routes }  from '@angular/router';

import { HomeComponent } from "./home/home.component";
import { CreateDocumentComponent } from "./create-document/create-document.component";
import { UserComponent } from "./user/user.component";
import { LoginComponent } from "./login/login.component";
import { SignupComponent } from "./signup/signup.component";


const appRoutes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    children: [
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'signup',
        component: SignupComponent
      },
      {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
      },
    ]
  },
  { path: 'upload', component: CreateDocumentComponent },
  { path: 'profile', component: UserComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', component: HomeComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    )
  ],
  exports: [
    RouterModule
  ]
})

export class AppRoutingModule {
}
