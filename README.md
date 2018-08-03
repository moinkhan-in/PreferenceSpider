# PreferenceSpider
Bind android shared preference values to field/Views.
Read/Write operations of shared preferences are done using the only annotation.

__Remember: The PreferenceSpider is like ButterKnife, But this library bind sharedpreferences.__

Download
--------

```groovy
dependencies {
  implementation 'in.moinkhan:preferencespider:alpha-3.0'

  // for java
  annotationProcessor 'in.moinkhan:preferencespider-compiler:alpha-3.0'

  // for kotlin
  kapt 'in.moinkhan:preferencespider-compiler:alpha-3.0'
}
```

Field binding with shared preferences of Android which uses annotation processing to generate boilerplate
code for you.

 * Eliminate creating of preference class by using `@Preference` on fields.
 * It use code generation instead of reflection to make it faster.
 * Use the preference singleton class for memory efficiency.
 * Eliminate boilerplate code to read/write the preference code.
 * Bind the preference values with views.
 * Apply string formatting on preferences.
 * Can use with your existing preferences.
 * Use native shared preference.
 * Support custom object.

```java
@PreferenceName("user_detail")
class ExampleActivity extends Activity {

  @Preference
  User spUser;

  @Preference(key = "sp_remember", defaultValue = "true")
  CheckBox spRememberMe;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.simple_activity);

    // If your target contains the View, make sure it is binded and not null.
    PreferenceSpider.read(this);

    // TODO Use fields...
  }
  
  // some event on which you want to save shared preference.
  @OnClick(R.id.btnSave)
  void onSaveClicked() {
  
    // somehow you update the value.
    spUser = new User(1, "TestUSer");
    spRememberMe.setChecked(false);

    // save this to shared preference.
    PreferenceSpider.write(this);
  }
}
```

##### Non-Activity or Non-Fragment classes
```java
  @PreferenceName("sp_file")
  public class ExampleAdapter extends BaseAdapter {

    @Preference(key = "spUserName")
    String userName;

    ExampleAdapter(Context context) {
      PreferenceSpider.read(this, context);
      // ...
    }

  }
```

### @PreferenceName
Allows you to specify preference file, so that preference spider will bind the all preference of that class from given preference name.

If you don't want give above annotation on all classes you can omit that annotation and provide name at application level.

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

### Customization for @Preference
#### 1. key
 To provide your preference key. You can omit it, but then it will use field name as key.
```java
@Preference(key = "sp_string")
String spString;

// equivalent
// for writing
getDefaultPrefs()
  .edit()
  .putString("sp_string", spString)
  .commit();

// for reading
spString = getDefaultPrefs().getString("sp_string", null);
```

#### 2. defaultValue
 To provide the default value if value doesn't exist on a given key.
```
@Preference(defaultValue = "userDefault")
String spString;

// equivalent
// for reading
spString = getDefaultPrefs()
  .getString("spString", "userDefault");
```

#### 3. format
 To apply string formatting with string preference values.
```java
@Preference(defaultValue = "Guest", format = "Welcome: %s")
String spUser;

// equivalent
// for reading
spString = String.format(
  "Welcome: %s",
  getDefaultPrefs().getString("spUser", "Guest")
);
```

  - format attribute is only applicable on string preference.
  - You can give only 1 format specifier (%s), otherwise it throws `MissingArgumentException` as preference contain single value.
  - Applying the format attribute will become readOnly preference.

#### 4. readOnly
 Sometimes you want to retrieve preference value, but changing value of that field should not update on preference.
```java
@Preference(readOnly = true)
boolean spBoolean;
```
 Applying `PreferenceSpider.write()` will ignore the readOnly fields.

#### 5. name
 Applying `@PreferenceName` on a class will retrieve all preference from that class for that class, and applying the preference name will retrieve all preference fields from that file. But to retrieve the preference from specific file apart from registered name you can use `name` attribute.
``` java
@Preference(name = "my_file")
String spString;

// equivalent
// for writing
getSharedPrefsEditor("my_file")
  .edit()
  .putString("spString", spString)
  .commit();

// for reading
spString = getSharedPrefsEditor("my_file")
  .getString("spString", null);
```

#### Don't want to use annotations, There is `PreferenceUtils` class available that you can use.

```java
  int spInt = PreferenceUtils.getInstance(context).readInt("sp_int");

  User user = PreferenceUtils.getInstance(context).read("spUser", User.class);
```
 There are more methods available that you can see

 ```java
   // custom object

   <T> T read(String prefsKey, Type type)

   <T> T read(String prefName, String prefsKey, Type type)

   <T> T read(String prefsKey, Class<T> type)

   <T> T read(String prefName, String prefsKey, Class<T> type)

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

##### View and Preference Mapping table

As we know you can apply preference on View, following table describes which preference data type is supported with which view.

|Data Type     | Write Method    | Read Method    | Supported Views     |
|--------------|-----------------|----------------|---------------------|
|String      | getText()       | setText()      | EditText, TextView, CheckedTextView, Button, Chronometer,  <br> <? extends TextView>
|String      | getTitle()      | setTitle()     | ToolBar, <br> <? extends ToolBar>
|Integer     | getProgress()   | setProgress()  | ProgressBar, SeekBar,<br> <? extends ProgressBar>
|Integer     | getValue()      | setValue()     | NumberPicker,<br> <? extends NumberPicker>
|Integer     | getSelectedItem()|setSelection() | Spinner, <br> <? extends Spinner>
|Boolean     | isChecked()    | setChecked()   | CheckBox, RadioButton, ToggleButton, Switch, <br> <? extends CompoundButton>
|Float       | getRating()     | setRating()    | RatingBar, <br> <? extends RatingBar>


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