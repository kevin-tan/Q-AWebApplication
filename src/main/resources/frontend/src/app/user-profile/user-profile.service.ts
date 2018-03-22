import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {User} from "./user";
import {Observable} from "rxjs/Observable";
import 'rxjs/add/operator/retry';
import 'rxjs/add/operator/catch';


@Injectable()
export class UserProfileService {

  baseURL: string = 'http://localhost:8080/';
  userURL: string = 'http://localhost:8080/users/';

  constructor(private http: HttpClient) { }

  register(user: User): Observable<User>{
    return this.http.post<User>(this.baseURL+'register', user);
  }
  login(user: User): Observable<User>{
    return this.http.post<User>(this.baseURL+'login',user);
  }
  getUser(URL): Observable<User>{
    return this.http.get<User>(URL,{responseType: 'json'});
  }
  getSecurityQuestion(email: string){
    return this.http.get(this.userURL+email+'/securityQuestion',{responseType: 'text'})
  }
  postSecurityAnswer(user: User): Observable<User>{
    return this.http.put<User>('http://localhost:8080/login/resetPassword', user);
  }
  changeUserInfo(id: string, user: User){
    return this.http.put(this.userURL+id+'/changeInfo',user);
  }
  changePassword(id: string, user: User){
    return this.http.put(this.userURL+id+'/changePassword',user);
  }
  changeUsername(id: string, user: string) {
    return this.http.put(this.userURL + id + '/changeUsername', user);
  }
  changeEmail(id: string, user: User) {
    return this.http.put(this.userURL + id + '/changeEmail', user);
  }
  changeFirstName(id: string, user: User) {
    return this.http.put(this.userURL + id + '/changeFirstName', user);
  }
  changeLastName(id: string, user: User) {
    return this.http.put(this.userURL + id + '/changeLastName', user);
  }
}
