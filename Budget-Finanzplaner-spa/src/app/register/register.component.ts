import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { RouterLink } from '@angular/router';
import { RouterLinkActive } from '@angular/router';




@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, HttpClientModule, CommonModule,RouterOutlet, RouterLink, RouterLinkActive],
  providers: [AuthService],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registrationData = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: '',
    acceptTerms: false
  };

  constructor(private authService: AuthService, private router: Router) {}

  goBack(): void {
    this.router.navigate(['/homepage']);
  }

  onSubmit(): void {
    if (
      !this.registrationData.firstName ||
      !this.registrationData.lastName ||
      !this.registrationData.email ||
      !this.registrationData.password ||
      !this.registrationData.confirmPassword ||
      !this.registrationData.acceptTerms
    ) {
      alert('Bitte alle Felder ausfüllen und AGB akzeptieren.');
      return;
    }

    if (this.registrationData.password !== this.registrationData.confirmPassword) {
      alert('Die Passwörter stimmen nicht überein.');
      return;
    }

    this.authService.register(this.registrationData).subscribe(
      (response: any) => {
        alert('Registrierung erfolgreich!');
        this.router.navigate(['/login']);
      },
      (error: HttpErrorResponse) => {
        console.error('Fehler:', error);
        alert('Registrierung fehlgeschlagen. Bitte erneut versuchen.');
      }
    );
  }
}
