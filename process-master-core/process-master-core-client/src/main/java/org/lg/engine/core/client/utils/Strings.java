package org.lg.engine.core.client.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Strings {
    public Strings() {
    }

    public static String join(CharSequence... array) {
        return join((Object[])array, (String)"");
    }

    public static String join(String separator, CharSequence... array) {
        return join((Object[])array, (String)separator);
    }

    public static String join(Object[] array) {
        return join(array, "");
    }

    public static String join(Object[] array, String separator) {
        return join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, char separator) {
        return join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            if (separator == null) {
                separator = "";
            }

            int L = endIndex - startIndex;
            if (L <= 0) {
                return "";
            } else {
                StringBuilder sb = new StringBuilder(L * (array[startIndex] == null ? 16 : array[startIndex].toString().length()) + separator.length());

                int index;
                for(index = startIndex; index < endIndex; ++index) {
                    if (array[index] != null) {
                        sb.append(array[index]).append(separator);
                    }
                }

                index = sb.lastIndexOf(separator);
                if (index != -1) {
                    sb.delete(index, sb.length());
                }

                return sb.toString();
            }
        }
    }

    public static String join(Object[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            int L = endIndex - startIndex;
            if (L <= 0) {
                return "";
            } else {
                StringBuilder sb = new StringBuilder(L * (array[startIndex] == null ? 16 : array[startIndex].toString().length()) + 1);

                int index;
                for(index = startIndex; index < endIndex; ++index) {
                    if (array[index] != null) {
                        sb.append(array[index]).append(separator);
                    }
                }

                index = sb.lastIndexOf(separator + "");
                if (index != -1) {
                    sb.deleteCharAt(index);
                }

                return sb.toString();
            }
        }
    }

//    public static <T> String join(Iterable<T> iterable, String separator) {
//        if (iterable == null) {
//            return null;
//        } else {
//            StringBuilder sb = new StringBuilder();
//            Iterator var3 = iterable.iterator();
//
//            while(var3.hasNext()) {
//                T t = var3.next();
//                if (t != null) {
//                    sb.append(t).append(separator);
//                }
//            }
//
//            int index = sb.lastIndexOf(separator);
//            if (index != -1) {
//                sb.delete(index, separator.length() + index);
//            }
//
//            return sb.toString();
//        }
//    }

    public static List<String> split(String regex, CharSequence input) {
        int index = 0;
        List<String> matchList = new ArrayList();

        for(Matcher m = Pattern.compile(regex).matcher(input); m.find(); index = m.end()) {
            matchList.add(input.subSequence(index, m.start()).toString());
        }

        if (index < input.length()) {
            matchList.add(input.subSequence(index, input.length()).toString());
        }

        return matchList;
    }

    public static String capitalize(CharSequence s) {
        return capitalize(s, " \t\r\n");
    }

    public static String capitalize(CharSequence s, String separator) {
        StringTokenizer st = new StringTokenizer(s.toString(), separator, true);
        StringBuilder sb = new StringBuilder(s.length());

        while(st.hasMoreTokens()) {
            String tok = st.nextToken();
            sb.append(tok.substring(0, 1).toUpperCase()).append(tok.substring(1).toLowerCase());
        }

        return sb.toString();
    }

    public static String lowerFirst(CharSequence s) {
        int len = s.length();
        if (len == 0) {
            return "";
        } else {
            char c = s.charAt(0);
            return Character.isLowerCase(c) ? s.toString() : (new StringBuilder(len)).append(Character.toLowerCase(c)).append(s.subSequence(1, len)).toString();
        }
    }

    public static String upperFirst(CharSequence s) {
        int len = s.length();
        if (len == 0) {
            return "";
        } else {
            char c = s.charAt(0);
            return Character.isUpperCase(c) ? s.toString() : (new StringBuilder(len)).append(Character.toUpperCase(c)).append(s.subSequence(1, len)).toString();
        }
    }

    public static boolean equalsIgnoreCase(String s1, String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    public static boolean equalsIgnoreCase(Object obj, String str) {
        return obj == null ? str == null : valueOf(obj).equalsIgnoreCase(str);
    }

    public static boolean equals(CharSequence s1, CharSequence s2) {
        return s1 == null ? s2 == null : s1.equals(s2);
    }

    public static boolean startsWithChar(CharSequence s, char c) {
        return null != s && s.length() != 0 && s.charAt(0) == c;
    }

    public static boolean endsWithChar(CharSequence s, char c) {
        return null != s && s.length() != 0 && s.charAt(s.length() - 1) == c;
    }

    public static boolean isEmpty(CharSequence... css) {
        CharSequence[] var1 = css;
        int var2 = css.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CharSequence cs = var1[var3];
            if (isEmpty(cs)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isEmpty(CharSequence cs) {
        return null == cs || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return null != cs && cs.length() > 0;
    }

    public static boolean isNotEmpty(CharSequence... css) {
        CharSequence[] var1 = css;
        int var2 = css.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CharSequence cs = var1[var3];
            if (isEmpty(cs)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isBlank(CharSequence cs) {
        int L;
        if (cs != null && (L = cs.length()) != 0) {
            for(int i = 0; i < L; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    public static String trim(CharSequence cs) {
        if (null == cs) {
            return null;
        } else if (cs instanceof String) {
            return ((String)cs).trim();
        } else {
            int length = cs.length();
            if (length == 0) {
                return cs.toString();
            } else {
                int l = 0;
                int last = length - 1;

                int r;
                for(r = last; l < length && Character.isWhitespace(cs.charAt(l)); ++l) {
                }

                while(r > l && Character.isWhitespace(cs.charAt(r))) {
                    --r;
                }

                if (l > r) {
                    return "";
                } else {
                    return l == 0 && r == last ? cs.toString() : cs.subSequence(l, r + 1).toString();
                }
            }
        }
    }

    public static String valueOf(Object o, String defaultVal) {
        return o == null ? defaultVal : o.toString();
    }

    public static String valueOf(Object o) {
        return valueOf(o, "");
    }

    public static String replace(String string, Pattern pattern, IReplaceCallback replacement) {
        if (string == null) {
            return null;
        } else {
            Matcher m = pattern.matcher(string);
            if (!m.find()) {
                return string;
            } else {
                StringBuffer sb = new StringBuffer();
                int var5 = 0;

                do {
                    m.appendReplacement(sb, Matcher.quoteReplacement(replacement.replace(m.group(0), var5++, m)));
                } while(m.find());

                m.appendTail(sb);
                return sb.toString();
            }
        }
    }

    public abstract static class ReplaceCallback implements IReplaceCallback {
        protected Matcher matcher;

        public ReplaceCallback() {
        }

        public abstract String replace(String var1, int var2);

        public final String replace(String text, int index, Matcher matcher) {
            this.matcher = matcher;

            String var4;
            try {
                var4 = this.replace(text, index);
            } finally {
                this.matcher = null;
            }

            return var4;
        }

        protected final String $(int group) {
            String data = this.matcher.group(group);
            return data == null ? "" : data;
        }
    }

    @FunctionalInterface
    public interface IReplaceCallback {
        String replace(String var1, int var2);

        default String replace(String text, int index, Matcher matcher) {
            return this.replace(text, index);
        }
    }
}
