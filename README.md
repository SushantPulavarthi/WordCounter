# Word Counter

Adds a new action `Word Counter`, that prints out the length of each word in the current selection (works for multiple cursors).

## Usage
The highlighting can be triggered by activating the `Word Counter` action (default keymap is `Ctrl+M`) to print out the length of each word in the current selection.

Alternatively:
This can be edited by going to `File | Settings | Keymap | Plugins -> WordCounter`

## Installation

- Download the [latest release](https://github.com/SushantPulavarthi/IntellijGesturesDemo/releases) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Clone the repository, and run the gradle task `buildPlugin`. This will build the distribution to `build/distribution`, which you can use to install the plugin to Intellij
```
git clone https://github.com/SushantPulavarthi/IntellijGesturesDemo.git
cd IntellijGesturesDemo
./gradlew buildPlugin
```
