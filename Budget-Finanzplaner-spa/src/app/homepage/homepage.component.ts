import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { RouterLink } from '@angular/router';
import { RouterLinkActive } from '@angular/router';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'an-home',
  standalone: true,
   imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router, @Inject(PLATFORM_ID) private platformId: Object) {}

  ngOnInit(): void {
    // Prüfe, ob der Code im Browser läuft, bevor du auf `document` zugreifst
    if (isPlatformBrowser(this.platformId)) {
      const hamburger = document.getElementById('hamburger');
      const navLinks = document.getElementById('navLinks');

      if (hamburger && navLinks) {
        hamburger.addEventListener('click', () => {
          console.log('Hamburger clicked'); // Debugging
          navLinks?.classList.toggle('active'); // Toggle-Klasse hinzufügen/entfernen
        });
      } else {
        console.error('Hamburger oder NavLinks nicht gefunden');
      }
    }
  }
}
