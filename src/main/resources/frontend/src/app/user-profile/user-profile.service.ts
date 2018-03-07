import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {User} from "./user";
import {Observable} from "rxjs/Observable";
import 'rxjs/add/operator/retry';
import 'rxjs/add/operator/catch';


@Injectable()
export class UserProfileService {

  baseURL: string = 'http://localhost:8080/users/';

  constructor(private http: HttpClient) { }

  getUser(URL): Observable<User>{
    return this.http.get<User>(URL,{responseType: 'json'});
  }

  getSecurityQuestion(email: string){
    return this.http.get(this.baseURL+email+'/securityQuestion',{responseType: 'text'})
      .retry(0);
  }

}
