package com.example.lenovo.housekeepingplatform.nlp;

/**
 * Created by lenovo on 2018/11/16.
 */

public class WordToken {

    public String termText;
    public byte type;
//    public int start;
//    public int end;
//    public long cost;
//    public String code;// 语义编码
//    public HashMap<String, Object> features = new HashMap<String, Object>();

    public static final String Hypernym = "上位词"; // 上位词
    public static final String Hyponym = "下位词"; // 下位词
    public static final String Synonym = "同义词";
    public static final String Reference = "指称词"; //指代语的指称对象

    public WordToken(byte typ) {
        type = typ;
    }

    public WordToken(String word, byte typ) {
        termText = word;
        type = typ;
    }

//    public WordToken( String word,int vertexFrom, int vertexTo, byte typ) {
//        start = vertexFrom;
//        end = vertexTo;
//        termText = word;
//        type = typ;
//    }

//    public WordToken(int vertexFrom, int vertexTo, long c, String word, byte typ) {
//        start = vertexFrom;
//        end = vertexTo;
//        termText = word;
//        cost = c;
//        type = typ;
//    }

//    public WordToken(String word, int i, int j) {
//        termText = word;
//        start = i;
//        end = j;
//    }

    /**
     * Adds a feature to the feature map. If the feature already exists then it
     * is given the new value. If the value provided is <code>null</code> the
     * feature is removed from the map.
     *
//     * @param featureName
     *            the name of the feature.
//     * @param featureValue
     *            the new value of the feature or <code>null</code> if the
     *            feature is to be removed.
     */
//    public void setFeature(String featureName, Object featureValue) {
//        if (featureName != null) {
//            if (featureValue == null) {
//                this.features.remove(featureName);
//            } else {
//                this.features.put(featureName, featureValue);
//            }
//        }
//    }

    public String toString() {
        StringBuilder buffer = new StringBuilder("text:" + termText
//                + " start:" + start
//                + " end:" + end
//                + " cost:" + cost
                + " pos:"
                + PartOfSpeech.names[type]);
//        if (this.features != null) {
//            buffer.append(", features=").append(this.features.toString());
//        }
        return buffer.toString();
    }

//    public Object getFeature(String number) {
//        return this.features.get(number);
//    }

    /**
     * Retrieves the value of the feature as an <code>Integer</code>. If the
     * feature does not exist or cannot be converted to an integer then
     * <code>null</code> is returned.
     *
     * @param featureName
     *            the name of the feature.
     * @return the <code>Integer</code> representation of the value. Numbers are
     *         converted to integers while Strings are parsed for integer
     *         values. Any other type will return <code>null</code>.
     */
//    public Integer getFeatureAsInteger(String featureName) {
//        Object value = this.features.get(featureName);
//        Integer intValue = null;
//        if (value instanceof Integer) {
//            intValue = (Integer) value;
//        } else if (value instanceof Number) {
//            intValue = new Integer(((Number) value).intValue());
//        } else if (value instanceof String) {
//            try {
//                intValue = new Integer((String) value);
//            } catch (NumberFormatException exception) {
//                intValue = null;
//            }
//        }
//        return intValue;
//    }

    /**
     * Retrieves the value of the feature as a <code>Long</code>. If the feature
     * does not exist or cannot be converted to a long then <code>null</code> is
     * returned.
     *
     * @param featureName
     *            the name of the feature.
     * @return the <code>Long</code> representation of the value. Numbers are
     *         converted to longs while Strings are parsed for long values. Any
     *         other type will return <code>null</code>.
//     */
//    public Long getFeatureAsLong(String featureName) {
//        Object value = this.features.get(featureName);
//        Long longValue = null;
//        if (value instanceof Long) {
//            longValue = (Long) value;
//        } else if (value instanceof Number) {
//            longValue = new Long(((Number) value).longValue());
//        } else if (value instanceof String) {
//            try {
//                longValue = new Long((String) value);
//            } catch (NumberFormatException exception) {
//                longValue = null;
//            }
//        }
//        return longValue;
//    }

    /**
     * Retrieves the value of the feature as a <code>Float</code>. If the
     * feature does not exist or cannot be converted to a float then
     * <code>null</code> is returned.
     *
     * @param featureName
     *            the name of the feature.
     * @return the <code>Float</code> representation of the value. Numbers are
     *         converted to floats while Strings are parsed for float values.
     *         Any other type will return <code>null</code>.
     */
//    public Float getFeatureAsFloat(String featureName) {
//        Object value = this.features.get(featureName);
//        Float floatValue = null;
//        if (value instanceof Float) {
//            floatValue = (Float) value;
//        } else if (value instanceof Number) {
//            floatValue = new Float(((Number) value).floatValue());
//        } else if (value instanceof String) {
//            try {
//                floatValue = new Float((String) value);
//            } catch (NumberFormatException exception) {
//                floatValue = null;
//            }
//        }
//        return floatValue;
//    }

    /**
     * Retrieves the value of the feature as a <code>Double</code>. If the
     * feature does not exist or cannot be converted to a double then
     * <code>null</code> is returned.
     *
     * @param featureName
     *            the name of the feature.
     * @return the <code>Double</code> representation of the value. Numbers are
     *         converted to doubles while Strings are parsed for double values.
     *         Any other type will return <code>null</code>.
//     */
//    public Double getFeatureAsDouble(String featureName) {
//        Object value = this.features.get(featureName);
//        Double doubleValue = null;
//        if (value instanceof Double) {
//            doubleValue = (Double) value;
//        } else if (value instanceof Number) {
//            doubleValue = new Double(((Number) value).doubleValue());
//        } else if (value instanceof String) {
//            try {
//                doubleValue = new Double((String) value);
//            } catch (NumberFormatException exception) {
//                doubleValue = null;
//            }
//        }
//        return doubleValue;
//    }

    /**
     * Retrieves the value of the feature as a <code>Boolean</code>. If the
     * feature does not exist or is not a boolean then
     * <code>Boolean.FALSE</code> is returned.
     *
     * @param featureName
     *            the name of the feature.
     * @return the <code>Boolean</code> representation of the value. Any
     *         non-Boolean type will return <code>Boolean.FALSE</code>.
//     */
//    public Boolean getFeatureAsBoolean(String featureName) {
//        Object value = this.features.get(featureName);
//        Boolean boolValue = Boolean.FALSE;
//        if (value instanceof Boolean) {
//            boolValue = (Boolean) value;
//        }
//        return boolValue;
//    }

    /**
     * Retrieves the value of the feature as a string. If the feature doesn't
     * exist then <code>null</code> is returned.
     *
     * @param featureName
     *            the name of the feature.
     * @return the <code>String</code> representation of the value. This is
     *         taken by calling the object's <code>toString()</code> method.
     */
//    public String getFeatureAsString(String featureName) {
//        Object value = this.features.get(featureName);
//        String stringValue = null;
//
//        if (value != null) {
//            stringValue = value.toString();
//        }
//        return stringValue;
//    }

}
