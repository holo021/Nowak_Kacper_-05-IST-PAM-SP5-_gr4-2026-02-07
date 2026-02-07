# FotoMapa - Projekt Semestralny

Witaj w repozytorium projektu **FotoMapa**. Jest to aplikacja mobilna na Androida, którą stworzyłam w ramach przedmiotu Programowanie aplikacji mobilnych. Jej głównym zadaniem jest tworzenie dokumentacji terenowej – pozwala robić zdjęcia i automatycznie przypisywać do nich dokładną lokalizację GPS.

## O co chodzi w tej aplikacji?

Głównym celem było stworzenie narzędzia, które łączy fotografię z danymi geograficznymi. Dzięki temu użytkownik nie tylko ma zdjęcie danego miejsca, ale też wie dokładnie, gdzie i kiedy zostało wykonane.

Kluczowe funkcje, które udało mi się zaimplementować:
* **Robienie zdjęć** – bezpośrednio z poziomu aplikacji, bez konieczności wychodzenia do systemowego aparatu i ręcznego importowania plików.
* **Geotagowanie** – w momencie robienia zdjęcia aplikacja pobiera współrzędne z GPS i trwale łączy je z fotografią.
* **Trwały zapis** – wszystkie dane (lista zdjęć, opisy, współrzędne) są zapisywane w pamięci telefonu. Dzięki temu po restarcie aplikacji nic nie ginie.
* **Przeglądanie historii** – prosty i czytelny podgląd wszystkich wykonanych pomiarów.
* **Integracja z mapami** – jednym kliknięciem można otworzyć lokalizację zdjęcia w zewnętrznej aplikacji, np. Google Maps, żeby zobaczyć kontekst terenu.

## Jak to działa "pod maską"? (Sensory i Dane)

Zgodnie z wymaganiami projektowymi, aplikacja korzysta z trzech głównych modułów telefonu:

1.  **Aparat** – do pozyskiwania zdjęć. Wykorzystałam tutaj mechanizm `FileProvider`, co jest bezpiecznym i nowoczesnym sposobem na obsługę plików w Androidzie.
2.  **GPS (Lokalizacja)** – używam biblioteki `FusedLocationProviderClient`, która jest standardem w pobieraniu precyzyjnych danych lokalizacyjnych.
3.  **Pamięć urządzenia** – dane o zdjęciach nie są trzymane tylko w pamięci RAM, ale są serializowane do formatu JSON (przy użyciu biblioteki Gson) i zapisywane w pliku. Dzięki temu baza danych jest trwała.

## Technologie

Całość została napisana w języku **Kotlin**. Interfejs użytkownika oparłam na **Jetpack Compose** – to nowoczesne podejście do budowania UI w Androidzie, które zastępuje stare pliki XML.

Architektura projektu to **MVVM (Model-View-ViewModel)**. Dzięki temu logika biznesowa (np. zapisywanie plików) jest oddzielona od widoku (tego, co widać na ekranie), co sprawia, że kod jest czystszy i łatwiejszy w utrzymaniu.

Użyte biblioteki:
* **Coil** – do szybkiego ładowania zdjęć.
* **Gson** – do zapisu danych.
* **Play Services Location** – do obsługi GPS.
* **Navigation Compose** – do przechodzenia między ekranami.

## Zrzuty ekranu

Poniżej możesz zobaczyć, jak aplikacja wygląda w działaniu:

| Ekran Startowy | Uprawnienia | Lista Zdjęć |
|:---:|:---:|:---:|
| <img src="screenshots/Screenshot_1.png" width="200"/> | <img src="screenshots/Screenshot_2.png" width="200"/> | <img src="screenshots/Screenshot_3.png" width="200"/> |

| Szczegóły Zdjęcia | Widok Mapy |
|:---:|:---:|
| <img src="screenshots/Screenshot_5.png" width="200"/> | <img src="screenshots/Screenshot_4.png" width="200"/> |

*(Aby obrazki się wyświetlały, pamiętaj o wrzuceniu plików png do folderu screenshots w projekcie)*

## Jak uruchomić projekt?

Jeśli chcesz przetestować aplikację u siebie:

1.  Upewnij się, że masz zainstalowane **Android Studio** i Javę (JDK 17+).
2.  Pobierz ten projekt (git clone lub zip) i otwórz w Android Studio.
3.  Poczekaj chwilę, aż Gradle pobierze wszystkie zależności.
4.  Podłącz telefon (zalecane, bo emulator ciężko symuluje aparat i GPS) lub uruchom emulator.
5.  Kliknij "Run".

Ważna uwaga: Przy pierwszym uruchomieniu aplikacja poprosi o dostępy (Kamera, Lokalizacja). Są one niezbędne do działania głównych funkcji.

---
**Autor:** Monika Guziwelakis
