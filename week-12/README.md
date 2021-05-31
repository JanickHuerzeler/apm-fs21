# APM Woche 12: Java NIO


[Vorlesungsfolien](w12_nio.pdf)

## Übung

Der Ordner `code` enthält einige Beispielprogramme die Ihnen die Funktionsweise der nio-Klassen verdeutlichen helfen. Darunter sind auch die im Skript erwähnten `CopyFile.java` Programm.

Versuchen Sie mit Hilfe von der Java API Dokumentation (Package java.nio und Subpackages) folgende Fragen zu beantworten:

1. Neben `position`, `limit` und `capacity` gibt es noch die Variable `mark`. Wozu dient sie und wie wird sie manipuliert? (Klasse `Buffer`)
2. Was versteht man unter _gathering_? (`GatheringByteChannel`)
3. Was versteht man unter _scattering_? (`ScatteringByteChannel`)
4. Was ist ein _direct buffer_? Wozu dient er? (`ByteBuffer`)
5. Wozu dient _buffer slicing_? (`slice()` in `ByteBuffer`)
6. Hält Java NIO in Bezug auf die asynchronen Operationen was es verspricht? Lesen Sie dazu die Präsentation `tymaPaulMultithreaded.pdf` aus diesem Ordner
7. Wir haben im Unterricht das Programm `CopyFile` kennengelernt. Vergleichen Sie die verschiedenen Varianten des Programmes (mit Streams, mit Channels und Buffers, mit Channels und DirectBuffers). Erstellen Sie eine Testreihe um herauszufinden für welche Files sich welche Implementation lohnt.
