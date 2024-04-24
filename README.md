# Java implementation of pyson
A java implementation of pyson (original code [here](https://github.com/OmegaGodzilla66/PYSON))
## Why this implementation?
Because the original code was very poorly documented and my friends were peer pressuring me into making this
## Usage
Download the pyson.jar file from the most recent release.
### IntelliJ IDEA
Go to `File` > `Project Structure`. Select `Libraries` from the menu on the left. Click the `+` button.
Select `Java`. Open the `pyson.jar` file.
### Visual Studio Code
Install the java extension. From the left panel expand `Java Dependencies`. From there select `+` and choose the `pyson.jar`
### Eclipse
Right-click on your project. Select `build path`. Click on `Configure Build path`. 
Click on `Libraries` and select `Add external JARs`. Select `pyson.jar`. Click `apply` and `ok`
### NetBeans
From the project view, right-click your project and go to `properties`. In the category list on the left, go to `Libraries`
and click `Add JAR/Folder` then select the `pyson.jar` file.
## Documentation
_This is a short summary of the methods. To see more complete documentation look at the code comments_
### pyson.PysonEntry object
This object stores a pyson value. See `pyson.PysonEntry.java` for more documentation
### readPysonFile()
Reads a pyson file at `filename` file. It returns a list of `pyson.PysonEntry` that consists of the values of the pyson file
### addPysonEntry()
Adds a pyson entry `content` to a pyson file at `filename`. `content` is a `pyson.PysonEntry` object that will be appended to the `filename` pyson file.
