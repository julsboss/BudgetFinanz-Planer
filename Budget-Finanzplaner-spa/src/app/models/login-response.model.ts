// src/app/models/login-response.model.ts
export interface LoginResponse {
  token: string;             // Erforderliches Token für die Authentifizierung
  userId?: string;          // Optionale Benutzer-ID (falls vorhanden)
  //username?: string;        // Optionaler Benutzername (falls vorhanden)
}
