# APM Woche 9: Garbage Collection Tuning


[Vorlesungsfolien](w09_gc_tuning.pdf)

[Notizen](w09_gc_tuning_notes_scan.pdf)

## Übungen

### Übung 1: Selbstudium GC-Implementierungen Java HotSpot

Lesen Sie Kapitel 4 des Skriptes durch (paralleler und nebenläufiger GC).

### Übung 2: GC Tuning

Schritt 1: Installieren Sie Java HotSpot 8 (z.B. Oracle JDK, Amazon Corretto).

Schritt 2: Kopieren Sie das File [gcviewer-1.35.jar](gcviewer-1.35.jar) an einen Ort Ihrer Wahl. Sie können das Jar-File mit 

    java -jar gcviewer-1.35.jar

starten, es sind keine weiteren Installationen nötig.

Schritt 3: Arbeiten Sie die Dokumente [GC Tuning Durchsatz](GCTuning/doc/Instructions/GCTuning-1.Throughput.html) und [GC Tuning Reaktionsfähigkeit](GCTuning/doc/Instructions/GCTuning-2.PauseTime.html) der Reihe nach durch. Beantworten Sie die Fragen am Ende der Dokumente. Die Resultate Ihrer Experimente können Sie in der Datei [GCTuning/data/tuning-results.xls](GCTuning/data/tuning-results.xls) erfassen.

### Quellenangabe

Die Übubng GC Tuning wurde von Angelika Langer and Klaus Kreft übernommen.