import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, provideHttpClient, withFetch } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { RouterOutlet } from '@angular/router';
import { RouterLink } from '@angular/router';
import { RouterLinkActive } from '@angular/router';


import { AppRoutingModule } from './app.routes';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './homepage/homepage.component';
import { AuthService } from './services/auth.service';
import { RegisterComponent } from './register/register.component';
import { StartpageComponent } from './startpage/startpage.component';


@NgModule({
    declarations: [
      AppComponent,
      HomeComponent,
      LoginComponent,
      RegisterComponent,
      StartpageComponent


    ],
    imports: [
      BrowserModule,
      RouterModule,
      FormsModule,
      HttpClientModule,
      ReactiveFormsModule,
      CommonModule,
      AppRoutingModule,
      LoginComponent,
      RegisterComponent,
      RouterLinkActive,
      RouterOutlet,
      RouterLink
      // andere Module hier...
    ],
    providers: [AuthService, provideHttpClient(withFetch())],
    bootstrap: [AppComponent]
  })
  export class AppModule { }

