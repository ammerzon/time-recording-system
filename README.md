# Time Recording System

## Domänenmodell und Mapping
* Vervollständigen Sie das Domänenmodell des Zeiterfassungssystems, sodass darin Angestellte, die Adressen der Angestellten, die Tagebucheinträge und Projekte und deren Beziehungen abgebildet werden.
        Berücksichtigen Sie zusätzlich, dass sich die einem Projekt zuzuordnenden Kosten auf verschiedene Kostenarten aufteilen (Projektleitung, Entwicklung, Testen, Wartung etc.).
        Tagebucheinträge sind einem Projekt und einer Kostenart zuzuordnen.
* Erweitern Sie das bestehende Zeiterfassungssystem dahingehend, dass auf Basis des Zeiterfassungssystems auch die Rechnungslegung gegenüber Kunden durchgeführt werden kann.
    * Dazu müssen Projekte Kunden zugeordnet werden. Berücksichtigen Sie, dass für einen Kunden mehrere Projekte durchgeführt werden können.
    * Des Weiteren müssen Mitarbeiter kategorisiert werden (Projektleiter, Senior-Developer, Junior-Developer etc.). Sie müssen nicht berücksichtigen, dass Mitarbeiter zu verschiedenen Zeiten (oder auch gleichzeitig) verschiedene Funktionen innehaben.
    * Sie müssen Stundensätze in Ihr Modell aufnehmen. Stundensätze sind zeit- und funktionsabhängig (ein Projektleiter kostet mehr als ein Junior-Developer, 2020 wird für eine Entwicklerstunde mehr verrechnet als 2019).
* Überlegen Sie bei jeder Beziehung zwischen Entitäten, ob Sie diese in Form einer Navigationsbeziehung im Domänenmodell modellieren oder ob Sie zusammenhängende Entitäten über Abfragen ermitteln. Beispielsweise ist eine Navigationsbeziehung zwischen Employee und LogbookEntry nur gerechtfertigt, wenn Sie häufig alle Tagebucheinträge eines geladenen Mitarbeiters benötigen.
* Definieren Sie das Mapping des Domänenmodells auf die Datenbank mithilfe von JPA- und Hibernate-Annotationen.

## Datenzugriffsschicht

Kapseln Sie die Datenbankzugriffslogik durch konsequente Anwendung des DAO- oder Repository-Musters.

* Die DAOs/Repositories sollten für alle Domänenklassen eine gewisse Grundfunktionalität anbieten: Einfügen, Aktualisieren und Löschen von Domänenobjekten.
* Gewährleisten Sie auch, dass die Beziehungen zwischen den Domänenobjekten verwaltet werden können: Hinzufügen und Trennen von Beziehungen. Überlegen Sie sich sinnvolle Einstellungen für die Kaskadierungs- und die Ladestrategien.
* Verwenden Sie für zumindest drei (nicht-triviale) Abfragen die Criteria-API.
* Stellen Sie in Ihrer Datenzugriffsschicht Funktionen zur Verfügung, die Sie zur automatischen Rechnungslegung benötigen.
    * Berücksichtigen Sie, dass die Rechnungslegung sowohl kundenbezogen (wie viele Stunden können einem Kunden in einem bestimmten Zeitraum verrechnet werden) als auch projektbezogen (wie viele Stunden können einem Kunden für ein bestimmtes Projekt in einem vorgegebenen Zeitraum verrechnet werden) durchgeführt werden können soll.
    * In der generierten Rechnung sind die Kosten übersichtlich aufzuschlüsseln (nach Kostenarten und Kompetenz der beteiligten Mitarbeiter).
* Sehen Sie auf Datenzugriffsebene Funktionalität vor, mit der statistische Auswertungen durchgeführt werden können. Sie sollten damit Fragen wie die folgenden beantworten können:
    * Wie verteilen sich die Projektkosten auf verschiedene Mitarbeiter und/oder Kostenarten?
    * Wie verteilt sich die Arbeitszeit eines Angestellten auf verschiedene Projekte?
    * Darstellung der Ist-Arbeitszeit eines Angestellten für einen bestimmten Zeitraum, aufgeschlüsselt auf Tage/Wochen/Monate.

## Transaktionen und Caching

Schaffen Sie einen einfachen Mechanismus zum Starten, Bestätigen und Zurückrollen von Transaktionen.

* Beachten Sie, dass Transaktionen nicht in DAO/Repository-Methoden definiert werden dürfen, sondern Transaktionen mehrere DAO/Repository-Methoden umschließen sollen.
* Berücksichtigen Sie auch die korrekte Behandlung von Fehlern. Jede Art von Ausnahme soll zu einem Zurückrollen der gesamten Transaktion führen.

*Bonusaufgabe* (optional, für Beurteilung mit „Sehr gut“ erforderlich): Optimieren Sie Ihre Lösung durch Einsatz einer Caching-Lösung (Second Level Cache). Überlegen Sie sich, welche Entitäten Ihrer Anwendung besonders gut für Caching geeignet sind und analysieren Sie, welchen Performancegewinn die Verwendung von Caching nach sich zieht.

## Integrationstests

Implementieren Sie Integrationstests mit JUnit, mit denen die DAOs/Repositories ausführlich getestet werden.

* Entwickeln Sie für jede Datenzugriffs-Methode zumindest einen Test.
* Achten Sie darauf, dass Ihre Tests unabhängig voneinander sind, d. h. die Ausführung eines Tests nicht das Ergebnis eines anderen beeinflusst bzw. dass die Tests in einer bestimmten Reihenfolge durchgeführt werden müssen.
* Setzen Sie Mechanismen zur automatischen Schema-Generierung ein.
* Verwenden Sie eine In-Memory-Datenbank (H2, SQLite, Derby etc.) für Ihre Tests, damit diese möglichst effizient ausgeführt werden können.
* Setzen Sie nach Möglichkeit weitere Werkzeuge (z. B. hibernate-testing) ein, welche das Schreiben und die Durchführung von Datenbank-basierten Tests vereinfachen.
* Strukturieren Sie Ihre Tests so, dass sie mit Maven ausgeführt werden können (auch von der Kommandozeile).

## Konsolenanwendung

Entwickeln Sie eine einfache interaktive Konsolenanwendung, mit der die oben angeführten Operationen in Form von zusammenhängenden Workflows getestet werden können. Achten Sie auf eine übersichtliche Darstellung der Ergebnisse. Insbesondere sollen aus der Ausgabe des Programms die Transaktionsgrenzen klar ersichtlich sein. Die Konsolenanwendung soll eine Produktivdatenbank (keine In-Memory-Datenbank) verwenden.
Sie dürfen für diese Aufgabenstellung keine Funktionen des Spring-Frameworks verwenden.
