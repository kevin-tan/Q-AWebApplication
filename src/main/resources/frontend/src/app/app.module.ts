import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes} from "@angular/router";


import { AppComponent } from './app.component';
import { QuestionsComponent } from './questions/questions.component';
import { HttpClientModule} from "@angular/common/http";
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './login/login.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { RegistrationComponent } from './registration/registration.component';

const appRoutes: Routes =[
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent}

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
    RouterModule.forRoot(appRoutes,{enableTracing: true})
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
