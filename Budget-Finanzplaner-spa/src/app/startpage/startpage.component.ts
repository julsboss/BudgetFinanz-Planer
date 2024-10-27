
import { Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';
import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { RouterLink } from '@angular/router';
import { RouterLinkActive } from '@angular/router';
import {CommonModule} from '@angular/common';


@Component({
  selector: 'an-startpage',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './startpage.component.html',
  styleUrls: ['./startpage.component.css']
})
export class StartpageComponent implements OnInit {
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


  onLocationChange(event: Event): void {
    const input = event.target as HTMLInputElement; // Typ-Assertion
    const location = encodeURIComponent(input.value); // Holen des Wertes
    const map = document.getElementById('map') as HTMLIFrameElement;
    // Stelle sicher, dass map nicht null ist
        if (map) {
          map.src = `https://www.google.com/maps?q=${location}+Geldautomat&output=embed`; // Korrektur hier
        } else {
          console.error('Map-Element nicht gefunden');
        }
  }

}

