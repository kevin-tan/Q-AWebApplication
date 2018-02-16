import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IDashboard } from './dashboard'
import { Observable } from 'rxjs/Observable';

@Injectable()
export class DashboardService {

    private _getDashboardURL: string = "http://localhost:8080/questions";

    constructor(private http: HttpClient) {}

    getDashboard(): Observable<IDashboard[]> {
        return this.http.get<IDashboard[]>(this._getDashboardURL);
    }
}