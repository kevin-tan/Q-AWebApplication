import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from "@angular/router";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { HttpClientModule } from "@angular/common/http";

import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './login/login.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { RegistrationComponent } from './registration/registration.component';
import { DashboardComponent } from "./dashboard/dashboard.component";
import { QuestionsComponent } from './questions/questions.component';
import { AskingComponent } from './asking/asking.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { LeaderboardComponent } from './leaderboard/leaderboard.component';
import { ForgotPassComponent } from './forgot-pass/forgot-pass.component';

import { LoginRedirectService } from "./login/login-redirect.service";
import { QuestionsService } from "./questions/questions.service";
import { AskingService } from "./asking/asking.service";
import { UserProfileService } from "./user-profile/user-profile.service";

import { EqualValidator } from "./registration/equal-validator.directive";

const appRoutes: Routes =[
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent, canActivate: [LoginRedirectService]},
  {path: 'login/forgotPassword', component: ForgotPassComponent, canActivate: [LoginRedirectService]},
  {path: 'register', component: RegistrationComponent, canActivate: [LoginRedirectService]},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'profile/:userID', component: UserProfileComponent},
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
    UserProfileComponent,
    EqualValidator,
    AskingComponent,
    ForgotPassComponent,
    LeaderboardComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    RouterModule.forRoot(appRoutes,{enableTracing: true})
  ],
  providers: [
    LoginRedirectService,
    QuestionsService,
    AskingService,
    UserProfileService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
