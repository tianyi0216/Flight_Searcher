run: FlightPlannerFrontend.class
	java --module-path ./javafx-sdk-11/lib/ --add-modules javafx.controls,javafx.fxml FlightPlannerFrontend

runTests: runDataWranglerTests runAlgorithmEngineerTests runBackendDeveloperTests runFrontendDeveloperTests
	@echo ""

runDataWranglerTests: DataWranglerTests.class FlightLoader.class #runDataWranglerPeerCodeTests 
	#DataWranglerPeerCodeTests doesn't work on my (DataWrangler) computer, so I've commented it out. Please check the comments in the file for more information.
	java -jar junit5.jar --class-path . --scan-classpath -n DataWranglerTests 

runDataWranglerPeerCodeTests: DataWranglerPeerCodeTests.class
#	java --module-path ./javafx-sdk-11/lib --add-modules javafx.controls --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar junit5.jar -cp .:JavaFXTester.jar --scan-classpath DataWranglerPeerCodeTests
	java --module-path ./javafx-sdk-11/lib --add-modules javafx.controls --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar junit5.jar -cp .:JavaFXTester.jar --scan-classpath

DataWranglerPeerCodeTests.class: DataWranglerPeerCodeTests.java
#	javac --module-path ./javafx-sdk-11/lib --add-modules javafx.controls -cp .:junit5.jar:JavaFXTester.jar DataWranglerPeerCodeTests.java
	javac --module-path ./javafx-sdk-11/lib --add-modules javafx.controls -cp .:junit5.jar:JavaFXTester.jar DataWranglerPeerCodeTests.java

DataWranglerTests.class: DataWranglerTests.java DijkstraGraph.class
	javac -cp .:junit5.jar DataWranglerTests.java -Xlint

DWFlightAppPlaceholder.class: DWFlightAppPlaceholder.java
	javac DWFlightAppPlaceholder.java

FlightLoader.class: FlightLoader.java Path.class
	javac FlightLoader.java

Path.class: Path.java
	javac Path.java

runAlgorithmEngineerTests: AlgorithmEngineerTests.class
	java -jar junit5.jar --class-path . --scan-classpath -n AlgorithmEngineerTests

AlgorithmEngineerTests.class: AlgorithmEngineerTests.java DijkstraGraph.class
	javac -cp .:junit5.jar AlgorithmEngineerTests.java -Xlint

DijkstraGraph.class: DijkstraGraph.java
	javac DijkstraGraph.java

runFrontendDeveloperTests: FrontendDeveloperTests.class FlightPlannerFrontend.class
#	java --module-path ./javafx-sdk-18/lib --add-modules javafx.controls --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar junit5.jar -cp .:JavaFXTester.jar --scan-classpath FrontendDeveloperTests
	java --module-path ./javafx-sdk-11/lib --add-modules javafx.controls --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar junit5.jar -cp .:JavaFXTester.jar --scan-classpath

FrontendDeveloperTests.class: FrontendDeveloperTests.java
#	javac --module-path ./javafx-sdk-18/lib --add-modules javafx.controls -cp .:junit5.jar:JavaFXTester.jar FrontendDeveloperTests.java
	javac --module-path ./javafx-sdk-11/lib --add-modules javafx.controls -cp .:junit5.jar:JavaFXTester.jar FrontendDeveloperTests.java

FlightPlannerFrontend.class: FlightPlannerFrontend.java
	javac --module-path ./javafx-sdk-11/lib/ --add-modules javafx.controls,javafx.fxml FlightPlannerFrontend.java

runBackendDeveloperTests: BackendDeveloperTests.class
	java -jar junit5.jar --class-path . --scan-classpath

BackendDeveloperTests.class: BackendDeveloperTests.java FlightPlannerBackend.class 
	javac -cp .:junit5.jar BackendDeveloperTests.java -Xlint

FlightPlannerBackend.class: FlightPlannerBackend.java DijkstraGraphBD.class
	javac FlightPlannerBackend.java

DijkstraGraphBD.class: DijkstraGraphBD.java
	javac DijkstraGraphBD.java

clean:
	rm *.class
