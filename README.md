## README

### Overview
This repository contains two main JavaFX projects: 

1. **Imenik Application**: An application for managing a directory of persons, featuring a graphical user interface built with JavaFX and Scene Builder.
2. **Tourist Agency Application**: A redesigned user interface for a tourist agency's holiday reservation system, adhering to Nielsen's Ten Usability Principles and User Interface Design Guidelines.

### Imenik Application

#### Features
- **Menu Bar**: Includes File, Edit, View, and Help options with appropriate accelerators.
- **Toolbar**: Contains buttons for Open, Clear, Exit, and Print All actions.
- **Work Area**: 
  - Text input fields for name, surname, and country.
  - ComboBox for displaying and selecting entries.
  - Radio buttons for adding, removing the first, and removing the selected entry.
  - Command button to perform the selected action.
  - Spinner for selecting numbers between 0 and 20.
  - Checkbox to allow duplicates.
  - Text area to display combo box contents.
  - Status and message areas for displaying current operations and status.

#### Usage
1. **File Menu**:
   - Open (Ctrl+O): Opens a file selection dialog.
   - Clear (Ctrl+C): Clears the status and message areas.
   - Exit (Ctrl+Q): Exits the application.
2. **Edit Menu**: Enables the corresponding text fields.
   - Name
   - Surname
   - Country
3. **View Menu**:
   - Print All: Displays the combo box contents in the text area.
4. **Toolbar**: Provides quick access to File and View menu actions.
5. **Perform Action Button**: 
   - Add: Combines input fields into a string and adds to the combo box.
   - Remove First: Removes the first item from the combo box.
   - Remove Selected: Removes the currently selected item from the combo box.

### Tourist Agency Application

#### Features
- **Redesigned UI**: Adheres to Nielsen's Ten Usability Principles.
- **Data Entry Capabilities**: Similar to the old application but more user-friendly.
- **Data Validation**: Ensures required fields are filled and data is correctly formatted.
- **Reset Values**: Resets fields to initial values.
- **Save Values**: Saves entered data to a text file.

#### Usage
1. **Data Entry**: Enter the required details for holiday reservations.
2. **Validation**: The application will notify you if any required fields are missing or if there is incorrect data.
3. **Reset Values**: Resets all input fields to their default state.
4. **Save Values**: Saves the current data to a text file for later use.

### Installation

1. Clone the repository:
   ```sh
   git clone <repository_url>
   ```
2. Open the projects in IntelliJ IDEA.
3. Use Scene Builder to further customize the FXML files if needed.

### Running the Applications

1. **Imenik Application**:
   - Run the `Imenik` main class to start the application.
2. **Tourist Agency Application**:
   - Run the `TouristAgency` main class to start the redesigned application.

### Additional Notes

- Ensure that JavaFX is correctly set up in your development environment.
- The interface is resizable, with widgets adjusting accordingly.
- Refer to the provided PDF document for detailed adherence to Nielsen's Usability Principles in the Tourist Agency application.

### PDF Documentation

A PDF file describing how the Tourist Agency Application satisfies Nielsen's ten principles is included in the repository. Please review this document for an in-depth explanation of design decisions and usability enhancements.


