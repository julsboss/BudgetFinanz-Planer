import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginResponse } from '../models/login-response.model'; // Importiere dein LoginResponse-Interface

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/auth/login';
   private registerUrl = 'https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/auth/sign-up'; // Registrierung-URL


  constructor(private http: HttpClient) { }

  login(data: { email: string, password: string }): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.apiUrl, data);
  }

register(data: { firstName: string, lastName: string, email: string, password: string }): Observable<any> {
    return this.http.post<any>(this.registerUrl, data);
  }
}
