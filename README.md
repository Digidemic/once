# Once

## Pass custom IDs or create variables that return 'true' only the first time used... Once!

```kotlin
class Example {
    
    // Ex 1: Instantiate then call isFirstUse. First call only will return true. 
    val localOnce = Once()
    fun testLocalOnce(): Boolean = localOnce.isFirstUse

    // Ex 2: Instance of class combined with an ID will return true only the first time passed for that instance.
    fun testInstanceId(): Boolean = Once.byInstanceId(this, "customInstanceId")

    // Ex 3: For the active life of the application, anywhere the same ID is used in your application, true will only be returned the first time.
    fun testGlobalId(): Boolean = Once.byGlobalId("customGlobalId")

    // Test examples 1-3
    fun runExamples() {
        testLocalOnce() // true
        testLocalOnce() // false
        testLocalOnce() // false

        testInstanceId() // true
        testInstanceId() // false
        testInstanceId() // false

        testGlobalId() // true
        testGlobalId() // false
        testGlobalId() // false
    }
}
```

<br>

### Once is perfect for automatic flag flipping conditions used locally, in an instance, or globally anywhere for the life of your application!
  - <table>
    <tr>
    <th>Instead of</th>
    <th>Use Once (variable)</th>
    <th>Or use Once (pass ID)</th>
    </tr>
    <tr>
    <td>
    
    ```kotlin
    class Example {
        var setup: Boolean = true

        fun test() {
            if(setup) {
                setup = false
                //doSetup()
            }
        }
    }

    ```

    </td>
    <td>

    ```kotlin
    class Example {
        val setup = Once()

        fun test() {
            if(setup.isFirstUse) {
                //doSetup()
            }
        }
    }


    ```

    </td>
    <td>

    ```kotlin
    class Example {
        fun test() {
            if(Once.byGlobalId("id")) {
                //doSetup()
            }
            // OR 
            if(Once.byInstanceId(this, "id")) {
                //doSetup()
            }
        }
    }
    ```

    </td>
    </tr>
    </table>


### Check out the [example app for Once](/example-once/)!

<br>

## Table of Contents
- [Usage / Examples](#usage--examples)
- [Syntax](#syntax)
- [Installation](#installation)
- [Versioning](#versioning)
- [License](#license)

<br>

## Usage / Examples

### Usage 1 Example: Local variable
In any instantiated instance of class `Foo`, function `bar()` may be called multiple times. No matter how many times `bar()` is called, only for that instance of `Foo`, `once.isFirstUse` will only be `true` the first time `bar()` is called. See [Syntax section](#local-variable) for additional information.

This works the same as [Once.byInstanceId()](#usage-2-example-passing-an-instance-and-id) but uses a static function with a passed ID rather than using a local variable.
```kotlin
class Foo {
    val once = Once()

    fun bar(): Boolean {
        if(once.isFirstUse) {
            // First time calling bar() for instance of class Foo
            return true
        } else {
            // Not the first time calling bar() for instance of class Foo
            return false
        }
    }
}

class Main {
    // Foo instantiated twice
    private val foo1 = Foo()
    private val foo2 = Foo()

    init {
        foo1.bar() // true
        foo1.bar() // false

        foo2.bar() // true
        foo2.bar() // false

        foo1.bar() // false
        foo2.bar() // false
    }
}
```
<br>

### Usage 2 Example: Passing an instance and ID

In any instantiated instance of class `Foo`, function `bar()` may be called multiple times. No matter how many times `bar()` is called, only for that instance of `Foo` (even if the same ID is used in other instances), `Once.byInstanceId(this, "customInstanceId")` will only be `true` the first time `bar()` is called. See [Syntax section](#oncebyinstanceid) for additional information.

This works the same as [val once = Once(); once.isFirstUse](#usage-1-example-local-variable) but uses a local variable rather than a static function with a passed ID.

See [Once.byGlobalId()](#usage-3-example-passing-a-global-id) for the ability to use the same ID everywhere in your project to persist the results throughout the life of your application rather than just for the instance.

```kotlin
class Foo {
    fun bar() {
        if(Once.byInstanceId(this, "customInstanceId")) {
            // First time calling bar() for instance of class Foo with ID "customInstanceId"
        } else {
            // Not the first time calling bar() for instance of class Foo with ID "customInstanceId"
        }
    }
}

class Main {
    // Foo instantiated twice
    private val foo1 = Foo()
    private val foo2 = Foo()

    init {
        foo1.bar() // true
        foo1.bar() // false

        foo2.bar() // true
        foo2.bar() // false

        foo1.bar() // false
        foo2.bar() // false
    }
}
```
<br>

### Usage 3 Example: Passing a global ID

In any instantiated instance of class `Foo`, function `bar()` may be called multiple times. No matter how many times `bar()` is called, and for any instance of `Foo` with the same ID, `Once.byGlobalId("customGlobalId")` will only be `true` the first time `bar()` is called. Anywhere that `"customGlobalId"` is used in `byGlobalId()` going forward for the life of this example project (even outside of instances of `Foo`), `true` will always be returned. See [Syntax section](#oncebyglobalid) for additional information.

See [Once.byInstanceId()](#usage-2-example-passing-an-instance-and-id) for the ability to use the same ID tied to an instance rather than everywhere in your project to persist the results throughout the life of your application.

```kotlin
class Foo {
    fun bar() {
        if(Once.byGlobalId("customGlobalId")) {
            // First time ID "customGlobalId" is used anywhere in the life of the application (not just instance of Foo).
        } else {
            // Not the first time ID "customGlobalId" is used anywhere in the life of the application (not just instance of Foo).
        }       
    }
}

class Main {
    // Foo instantiated twice
    private val foo1 = Foo()
    private val foo2 = Foo()

    init {
        foo1.bar() // true
        foo1.bar() // false

        foo2.bar() // false
        foo2.bar() // false
    }
}
```
<br>

## Syntax

### Main Syntax

There are 3 main usages for Once. Two of which directly call a function without instantiation ([Once.byInstanceId()](#oncebyinstanceid) and [Once.byGlobalId()](#oncebyglobalid)), One requires [instantiating Once](#local-variable). See [Usage / Examples](#usage--examples) section for code usage and examples.

#### Local Variable:
- Requires instantiating Once with `Once()` or `Once.NewLocalInstance()` (both instantiate with `NewLocalInstance()` behind the scenes).
- Calling `isFirstUse` property for the first time will return true. Everytime after, `isFirstUse` will always return false for the created instance of it.
    - Instantiate a new `Once` variable to repeat this.
    - See an example of this [here](#usage-1-example-local-variable)

#### Once.byInstanceId:
- Do not instantiate Once. Call function `Once.byInstanceId(this, id)` directly to return a boolean. See an example [here](#usage-2-example-passing-an-instance-and-id)
    - <b>`instance`</b> - T / <i><b>Required</b></i>
        - An instance to bind the passed ID with. This to allow the same ID to be used in different or new instances with different results.
    - <b>`id`</b> - String / <i><b>Required</b></i>
        - An identifier.

#### Once.byGlobalId:
- Do not instantiate Once. Call function `Once.byGlobalId(id)` directly to return a boolean.
- The first time a specific ID is passed to `byGlobalId` anywhere in the application for the life of the application, true will be returned. Everytime after, false will be returned with that same ID when passed into `byGlobalId` for the remainder of the life of the application. See an example [here](#usage-3-example-passing-a-global-id)
    - <b>`id`</b> - String / <i><b>Required</b></i>
        - An identifier.

<br>

## Installation

### Install with JitPack
[![](https://jitpack.io/v/Digidemic/once.svg)](https://jitpack.io/#Digidemic/once)
1) Add JitPack to your project's root `build.gradle` at the end of `repositories`:
- ```groovy
  dependencyResolutionManagement {
      repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
      repositories {
          mavenCentral()
          maven { url 'https://jitpack.io' }
    }
  }
  ```
2) In the `build.gradle` of the module(s) you wish to use Once with, add the following to `dependencies`:
- ```groovy
  dependencies {
      // Required: Installs the .aar without any documentation.
      implementation 'com.github.digidemic:once:1.1.0'
      
      // Optional: Displays documentation while writing coding. 
      implementation 'com.github.digidemic:once:1.1.0:javadoc'
  
      // Optional: Displays documentation (more comprehensive than javadoc in some cases) and uncompiled code when stepping into library.
      implementation 'com.github.digidemic:once:1.1.0:sources'
  }
  ```
3) [Sync gradle](https://www.delasign.com/blog/how-to-sync-an-android-project-with-its-gradle-files-in-android-studio/) successfully.
4) Done! Your Android project is now ready to use Once. Go to [Examples](#usage--examples) or [Syntax](#syntax) for Once usage!

<br>

## Versioning
- [SemVer](http://semver.org/) is used for versioning.
- Given a version number MAJOR . MINOR . PATCH
    1) MAJOR version - Incompatible API changes.
    2) MINOR version - Functionality added in a backwards-compatible manner.
    3) PATCH version - Backwards-compatible bug fixes.
       <br><br>

## License
Once created by Adam Steinberg of DIGIDEMIC, LLC
```
Copyright 2024 DIGIDEMIC, LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```