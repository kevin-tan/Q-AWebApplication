import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {User} from "./user";
import {Observable} from "rxjs/Observable";

@Injectable()
export class UserProfileService {

  constructor(private http: HttpClient) { }

  getUser(URL): Observable<User>{
    return this.http.get<User>(URL,{responseType: 'json'});
  }

}
