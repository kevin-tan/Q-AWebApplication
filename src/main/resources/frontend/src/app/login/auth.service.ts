import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import 'rxjs/add/operator/toPromise';
import {UserModel} from "../UserModel";

@Injectable()
export class AuthService {

  private BaseUrl: string = 'http://localhost:8080/'
  constructor(private http: Http) { }

  login(user: UserModel): Promise<any>{
    let url: string = `${this.BaseUrl}/login`;
    return this.http.post(url, user).toPromise();
  }

  register(user: UserModel): Promise<any>{
    let url: string = `${this.BaseUrl}/register`;
    return this.http.post(url, user).toPromise();
  }

}
