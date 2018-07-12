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


