# jagintellij

An IntelliJ IDEA plugin that adds support for different RuneScape file types.

#### Supported types
- ClientScript 2 (`.cs2`)

## ClientScript 2
ClientScript 2 is the scripting format that is used in higher revision clients 
(~414+). See [RuneStar/cs2](https://github.com/RuneStar/cs2#readme) for more 
information on how the syntax works.

<p align="center">
  <img src="https://gitlab.com/neptune-ps/jagintellig/raw/master/images/cs2.png" 
       width="594" alt="IntelliJ plugin for ClientScript 2"/>
</p>

### Features
- Syntax highlighting
- Shows color for hex literals

## Installing
Installation is done by dragging a distrubtion zip file into an IntelliJ window
and restarting IntelliJ afterwards. The same applies for updating.

## Testing
To test the plugin you just need to run the `runIde` gradle task. This task
will open a sandboxed IntelliJ IDEA CE with the plugin loaded.

## Building
To build the distribution file run the `assemble` gradle task. This task will
build a zip file containing the plugin. For installing the plugin see the 
"Installing" section of this readme.
