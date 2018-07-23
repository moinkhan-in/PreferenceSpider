# PreferenceSpider
Bind android shared preference values to field.
Read/Write operations of shared preferences are done using the only annotation.

__Remember: The PreferenceSpider is like ButterKnife, But this library bind sharedpreferences.__

Download
--------

```groovy
dependencies {
  implementation 'in.moinkhan:preferencespider:alpha-2.4'
  annotationProcessor 'in.moinkhan:preferencespider-compiler:alpha-2.4'
}
```

Field binding with shared preferences of Android which uses annotation processing to generate boilerplate
code for you.

 * Eliminate creating of preference class by using `@Preference` on fields.
 * It use code generation instead of reflection to make it faster.
 * Use the preference singleton class for memory efficiency.
 * Eliminate boilerplate code to read/write the preference code.
 * Apply string formatting on preference.
 * Can use with your existing preferences.
 * Use native shared preference.
 * Can store object also.

```java
class ExampleActivity extends Activity {

  // Use field name as preference key ie. 'spInt', and default shared preference file.
  @Preference
  Integer spInt;

  // Use given key as preference key, and default shared preference file.
  @Preference(key = "sp_user")
  User spUser;

  // Use given key as preference key, and return given default value if not found.
  @Preference(key = "sp_boolean", defaultValue = "true")
  boolean spBoolean;

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
    spUser = new User(1, "TestUSer");
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

    ExampleAdapter(Context context) {
      // ...
      PreferenceSpider.read(this, context);
      // ...
    }

    // .....
  }

```

__You can use `name` attribute to retrieve field from custom preference file name,__

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


Update all fields into shared preferences with their respective keys.
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

#### Don't want to use annotations, I created a singleton preference helper class for you.

```java
  int spInt = PreferenceUtils.getInstance(context).readInt("sp_int");

  User user = PreferenceUtils.getInstance(this).read("spUser", User.class);
```

###### You can use following methods.
```java
  // custom object

  <T> T read(String prefsKey, Type type)

  <T> T read(String prefName, String prefsKey, Type type)

  void write(String prefsKey, Object prefVal)

  void write(String prefName, String prefsKey, Object prefVal)

  // boolean
  boolean readBoolean(String prefsKey)

  boolean readBoolean(String prefsKey, boolean defaultValue)

  boolean readBoolean(String prefName, String prefsKey, boolean defaultValue)

  void writeBoolean(String prefsKey, boolean prefsValue)

  void writeBoolean(String prefName, String prefsKey, boolean prefsValue)

  // double
  double readDouble(String prefsKey)

  double readDouble(String prefsKey, double defaultValue)

  double readDouble(String prefName, String prefsKey, double defaultValue)

  void writeDouble(String prefsKey, double prefsValue)

  void writeDouble(String prefName, String prefsKey, double prefsValue)

  // float
  float readFloat(String prefsKey)

  float readFloat(String prefsKey, float defaultValue)

  float readFloat(String prefName, String prefsKey, float defaultValue)

  void writeFloat(String prefsKey, float prefsValue)

  void writeFloat(String prefName, String prefsKey, float prefsValue)

  // integer
  int readInt(String prefsKey)

  int readInt(String prefsKey, int defaultValue)

  int readInt(String prefName, String prefsKey, int defaultValue)

  void writeInt(String prefsKey, int prefsValue)

  void writeInt(String prefName, String prefsKey, int prefsValue)

  // long
  long readLong(String prefsKey)

  long readLong(String prefsKey, long defaultValue)

  long readLong(String prefName, String prefsKey, long defaultValue)

  void writeLong(String prefsKey, long prefsValue)

  void writeLong(String prefName, String prefsKey, long prefsValue)

  // string
  String readString(String prefsKey)

  String readString(String prefsKey, String defaultValue)

  String readString(String prefName, String prefsKey, String defaultValue)

  void writeString(String prefsKey, String prefsValue)

  void writeString(String prefName, String prefsKey, String prefsValue)

  // String set
  Set<String> readStringSet(String prefsKey)

  Set<String> readStringSet(String prefsKey, Set<String> defaultValue)

  Set<String> readStringSet(String prefName, String prefsKey, Set<String> defaultValue)

  void writeStringSet(String prefsKey, Set<String> prefsValue)

  void writeStringSet(String prefName, String prefsKey, Set<String> prefsValue)

```


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