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
import { AskingComponent } from './asking/asking.component';



import {AuthService} from "./login/auth.service";
import {VerifyAuthenticationService} from "./login/verify-authentication.service";
import {LoginRedirectService} from "./login/login-redirect.service";
import {GreetingComponent} from './greeting/greeting.component';
import {QuestionsService} from "./questions/questions.service";
import {AskingService} from "./asking/asking.service";
import {HttpModule} from "@angular/http";
import { UserProfileComponent } from './user-profile/user-profile.component';

import { EqualValidator} from "./registration/equal-validator.directive";
import { ForgotPassComponent } from './forgot-pass/forgot-pass.component';
import {UserProfileService} from "./user-profile/user-profile.service";
import {LeaderboardComponent} from './leaderboard/leaderboard.component';
import { EditingComponent } from './editing/editing.component';

const appRoutes: Routes =[
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent, canActivate: [LoginRedirectService]},
  {path: 'login/forgotPassword', component: ForgotPassComponent},
  {path: 'register', component: RegistrationComponent, canActivate: [LoginRedirectService]},
  {path: 'welcome', component: GreetingComponent, canActivate: [VerifyAuthenticationService]},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'profile', component: UserProfileComponent},
  {path: 'leaderboard', component: LeaderboardComponent},
  {path: 'dashboard/asking', component: AskingComponent},
  {path: 'dashboard/question/:id', component: QuestionsComponent},
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
    AskingComponent,
    ForgotPassComponent,
    LeaderboardComponent,
    EditingComponent
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
    AskingService,
    UserProfileService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
