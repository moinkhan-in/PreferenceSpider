# PreferenceSpider
Bind android shared preference values to field.
Read/Write operations of sharedpreferences are done using the only annotation.

__Remember: The PreferenceSpider is like ButterKnife, But this library bind sharedpreferences.

Field binding with shared preferences of Android which uses annotation processing to generate boilerplate
code for you.

 * Eliminate creating of preference class by using `@Preference` on fields.
 * It use code generation instead of reflection to make it fater.
 * Eliminate boilerplate code to read/write the preference code.
 * Apply formating directly on preference.

```java
class ExampleActivity extends Activity {

  @Preference(key = "sp_string", defaultValue = "userDefault")
  String spString;
  
  @Preference(key = "sp_boolean", defaultValue = "true")
  boolean spBoolean;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.simple_activity);
    
    PreferenceSpider.read(this);
    // TODO Use fields...
  }
}
```

__Note: Above example will use the default shared preferences, if you want to user your preference file name then,__

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
      new PreferenceSpider.Builder(getApplicationContext())
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

You can also use the `format` attribute to make formatted string.
```java
    @Preference(key = "sp_username", defaultValue = "Guest", format = "Welcome: %s")
    String spString;
```
  - Currently format attribute is only applicable on string preference.
  - You can give only 1 format specifier, otherwise it throws `MissingArgumentException` as preference contain single value.
  - Once you apply the format that field will become default readOnly. Because your preferece should not be overwrite with formatted string.


Download
--------

```groovy
dependencies {
  implementation 'in.moinkhan:preferencespider:alpha-2.0'
  annotationProcessor 'in.moinkhan:preferencespider-compiler:alpha-2.0'
}
```


