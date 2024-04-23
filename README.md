# Java implementation of pyson
A java implementation of pyson (original code [here](https://github.com/OmegaGodzilla66/PYSON))
## Why this implementation?
Because the original code was very poorly documented and my friends were peer pressuring me into making this
## Usage
Eventually I will get a JAR, and then you will be able to add it to your project as a library, but for now copy
`PysonEntry.java`, `PysonReader.java`, and `PysonWriter.java` into your project
## Documentation
_This is a short summary of the methods. To see more complete documentation look at the code comments_

### PysonEntry object
This object stores a pyson value. See `PysonEntry.java` for more documentation
### readPysonFile()
Reads a pyson file at `filename` file. It returns a list of `PysonEntry` that consists of the values of the pyson file
### addPysonEntry()
Adds a pyson entry `content` to a pyson file at `filename`. `content` is a `PysonEntry` object that will be appended to the `filename` pyson file.
