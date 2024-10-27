
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';  // Importiere den Auth-Service
import { HttpErrorResponse } from '@angular/common/http';
import { LoginResponse } from '../models/login-response.model';
import {CommonModule} from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import {FormsModule} from '@angular/forms';
import { HttpClientModule} from '@angular/common/http';
import { RouterModule } from '@angular/router';





@Component({
  selector: 'an-login',
  standalone: true,
  imports: [FormsModule, HttpClientModule],
  providers: [AuthService],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginData = {
    email: '',
    password: ''
  };

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    // Einfache Validierungsprüfung
    if (!this.loginData.email || !this.loginData.password) {
      alert('Bitte alle Felder ausfüllen.');
      return;
    }

    console.log("Login-Daten:", this.loginData); // Protokolliere die Login-Daten

    this.authService.login(this.loginData).subscribe(
      (data: LoginResponse) => {
        if (data.token) {
          localStorage.setItem('authToken', data.token);
          console.log('Login erfolgreich', data);
          this.router.navigate(['/startpage']);  // Weiterleitung zur Startseite
        } else {
          console.log('Login fehlgeschlagen: Kein Token erhalten');
          alert('Login fehlgeschlagen. Bitte überprüfe deine Zugangsdaten.');
        }
      },
      (error: HttpErrorResponse) => {
        console.error('Login fehlgeschlagen:', error);
        alert('Ein Fehler ist aufgetreten. Bitte versuche es erneut.');
      }
    );
  }

  goBack(): void {
    this.router.navigate(['/homepage']);  // Gehe zurück zur Homepage
  }
}
