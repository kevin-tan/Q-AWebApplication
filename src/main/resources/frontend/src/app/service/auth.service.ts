import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import {UserModel} from "../model/UserModel";

@Injectable()
export class AuthService {

  private BaseUrl: string = 'http://localhost:8080/'
  private headers: Headers = new Headers({'Content-Type': 'application/json'});
  constructor(private http: Http) { }

  login(user: UserModel): Promise<any>{
    let url: string = `${this.BaseUrl}/login`;
    return this.http.post(url,user,{headers: this.headers}).toPromise();
  }

  register(user: UserModel): Promise<any>{
    let url: string = `${this.BaseUrl}/register`;
    return this.http.post(url,user,{headers: this.headers}).toPromise();
  }

}
