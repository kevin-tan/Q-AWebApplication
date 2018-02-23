import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";


import {HeaderComponent} from './header/header.component';
import {LoginComponent} from './login/login.component';
import {FooterComponent} from './footer/footer.component';
import {HomeComponent} from './home/home.component';
import {RegistrationComponent} from './registration/registration.component';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {StatusComponent} from './component/status/status.component';
import {QuestionsComponent} from './questions/questions.component';


import {AuthService} from "./login/auth.service";
import {VerifyAuthenticationService} from "./login/verify-authentication.service";
import {LoginRedirectService} from "./login/login-redirect.service";
import {GreetingComponent} from './greeting/greeting.component';
import {QuestionsService} from "./questions/questions.service";
import {HttpModule} from "@angular/http";
import { UserProfileComponent } from './user-profile/user-profile.component';

import { EqualValidator} from "./registration/equal-validator.directive";
import { ForgotPassComponent } from './forgot-pass/forgot-pass.component';

const appRoutes: Routes =[
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent, canActivate: [LoginRedirectService]},
  {path: 'login/forgotPassword', component: ForgotPassComponent},
  {path: 'register', component: RegistrationComponent, canActivate: [LoginRedirectService]},
  {path: 'welcome', component: GreetingComponent, canActivate: [VerifyAuthenticationService]},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'dashboard/question/:id', component: QuestionsComponent, data: {currentQuestion: 'question'}},
  {path: 'profile', component: UserProfileComponent},
  {path: '**', component: HomeComponent}
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
    UserProfileComponent,
    EqualValidator,
    ForgotPassComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    HttpModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    RouterModule.forRoot(appRoutes,{enableTracing: true})
  ],
  providers: [
    AuthService,
    VerifyAuthenticationService,
    LoginRedirectService,
    QuestionsService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
