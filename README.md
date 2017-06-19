Nedap Retail !D Cloud API
=========================
Reference implementations and test tools for using the Nedap Retail !D Cloud APIs.

This example code is a supplement to the !D Cloud developer portal: https://developer.nedap-idcloud.com/

## Open-API specification
The directory _openapi_ contains the Open-API specification (https://www.openapis.org/) JSON file which can be used to generate API client code. There are
three example projects available for Java, C# and Python. If you want to learn more about using this specification please check:
http://swagger.io/

## Java
The directory _java_ contains an example Java project with build scripts to generate and compile the API client code, API client
library and the example project.

### Requirements
* Java 8+
* Maven
* Access token
* API URL

### Steps
1. In the directory _java_ run the command `mvn package`.
2. After completion the directories _java/client/target_ and _java/examples/target_ will contains the example executable and libraries.
3. For running the example execute `java -jar examples-2.0-SNAPSHOT.jar -token <access-token> -url <api-url>`.

### Usage generated source 
One approach is to include the generated sources, which after running the previous build steps, can be found in the directory 
_java/client/target/generated-sources/openapi/src/main/java_.
Include or copy this directory into your project and mark it in the IDE as Java source.

### Usage library
Another approach is to include the !D Cloud API client library. After running `mvn install` in the _java/client_ directory the library 
will be build and installed in the local Maven repository.

Add the following code snippet to the pom.xml and the library can be used:
```xml
<dependency>
    <groupId>com.nedap.retail.client</groupId>
    <artifactId>api-client</artifactId>
    <version>2.0-SNAPSHOT</version>
</dependency>
```

## C# .NET
The directory _csharp_ contains an example C# .NET project with build scripts to generate and compile the API client code, API client 
library and the example project. Tested with VS2015 and Mono.

### Requirements
* Java 8+
* Mono (Linux) or Visual Studio (Windows) 
* Access token
* API URL

### Steps
1. In the directory _csharp_ run the command `sh ./build.sh` under Linux or `build.bat` under Windows (Under Windows only code will be generated).
2. After completion the directory _csharp/target_ wil contains the example executable and libraries.
3. Configure the access token and API URL in the _target/NedapRetailApiExample.exe.config_ file.
3. For running the example with Mono execute `mono NedapRetailApiExample.exe` in the _target_ directory.

### Usage generated source
After running the previous steps the directory _csharp/target/generated-sources_ will contain the generated sources. Copy or include the 
directory _Nedap.Retail.Api_ into your project.

### Usage library
After running the previous steps the directory _csharp/target/_ will contain the _Nedap.Retail.Api.dll_ .NET assembly which can be 
included in your .NET project. 

## Python
The directory _python_ contains an example Python project with build scripts to generate the API client code, API client 
package and the example project.

### Requirements
* Java 8+
* Python 2.7
* Access token
* API URL

### Steps
1. In the directory _python_ run the command `sh ./build.sh`.
2. After completion the directory _python/target_ wil contains the example python script and package.
3. Configure the correct access token and API URL in the _main.py_.
3. For running the example execute `python main.py`.

## Best practise
* Do _not_ use a leading slash in the api URL. Example: _http://api.nedapretail.com_