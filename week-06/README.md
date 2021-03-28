# APM Woche 6: Performance Management Cloud


[Vorlesungsfolien](Performance%20Management%20Cloud.pdf)


## Übung

In den vergangenen Übungen haben Sie eine einfache Key–Value-Store App mit 
Docker containerisiert, mit NGINX skaliert und hochverfügbar gemacht, um 
einen Clusterspeichersystem erweitert und ihre Performance mittels JMeter
analysiert. In dieser letzten Übung sollen Sie die Applikation in einen
[Kubernetes-Cluster](https://kubernetes.io/docs/concepts/overview/what-is-kubernetes/)
deployen und automatisch skalieren lassen.


### 1. Kubernetes aufsetzen

Kubernetes ist ein Open-Source-System zum "Orchestrieren" von 
Container-basierten Anwendungen. Mittels einer Konfigurationsdatei gibt man 
an, welche Container (und wie viele davon) man braucht, und Kubernetes 
kümmert sich darum, diese Container auf den verfügbaren Rechnern zu 
verteilen, zu vernetzen und im Fall eines Versagens automatisch wieder zu 
starten. Kubernetes bietet auch Loadbalancing und, für das Thema dieser Woche
interessant, automatische Skalierung von Containern basierend auf 
Performance-Metriken wie CPU-Auslastung.

Wenn Sie Docker Desktop verwenden (wie in der ersten Übung empfohlen), 
können Sie Kubernetes mit einem Klick in den Einstellungen aktivieren:

![Kubernetes in Docker Desktop aktivieren](kubernetes-docker-desktop.png)

Falls Kubernetes unter Windows nicht startet, folgen Sie den Anweisungen in 
dieser [Antwort auf Stackoverflow](https://stackoverflow.com/a/57711650/1374678).
Diese vorkonfigurierte Version von Kubernetes enthält nur einen einzigen 
*Node*, der Rechner, auf dem Docker installiert ist. Kubernetes verwaltet 
normalerweise mehrere Nodes, aber für einfache Experimente reicht dieses 
1-Node-Setup.

Sie interagieren mit Kubernetes mittels des Kommandozeilenbefehls `kubectl`. 
Geben Sie z. B. folgenden Befehl ein, um alle Nodes anzuzeigen:

    kubectl get nodes

Sie sollten eine Ausgabe ähnlich wie diese erhalten:

    NAME             STATUS   ROLES    AGE    VERSION
    docker-desktop   Ready    master   30s    v1.19.7

Für kompliziertere Befehle können Sie auch die Kubernetes-Integration von 
IntelliJ verwenden. Gehen Sie dafür in den Einstellungen auf "Plugins", 
wechseln Sie oben auf "Marketplace" und suchen Sie nach "Kubernetes". 
Installieren Sie das Plugin von JetBrains und starten Sie IntelliJ neu. 
Jetzt sollten Sie im "Services"-Tab unten einen Eintrag für "docker-desktop" 
sehen.


### 2. App auf Kubernetes deployen

Um die APM-App auf Kubernetes zu deployen, muss sie als Docker-Image zur 
Verfügung stehen. Bisher haben Sie die verschiedenen Docker-Images 
wahrscheinlich grösstenteils mittels Docker Compose erstellt, wodurch sie 
unter einem von Compose generierten Namen gespeichert wurden, z. B. 
'apm-app_web-app'. Um den Namen selber zu bestimmen, greifen Sie auf 
folgenden Docker-Befehl zurück, den Sie im Haupt-Ordner des Projekts ausführen:

    docker build -t apm-app .

Um eine App zu deployen, erstellen Sie eine Kubernetes-YAML-Datei. Erstellen 
Sie eine Datei namens 'apm-app.yaml' in einem neuen Ordner 'kubernetes'. 
Kopieren Sie folgenden Inhalt rein:

    apiVersion: apps/v1
    kind: Deployment
    metadata:
      name: apm-app
    spec:
      selector:
        matchLabels:
          app: apm-app
      replicas: 2
      template:
        metadata:
          labels:
            app: apm-app
        spec:
          containers:
            - name: apm-app
              image: apm-app
              imagePullPolicy: Never

Diese Konfiguration beschreibt ein Kubernetes-"Deployment", welches aus 
einer Sammlung von gleichartigen "Pods" besteht. Ein Pod ist die kleinste 
Einheit, welche Kubernetes "orchestriert" und kann aus einem oder mehreren 
Containern bestehen. In diesem Fall werden zwei Pods erstellt (`replicas: 2`).
Die Definition dieser Pods befindet sich im `template`-Teil und legt fest, 
dass ein Container mit dem zuvor erstellten Image 'apm-app' gestartet wird. 
Die Option `imagePullPolicy: Never` ist nötig, da dieses Image nur lokal 
gespeichert ist und nicht heruntergeladen werden soll (z. B. von
[Docker Hub](https://hub.docker.com/)).

Sie können dieses Deployment mit folgendem Befehl starten (dazu müssen Sie 
sich im selben Ordner wie die Datei befinden):

    kubectl apply -f apm-app.yaml

Mit folgenden Befehlen können Sie die gestarteten Pods auflisten:

    kubectl get pods

Sie sollten eine Ausgabe wie diese erhalten:

    NAME                      READY   STATUS    RESTARTS   AGE
    apm-app-cdd866ddf-cdstf   1/1     Running   0          11s
    apm-app-cdd866ddf-cqpb7   1/1     Running   0          12s

Um die Logs der Applikation anzuzeigen, verwenden Sie folgenden Befehl und 
ersetzen den Pod-Namen mit einem vorher angezeigten:

    kubectl logs apm-app-cdd866ddf-cdstf

Sie sollten die bekannte Ausgabe von Spring Boot sehen. Die beiden Instanzen 
sollten sich auch gegenseitig gefunden und dem gleichen Hazelcast-Cluster 
beigetreten sein (siehe [Übung 3](../week-03)). Die App-Instanzen laufen in 
normalen Docker-Containern, welche mit dem bekannten Docker-Befehl angezeigt 
werden können:

    docker ps

Allerdings ist die App von ausserhalb von Kubernetes noch nicht erreichbar. 
Dieses Problem werden Sie im nächsten Schritt angehen. Um das Deployment zu 
beenden, geben Sie einen der folgenden Befehle ein. Der zweite Befehl wird 
in den folgenden Übungen nützlich sein, weil er nicht nur das Deployment, 
sondern auch alle weiteren in der Datei definierten "Objekte" löscht (siehe 
nächster Schritt).

    kubectl delete deployment apm-app
    kubectl delete -f apm-app.yaml

Statt mittels dieser `kubectl`-Befehlen kann ein Grossteil der
Kubernetes-Interaktion übrigens auch über das IntelliJ-Kubernetes-Plugin 
erfolgen. Pods, Deployments und viele weitere Dinge sind im "Services"-Tab 
sichtbar. (Allerdings müssen Sie diese ab und zu refreshen.)


### 3. App öffentlich zugänglich machen

Mit der bisherigen Konfiguration ist die App bereits im internen 
Kubernetes-Netzwerk erreichbar (sonst hätten sich die beiden 
Hazelcast-Instanzen nicht gefunden), aber von aussen ist noch kein Zugriff 
möglich. Um diesen zu ermöglichen, braucht es ein weiteres Kubernetes-Objekt 
vom Typ "Service". Ein Service nimmt externe Anfragen an einen bestimmten Port 
entgegen und leitet sie an eine bestimmte Menge von Pods weiter.

Erweitern Sie die YAML-Datei indem Sie folgenden Text hinzufügen:

    ---
    apiVersion: v1
    kind: Service
    metadata:
      name: apm-app
    spec:
      selector:
        app: apm-app
      type: LoadBalancer
      ports:
        - port: 8080

Beachten Sie die drei Striche `---`; diese trennen mehrere Kubernetes-Objekte
innerhalb einer Datei voneinander. Diese Konfiguration erstellten einen 
Service vom Typ 'LoadBalancer', welcher die Anfragen in einer 
Round-Robin-Manier an jene Pods weiterleitet, welche durch den `selector` 
definiert sind. In diesem Fall sind das alle Pods mit dem Label `app: apm-app`,
was auf die im Deployment definierten Pods zutrifft.

Jetzt sollten Sie die App unter [localhost:8080](http://localhost:8080) 
erreichen. Beachten Sie den angezeigten Hostnamen. Wenn Sie die Seite 
wiederholt neu laden, sollten Sie irgendwann auch mal auf dem zweiten Server 
landen. Halten Sie die Reload-Taste (z. B. F5) gedrückt, um das Ganze zu 
beschleunigen.
