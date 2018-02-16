import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Question } from './questions/question'
import { Observable } from 'rxjs/Observable';

@Injectable()
export class DashboardService {

    private _getDashboardURL: string = "http://localhost:8080/questions";

    constructor(private http: HttpClient) {}

    getDashboard(): Observable<Question[]> {
        return this.http.get<Question[]>(this._getDashboardURL);
    }
}