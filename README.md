# Application to Visualise 4D Objects in 3D Space

## CO3015 Computer Science Project
###### Shonam Shahi

## Current Build
All source files appended with "OLD" can and will be ignored, and are not depended on by other class files

## Intoduction
An education application to visualise how 4D geometry will apprear in 3D space, with abilities to transform the position along the x, y, z, w axis and rotations in all 6 axis possiable axes.

The application window is seperated into 'panels' which can be repositioned and resized around the window by the user.

## Installation
The application should be imported and loaded into an IDE i.e. Eclipse or IntelliJ

For the application to work, some libraries are needed to be added to the build path for the project. These required libraries can be found in `application/lib/`
### Eclipse
1. Select the Project
2. Go to 'Project > Properties > Java Build Path > Libraries'
3. Now add the included JARs within `application/lib/`. Namely:
    1. joml 1.9.2
    2. lwjgl 3.2.3 stable (release version is also included and usable)
4. Click 'Apply and Close'

### IntelliJ
1. Select the Project
2. Go to 'File > Project Structure > Libraries'
3. Press the '+' button to add new project libraries, and add the JARs inclused within `application/lib/`. Namely:
    1. joml 1.9.2
    2. lwjgl 3.2.3 stable (release version is also included and usable)
4. Click 'Ok'
