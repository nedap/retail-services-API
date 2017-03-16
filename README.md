# retail-services-API
Reference implementations and test tools for using the Nedap Retail API's

Provided for you as help to integrate the Nedap Retail platform into your own infrastructure and applications. You can use this to integrate data from:
- !D Cloud
- Retail Analytics
- Cube
- Renos
- Nedap Retail Device Management

The Java version is also available on Maven Central. This is the preferred way of including this library into your own source code: see http://mvnrepository.com/artifact/com.nedap.retail.services

# Open-API specification
The directory _openapi_ contains the Open-API specification JSON file which can be used to generate API client code. There are already 
three example projects included for Java, Python and C#.
 
##C# .NET
The directory _csharp_ contains an example C# .NET project with build scripts to generate and compile the API client code, API client 
library and the example project. Tested with VS2015 and Mono.

###Requirements
* Java 8+
* Mono (Linux) or Visual Studio (Windows) 
* Access token
* Api URL 

###Steps
1. In the directory _csharp_ run the command `sh ./build.sh` under Linux or `build.bat` under Windows (Under Windows only code will 
be generated).
2. After completion the directory _csharp/target_ wil contains the example executable and libraries.
3. Configure the access token and api URL in the _target/NedapRetailApiExample.exe.config_ file.
3. For running the example with Mono execute `mono NedapRetailApiExample.exe` in the _target_ directory.

##Java
The directory _java_ contains an example Java project with build scripts to generate and compile the API client code, API client 
library and the example project.

###Requirements
* Java 8+
* Maven
* Access token
* Api URL

###Steps
1. In the directory _java_ run the command `mvn package`.
2. After completion the directory _java/target_ wil contains the example executable and libraries.
3. For running the example execute `java -jar examples-2.0-SNAPSHOT.jar -token <access-token> <api-url>`.

##Python
The directory _python_ contains an example Python project with build scripts to generate the API client code, API client 
package and the example project.

###Requirements
* Java 8+
* Python 2.7
* Access token
* Api URL

###Steps
1. In the directory _python_ run the command `sh ./build.sh`.
2. After completion the directory _python/target_ wil contains the example python script and package.
3. Configure the correct access token and api URL in the _main.py_.
3. For running the example execute `python main.py`.