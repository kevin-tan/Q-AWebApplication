import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes} from "@angular/router";
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';


import { AppComponent } from './app.component';
import { QuestionsComponent } from './component/questions/questions.component';
import { HttpClientModule} from "@angular/common/http";
import { HeaderComponent } from './component/header/header.component';
import { LoginComponent } from './component/login/login.component';
import { FooterComponent } from './component/footer/footer.component';
import { HomeComponent } from './component/home/home.component';
import { RegistrationComponent } from './component/registration/registration.component';
import {AuthService} from "./service/auth.service";

const appRoutes: Routes =[
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegistrationComponent}

];


@NgModule({
  declarations: [
    AppComponent,
    QuestionsComponent,
    HeaderComponent,
    LoginComponent,
    FooterComponent,
    HomeComponent,
    RegistrationComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(appRoutes,{enableTracing: true})
  ],
  providers: [AuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }
