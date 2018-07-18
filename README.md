# PreferenceSpider
Bind android shared preference values to field.
Read/Write operations of sharedpreferences are done using the only annotation.

__Remember: The PreferenceSpider is like ButterKnife, But this library bind sharedpreferences.__

Download
--------

```groovy
dependencies {
  implementation 'in.moinkhan:preferencespider:alpha-2.2'
  annotationProcessor 'in.moinkhan:preferencespider-compiler:alpha-2.2'
}
```

Field binding with shared preferences of Android which uses annotation processing to generate boilerplate
code for you.

 * Eliminate creating of preference class by using `@Preference` on fields.
 * It use code generation instead of reflection to make it faster.
 * Use the preference singleton class for memory efficiency.
 * Eliminate boilerplate code to read/write the preference code.
 * Apply formatting directly on preference.

```java
class ExampleActivity extends Activity {

  @Preference(key = "sp_string", defaultValue = "userDefault")
  String spString;
  
  @Preference(key = "sp_boolean", defaultValue = "true")
  boolean spBoolean;

  // it will use variable name as preference key.
  @Preference
  Integer spInt;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.simple_activity);
    
    PreferenceSpider.read(this);
    // TODO Use fields...
  }
  
  // some event on which you want to save shared preference.
  @OnClick(R.id.btnSave)
  void onSaveClicked() {
  
    // somehow you update the value.
    spString = "Updated";
    spBoolean = false;
    spInt = 50;
    
    // save this to shared preference.
    PreferenceSpider.write(this);
  }
}
```

##### Non-Activity or Non-Fragment classes
```java
  public class ExampleAdapter extends BaseAdapter {

    @Preference(key = "spUserName")
    String userName;

    private Context context;

    SampleAdapter(Context context) {
      this.context = context;
      // ...
      PreferenceSpider.read(this, context);

    }

    // .....
  }

```

__Note: Above example will use the default shared preferences, If you want to use your preference file name then,__

```java
  @Preference(name = "my_file", key = "sp_string", defaultValue = "userDefault")
  String spString;
```

Configure preference file name at application level, So that you don't have to provide it on each field.
```java
  public class MyApplication extends Application {

    @Override
    public void onCreate() {
      super.onCreate();
      PreferenceSpider.newBuilder()
        .preferenceName("my_file")
        .allowLog(true)
        .build();
    }
  }
```


Update all fields into shared preferences.
```java
  PreferenceSpider.write(this);
```

To make field read only use `readOnly` attribute. (Applying `write` will not update that field into preference)
```java
  @Preference(key = "sp_boolean", defaultValue = "true", readOnly = true)
  boolean spBoolean;
```

You can also use the `format` attribute to make formatted string. e.g Welcome: [Preferece Value]
```java
  @Preference(key = "sp_username", defaultValue = "Guest", format = "Welcome: %s")
  String spString;
```
  - Currently format attribute is only applicable on string preference.
  - You can give only 1 format specifier, otherwise it throws `MissingArgumentException` as preference contain single value.
  - Once you apply the format that field will become default readOnly. Because your preferece should not be overwrite with formatted string.


Licence
-------

    Copyright 2018 Moinkhan Pathan

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.