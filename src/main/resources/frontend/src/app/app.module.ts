import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes} from "@angular/router";
import { HttpModule } from '@angular/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AppComponent } from './app.component';
import { HttpClientModule} from "@angular/common/http";


import { HeaderComponent } from './component/header/header.component';
import { LoginComponent } from './component/login/login.component';
import { FooterComponent } from './component/footer/footer.component';
import { HomeComponent } from './component/home/home.component';
import { RegistrationComponent } from './component/registration/registration.component';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { StatusComponent } from './component/status/status.component';
import { QuestionsComponent } from './component/questions/questions.component';


import {AuthService} from "./service/auth.service";
import {VerifyAuthenticationService} from "./service/verify-authentication.service";
import {LoginRedirectService} from "./service/login-redirect.service";
import { GreetingComponent } from './component/greeting/greeting.component';
import {DashboardService} from "./dashboard.service";

const appRoutes: Routes =[
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent, canActivate: [LoginRedirectService]},
  {path: 'register', component: RegistrationComponent, canActivate: [LoginRedirectService]},
  {path: 'welcome', component: GreetingComponent, canActivate: [VerifyAuthenticationService]},
  {path: 'dashboard', component: DashboardComponent}
];


@NgModule({
  declarations: [
    AppComponent,
    QuestionsComponent,
    HeaderComponent,
    LoginComponent,
    FooterComponent,
    HomeComponent,
    RegistrationComponent,
    DashboardComponent,
    StatusComponent,
    GreetingComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    RouterModule.forRoot(appRoutes,{enableTracing: true})
  ],
  providers: [
    AuthService,
    VerifyAuthenticationService,
    LoginRedirectService,
    DashboardService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
