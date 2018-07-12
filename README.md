# PreferenceSpider
Bind android shared preference values to varable.

Field binding with shared preferences of Android which uses annotation processing to generate boilerplate
code for you.

 * Eliminate creating of preference class by using `@Preference` on fields.
 * Eliminate resource lookups by using resource annotations on fields.

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

__Remember: A preferenceSpider is like ButterKnife only infinitely less sharp.__

__Note: Above example will use the default shared preferences, if you want to user your own preference file name then,___

```java
  @Preference(name = "my_file", key = "sp_string", defaultValue = "userDefault")
  String spString;
```

If you want to update all variable into shared preferences.
```java
  PreferenceSpider.write(this);
```

If you want to make preference field read only. (Applying `write` will not update that field into preference.)
```java
  @Preference(key = "sp_boolean", defaultValue = "true", readOnly = true)
  boolean spBoolean;
```



Download
--------

```groovy
dependencies {
  implementation 'in.moinkhan:preferencespider:alpha-2.0'
  implementation 'in.moinkhan:preferencespider-annotations:alpha-2.0'
  annotationProcessor "in.moinkhan:preferencespider-compiler:alpha-2.0" 
}
```

If you are using Kotlin, replace `annotationProcessor` with `kapt`.


