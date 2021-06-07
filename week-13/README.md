# APM Woche 13: Java Class Loading


[Vorlesungsfolien](w13_classloading.pdf)

[Skript](script_classloader.pdf)

[Skript mit Notizen](script_classloader_with_notes.pdf)

## Übung

### Problemstellung

Der Ordner `code` enthält ein Beispielprogramm `CL`. Dieses versucht eine Klasse `Singleton` zweimal zu laden.

Bringen Sie den Code zum Laufen. Bringen Sie den Code zum Laufen. Die Meldung `Singleton instance initialized` soll zwei Mal auf
der Konsole erscheinen. Passiert was Sie erwarten, nämlich dass die Klasse zwei Mal geladen wird? Warum (nicht)?

### Lösung

Die Klasse wird nur geladen, aber nicht initialisiert. Dies passiert erst, wenn auch tatsächlich Code der Klasse zur Ausführung gebracht wird. Daher ist es nötig, nach dem Befehl die Klasse zu laden...


```java
cl.loadClass(classname);
```

...auch noch eine Methode aufzurufen:

```java
Method m = c.getDeclaredMethod("getInstance", new Class<?>[] {});
m.invoke(null, (Object[])null);
```

Falls die Meldung `Singleton instance initialized!` nur einmal auf der Konsole erscheint, dann wurde wahrscheinlich das File `Singleton.class` welches der Compiler direkt neben das File `CL.class` geschrieben hat, vom System-Classloader schon gefunden und geladen. Versuchen Sie es nochmals in dem Sie die Klasses von einem Ort laden, der für den System-Classloader nicht erreichbar ist.