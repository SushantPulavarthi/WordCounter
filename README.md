# Word Counter

Adds a new action `Word Counter`, that prints out the length of each word in the current selection (works for multiple cursors).

![WordCounter](https://github.com/user-attachments/assets/36e11a93-d11d-4f9b-bfb7-0ecf815ca6f0)

## Usage
The highlighting can be triggered by activating the `Word Counter` action (default keymap is `Ctrl+M`) to print out the length of each word in the current selection.
This can be edited by going to `File | Settings | Keymap | Plugins -> WordCounter`

Alternatively:
Added a button to the right side toolbar that opens a tool window. The first tab of this contains a button that you can press after selecting text to display the length of each word.

## Installation

- Download the [latest release](https://github.com/SushantPulavarthi/WordCounter/releases) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Clone the repository, and run the gradle task `buildPlugin`. This will build the distribution to `build/distribution`, which you can use to install the plugin to Intellij
```
git clone https://github.com/SushantPulavarthi/WordCounter.git
cd WordCounter
./gradlew buildPlugin
```
