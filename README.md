# Obuoliukų Žaidimas

<div align="center">
  <p><strong>Interaktyvus JavaFX žaidimas - surink krintančius obuolius!</strong></p>
  
  [![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
  [![JavaFX](https://img.shields.io/badge/JavaFX-17+-blue.svg)](https://openjfx.io/)
  [![Platform](https://img.shields.io/badge/Platform-Cross--Platform-green.svg)](https://www.java.com/)
  [![Game](https://img.shields.io/badge/Genre-Arcade-red.svg)](https://en.wikipedia.org/wiki/Arcade_game)
</div>

## Apie žaidimą

Paprastas ir įtraukiantis arkadinis žaidimas, sukurtas JavaFX platformoje. Žaidėjo tikslas - sugauti krintančius obuolius į krepšį, vengt jų praleidimo ir surinkti kuo daugiau taškų. Žaidimas turi gražų vizualinį dizainą, garso efektus ir sklandžią animaciją.

## Žaidimo nuotraukos

<div align="center">
  <img src="https://res.cloudinary.com/dknalmer3/image/upload/v1749507492/Screenshot_2025-06-10_011810_xb1djq.png" alt="Pagrindinis langas" width="250"/>
  <img src="https://res.cloudinary.com/dknalmer3/image/upload/v1749507511/Screenshot_2025-06-10_011827_k9p7jr.png" alt="Žaidimas" width="250"/>
</div>

### Valdymas:

- **←/A** - judinti krepšį į kairę
- **→/D** - judinti krepšį į dešinę

### Žaidimo taisyklės:

1. Obuoliai krenta iš viršaus atsitiktinėse vietose
2. Sugaukite obuolį krepšiu - gaukite +1 tašką
3. Praleiskite obuolį - praraskite -1 tašką
4. Žaidimas baigiasi, kai taškai tampa neigiami (< 0)

### Run Configurations

```bash
--module-path "/path/to/javafx/lib" --add-modules javafx.controls,javafx.fxml,javafx.media
```

- **Rekordų sistema** - geriausių rezultatų išsaugojimas
- **Lygių sistema** - skirtingi sunkumo lygiai
- **Papildomi objektai** - kiti vaisiai, bonusai
- **Specialūs efektai** - partiklių sistema
- **Mobilaus valdymo palaikymas** - touch/swipe valdymas
- **Daugiažaidėjų režimas** - konkurencija tarp žaidėjų
- **Pasiekimų sistema** - specialūs uždaviniai

## Licencija

Šis projektas yra licencijuotas pagal MIT licenciją. Laisvai naudokite, keiskite ir platinkite.

## Kontaktai

- El. paštas: chmieliauskas7@gmail.com
