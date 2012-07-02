# tin-whistle-tabs

A GUI/CLI tool that generates a D tin whistle tabs for the given musical notes.

## Installation

JAR file could be obtained by executing:

    $ lein uberjar

from a directory with a source code.

Alternatively, it could be downloaded from [here](https://github.com/downloads/gsnewmark/tin-whistle-tabs/tin-whistle-tabs-0.5.0-SNAPSHOT.jar).

## Usage

### GUI version

Start a program using the following command:

    $ java -jar tin-whistle-tabs-0.5.0-SNAPSHOT-standalone.jar

Shortly you will see an app's window - it's separated into two parts: on left
you see a list of selectable notes, on right - tab for a selected note.

### CLI version

Start a program using the following command:

    $ java -jar tin-whistle-tabs-0.5.0-SNAPSHOT-standalone.jar [args]

Possible arguments are:

`-abc <filename>` - creates a tab for a tune saved in a specified file in ABC
 notation 

`-note <sequence_of_notes>` - gives a fingering chart for every note specified
as an argument (notes must be separated by a space) 

Supported notes are: ** D E F# G A B C C# d e f# g a b c c# **

Fingering chart are represented as a vectors with 7 elements: first six
correspond to a whistle's holes (first element is an upper hole), their  values
are either :o - opened hole or :x - closed hole; last element says whether it's
required to overblow, it's value are either :- (not required) or :+ (required).

For example, F# would be represented as a [:x :x :x :x :o :o :-].

## License

Distributed under the Eclipse Public License, the same as Clojure.
