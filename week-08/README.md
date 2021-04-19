# APM Woche 8: Garbage Collection Algorithmen


[Intro (zweite Semesterhälfte)](w08_intro.pdf)

[Vorlesungsfolien](w08_gc_algorithmen.pdf)

## Übungen

### Reference Counting

Für ein Programm wurde der folgende Objektgraph aufgebaut:

![Objektgraph](objektgraph.png)

Das Objekt X hat eine reference count von 1. Was ist die reference count der anderen Objekte? Was passiert mit den reference counts und mit anderen Objekten, wenn der Pointer A  &#8594; B entfernt wird?

### Vergleich Speicherverwaltungsmethoden

Füllen Sie die am Ende der Vorlesung gezeigte Tabelle aus.

1. Nach welchen Kriterien macht es nach Ihrer Beurteilung Sinn die in der Vorlesung gesehnen Methoden für Speicherverwaltung (Reference Counting, Mark & Sweep GC, Mark & Copy GC und Mark & Compact GC) miteinander zu vergleichen und wieso?
2. Vergleichen Sie die in der Vorlesung gesehenen Speicherverwaltungsmethoden nach den von Ihnen ausgewählten Kriterien. Nehmen Sie die von Ihnen ausgefüllte Tabelle nächste Woche in die Vorlesung mit. Wir werden den Vergleich zusammen besprechen.
