package in.moinkhan.preferencespider_compiler;

import java.util.HashMap;

import javax.lang.model.type.TypeKind;

/**
 * Created by moin on 18/7/18.
 */

public class Constants {

    public static final HashMap<String, DataType> SUPPORTED_DATA_TYPES = new HashMap<String, DataType>() {{
        put(TypeKind.LONG.toString(), DataType.LONG);
        put(TypeKind.DOUBLE.toString(), DataType.DOUBLE);
        put(TypeKind.INT.toString(), DataType.INT);
        put(TypeKind.FLOAT.toString(), DataType.FLOAT);
        put(TypeKind.BOOLEAN.toString(), DataType.BOOLEAN);
        put("java.lang.Long", DataType.LONG);
        put("java.lang.Double", DataType.DOUBLE);
        put("java.lang.Integer", DataType.INT);
        put("java.lang.Float", DataType.FLOAT);
        put("java.lang.Boolean", DataType.BOOLEAN);
        put("java.lang.String", DataType.STRING);

    }};

    public static final HashMap<String, FieldType> SUPPORTED_FIELD_TYPES = new HashMap<String, FieldType>() {{
        put("android.widget.TextView", FieldType.TEXT_VIEW);
        put("android.widget.CompoundButton", FieldType.COMPOUND_BUTTON);
        put("android.widget.ProgressBar", FieldType.PROGRESS_BAR);
        put("android.widget.RatingBar", FieldType.RATING_BAR);
        put("android.widget.SeekBar", FieldType.SEEK_BAR);
        put("android.widget.AdapterView", FieldType.ADAPTER_VIEW);
    }};

    public static final HashMap<FieldType, DataType> FILED_TYPE_TO_DATA_TYPE = new HashMap<FieldType, DataType>() {{
        put(FieldType.TEXT_VIEW, DataType.STRING);
        put(FieldType.COMPOUND_BUTTON, DataType.BOOLEAN);
        put(FieldType.PROGRESS_BAR, DataType.INT);
        put(FieldType.SEEK_BAR, DataType.INT);
        put(FieldType.RATING_BAR, DataType.FLOAT);
        put(FieldType.ADAPTER_VIEW, DataType.INT);
        put(FieldType.NONE, DataType.OTHER);
    }};


    public enum DataType {
        LONG,
        DOUBLE,
        INT,
        FLOAT,
        BOOLEAN,
        STRING,

        OTHER,
    }

    public enum FieldType {
        TEXT_VIEW,
        COMPOUND_BUTTON,
        PROGRESS_BAR,
        RATING_BAR,
        SEEK_BAR,
        ADAPTER_VIEW,

        NONE
    }
}
