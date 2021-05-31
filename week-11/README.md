# APM Woche 11: JIT-Kompilierung


[Vorlesungsfolien](w11_jit_compilation.pdf)

## Übung

Im Ordner 'code' finden Sie zwei Dateien, `Producer.java` und `Consumer.java`. Kompilieren Sie diese mit `javac` und beantworten Sie danach die folgenden Fragen.

1. Was ist die Laufzeit des Programmes Consumer?
2. Was ist die Laufzeit des Programmes Consumer nur mit Interpretierung? Sie können den Switch `-Xint` verwenden um Kompilierung auszuschalten.
3. Was ist die Laufzeit des Programmes Consumer wenn alle Methoden kompiliert werden? Sie können den Switch `-Xcomp` verwenden um alle Methoden zu kompilieren.
4. Was ist die Laufzeit des Programmes mit C1? Sie können den Switch
`-XX:TieredStopAtLevel=3` verwenden um C2-Kompilierungen auszuschalten.
6. Was ist die Laufzeit des Programmes mit C2? Sie können den Switch `-XX:TieredStopAtLevel=4` verwenden um auch C2-Kompilierungen zu erlauben.
7. Was sind die Laufzeiten der ersten 501 Aufrufe (Items 0-500) der Methode `produce()?`
8. Was sind die Laufzeiten der Aufrufen 502-1002 Aufrufe der Methode `produce()`?
9. Bei welchen Aufrufen wird die Methode produce() kompiliert bzw. deoptimiert? Sie können die folgenden Switches verwenden um den Kompilierungsprozess einzusehen bzw. das Experiment besser zu kontrollieren:
	* -XX:+PrintCompilation
	* -XX:CompileOnly=Producer.produce
	* -Xbatch